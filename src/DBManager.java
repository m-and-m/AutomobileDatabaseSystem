
import java.sql.*;
import java.util.*;
import java.lang.String;

public class DBManager {

	private static Connection con;
    private final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String DB_URL    = "jdbc:oracle:thin:@localhost:1521:oraDB";
    private final String USER_ID   = "scott";
    private final String PASSWORD  = "tiger";
    
	/**
	 * @param args
	 */
	public DBManager() {
        try {
            Class.forName(DB_DRIVER);
            con = DriverManager.getConnection(DB_URL, USER_ID, PASSWORD);

            System.out.println("Connected to DB!");
//INTERVIEW QUESTION
            //insertData();
//INTERVIEW QUESTION-2            
            //createSJSU3Maker();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
	} // DBManager
	
    public Connection getDBConnection() {
    	return DBManager.con; 
    } // getDBConnection

    public static void disconnectFromDB() {
        try {
            con.close();
            System.out.println("Disconnected from DB!");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    } //disconnectFromDB
    
/*****************************************INTERVIEW QUESTION***********************************/    
public static void insertData() throws SQLException {
	Statement st = con.createStatement();	
	String query = "UPDATE radcrx SET ARS1='847' WHERE RLINK=4391";
	
	System.out.println("HERE INTERVIEW QUESTION!") ;
	int rows = st.executeUpdate(query);
	System.out.println(rows + " rows modified.") ;
} // insertData
/*****************************************INTERVIEW QUESTION-2***********************************/    
public static void createSJSU3Maker() throws SQLException {

	insertMaker();
	createTable();
	
} // insertData

public static void insertMaker() throws SQLException {
	Statement st = con.createStatement();	
	String query = "INSERT INTO maker VALUES('SJSU3', 'SJ3')";
	int rows = st.executeUpdate(query);
	System.out.println(rows + " rows modified.") ;
} // insertMaker 

public static void createTable() throws SQLException  {
	Statement st = con.createStatement();	
	String query = "CREATE TABLE APLSJ3 (MODEL VARCHAR2(25) NOT NULL,"+
										"YEAR VARCHAR2(4) NOT NULL,"+
										"DESCRIPTION VARCHAR2(51),"+
										"LITRES VARCHAR2(4),"+
										"ENGINE_TYPE VARCHAR2(3),"+
										"CUBIC_INCHES VARCHAR2(6),"+
										"RLINK NUMBER(4),"+
										"PRIMARY KEY (MODEL, YEAR, DESCRIPTION, " +
													 "LITRES, ENGINE_TYPE, CUBIC_INCHES),"+
										"FOREIGN KEY (RLINK) REFERENCES RADCRX)";
	int rows = st.executeUpdate(query);
	System.out.println(rows + " rows modified.") ;
} // createTable
/**************************************BY USER+BY MAKER*****************************************/    
    public static List<String> getMakerList() throws SQLException{
    	String query = "SELECT * FROM maker";
    	return insertList(query);
    } // getMakerList
    
    public static List<String> getModelList(String maker) throws SQLException {
    	String abbMaker =getAbbMaker(maker);
    	
       	System.out.println("@dbmangager/abb. maker:"+maker+" ("+abbMaker+")");
       	
    	String query = "SELECT DISTINCT model FROM apl"+abbMaker;
    	return insertList(query);
    } // getModelList
        
    public static List<String> getYearList(String maker, String model) throws SQLException {
    	String abbMaker =getAbbMaker(maker);
    	
    	String query = "SELECT DISTINCT year FROM apl"+abbMaker+
				" WHERE model ='"+model+"'";
    	return insertList(query);
    } //getYearList
    
    public static ArrayList<ArrayList<String>> getDiscription(String maker, String model, String year) throws SQLException {
    	String abbMaker = getAbbMaker(maker);
    	
    	String query = "SELECT description, litres AS ltr, engine_type AS ENG,"+
    				"Cubic_inches AS CID, RLINK FROM apl"+abbMaker+
    					" WHERE model='"+model+"' and year='"+year+"'";	

    		return insert2Ddata(query);
    } // getDiscription
    
    public static ArrayList<ArrayList<String>> getVendor(String rlink) throws SQLException {
    	String query = "";
    	
		if(rlink != null) {
			query = "SELECT * FROM radcrx WHERE rlink="+rlink;
			return insert2Ddata(query);
		} 
    	
		return null;
    } // getVendor
        
    public static ArrayList<ArrayList<String>> getResultByMaker (String vendor, String rlink) throws SQLException {
       	// triming vendor's id number 	
    	String trimmedVndr = vendor.substring(0, vendor.length() - 1);
    	
    	String query = "SELECT * FROM rdim"+trimmedVndr+" WHERE P_Number " +
    			"IN (SELECT "+vendor+" FROM radcrx WHERE rlink="+rlink+")";

    	return insert2Ddata(query);
    } // getResult 

    public static void deleteDataFromAPL(String maker, String model, String year, String cid, String rlink) throws SQLException {
    	String abbMaker = getAbbMaker(maker);
    	Statement st = con.createStatement();
    	
    	String query = "DELETE FROM APL"+abbMaker+" WHERE MODEL= '"+model+"' AND YEAR= '"+year+"' AND " +
    													 "CUBIC_INCHES ='"+cid+"' AND RLINK="+rlink;
    	
    	int rows = st.executeUpdate(query);
		System.out.println(rows + " rows modified (delete data by maker)"); 	
    } // deleteDataFromAPL
    
/*****************************************BY VENDOR*****************************************/   
    public static List<String> getAllParts(String vendor) throws SQLException  {
    	// triming vendor's id number 	
    	String trimmedVndr = vendor.substring(0, vendor.length() - 1);
    	
    	/* "RADCRX" has general/possible values; "RDIM+vendor" has actual values
    	* in this method, extract common parts number (i.e., NA, NS are eliminated)
    	*/
    	String query = "SELECT DISTINCT "+vendor+" FROM radcrx g, rdim"+trimmedVndr+" l " +
    			"WHERE g."+vendor+" = l.P_Number";
    	return insertList(query);
    } // getAllParts
       
    public static ArrayList<ArrayList<String>> getResultByVendor(String vendor, String partsId) throws SQLException {
    	// triming vendor's id number 	
    	vendor = vendor.substring(0, vendor.length() - 1);
    	
    	System.out.println("@dbmangager/trimed vendor:"+vendor);
    
    	String query = "SELECT * FROM rdim"+vendor+" WHERE P_Number = '"+partsId+"'";
    	return insert2Ddata(query);
    } // getResult 

    public static void deleteDataFromRADCRX(String vendor, String parts) throws SQLException {
    	Statement st = con.createStatement();

		String query = "UPDATE RADCRX SET "+vendor+" = '' WHERE "+vendor+" = '"+parts+"'";
		
		int rows = st.executeUpdate(query);
		System.out.println(rows + " rows modified (delete data in radcrx by vendor)");
    } // deleteDataFromRDIM
    
    public static void deleteDataFromRDIM(String vendor, String parts) throws SQLException {
    	// triming vendor's id number 	
    	vendor = vendor.substring(0, vendor.length() - 1);
    	
    	Statement st = con.createStatement();

		String query = "DELETE FROM rdim"+vendor+" WHERE P_NUMBER = '"+parts+"'";
		
		int rows = st.executeUpdate(query);
		System.out.println(rows + " rows modified (delete data in rdim by vendor)");
    } // deleteDataFromVendor
    
/*********************************MAKER DATA MANAGER**************************************/  	  
    public static void insertNewMakerData(String maker, String model, String year, String desc,
    									  String ltr, String eng, String cbi, String rlink) throws SQLException {
    	String abbMaker = getAbbMaker(maker);
    	Statement st = con.createStatement();
		String query = "INSERT INTO APL"+abbMaker+" VALUES('"+model+"', '"+year+"', '"+desc+"', '"+ltr+"', " +
														  "'"+eng+"', '"+cbi+"', "+rlink+")";
		int rows = st.executeUpdate(query);
		System.out.println(rows + " rows modified (insert desc by maker)"); 	
    } // insertNewMakerData
    
    public static ArrayList<ArrayList<String>> getDescriptionFromMkrMgmt(String maker, String model, String year, 
    																	 String cid, String rlink) throws SQLException {
    	String abbMaker = getAbbMaker(maker);
    	String query = "SELECT description, litres AS ltr, engine_type AS ENG,"+
    				"Cubic_inches AS CID, RLINK FROM apl"+abbMaker+
    					" WHERE model='"+model+"' AND year='"+year+"' AND" +
    					" Cubic_inches='"+cid+"' AND rlink="+rlink;	

    		return insert2Ddata(query);
    } // getDiscription
    
   
    public static void updateMakerData(String maker, String model, String year, String desc,
    								   String ltr, String eng, String newCid, String rlink, String oldCid) throws SQLException {
    	String abbMaker = getAbbMaker(maker);
    	Statement st = con.createStatement();
    	
    	String query = "UPDATE APL"+abbMaker+" SET DESCRIPTION='"+desc+"', LITRES='"+ltr+"'," +
    											" ENGINE_TYPE='"+eng+"', CUBIC_INCHES='"+newCid+"' " +
    										  "WHERE MODEL = '"+model+"' AND YEAR = '"+year+"' AND " +
    										  		"CUBIC_INCHES ='"+oldCid+"' AND RLINK ="+rlink;
    	int rows = st.executeUpdate(query);
		System.out.println(rows + " rows modified (update desc by maker)"); 	
    	
    } // updateMakerData
    
/*********************************VENDOR DATA MANAGER**************************************/  	
	public static void insertNewVendorData(String vendor, String partsNum, String core, String inHd, String outHd, String inCn, 
										   String outCn, String tMnt, String oilCl, String price, String amnt) throws SQLException {
    	// triming vendor's id number 	
    	vendor = vendor.substring(0, vendor.length() - 1);
    	
		Statement st = con.createStatement();
		String query = "INSERT INTO rdim"+vendor+" VALUES('"+partsNum+"', '"+core+"', '"+inHd+"', " +
														 "'"+outHd+"', '"+inCn+"', '"+outCn+"', '"+tMnt+"', " +
														 "'"+oilCl+"', '"+price+"', '"+amnt+"')";
		
		int rows = st.executeUpdate(query);
		System.out.println(rows + " rows modified (insert desc by vendor)");
	} // insertNewVendorData
	
	public static void insertNewPartsNum(String rlink, String vendor, String partsNum) throws SQLException {
		Statement st = con.createStatement();

		String query = "UPDATE RADCRX SET "+vendor+" = '"+partsNum+"' WHERE RLINK ="+rlink;
		
		int rows = st.executeUpdate(query);
		System.out.println(rows + " rows modified (update rlink+pNum by vendor)");
	} // insertNewPartsNum
		
/*	
	public static void updateVendorData(String vendor, String partsNum, String core, String inHd, String outHd, String inCn, 
										String outCn, String tMnt, String oilCl, String price, String amnt) throws SQLException {
	
    	// triming vendor's id number 	
    	vendor = vendor.substring(0, vendor.length() - 1);
    	
		Statement st = con.createStatement();
		String supplier_table = "RDIM" + vendor;
		int newPartNum = 1 + getLargestPartNumberByVendor(supplier_table);
		
		String query = "UPDATE "+supplier_table+" SET P_NUMBER = '"+partsNum+"', CORE = '"+core+"', INHEAD = '"+inHd+"', OUTHEAD = '"+outHd+"'," +
												" INCON = '"+inCn+"', OUCON = '"+outCn+"', TMOUNT = '"+tMnt+"'," +
												" OILCOOL = '"+oilCl+"', PRICE = "+price+", AMOUNT = "+amnt+
											"WHERE P_NUMBER = '"+partsNum+"'";
		int rows = st.executeUpdate(query);
		System.out.println(rows + " rows modified (insert data by vendor)");
	} // updateVendorData
	
	//MM
	public static void updateVendorData(String vendor, String partsNum, String core, String inHd, String outHd, String inCn, 
										String outCn, String tMnt, String oilCl, String price, String amnt) throws SQLException {
    	// triming vendor's id number 	
    	vendor = vendor.substring(0, vendor.length() - 1);
    	String supplier_table = "RDIM" + vendor;
    	
    	Statement st = con.createStatement();
    	
    	int NewPartsNum = 1 + getLargestPartNumberByVendor(supplier_table);
		String query = "UPDATE rdim"+vendor+" SET P_NUMBER = '"+NewPartsNum+"', CORE = '"+core+"', INHEAD = '"+inHd+"', OUTHEAD = '"+outHd+"'," +
												" INCON = '"+inCn+"', OUCON = '"+outCn+"', TMOUNT = '"+tMnt+"'," +
												" OILCOOL = '"+oilCl+"', PRICE = "+price+", AMOUNT = "+amnt+"WHERE P_NUMBER = '"+partsNum+"'";
		int rows = st.executeUpdate(query);
		System.out.println(rows + " rows modified (insert data by vendor)");
	} // updateVendorData
*/	
	
public static void updateVendorData(String vendor, String partsNum, String core, String inHd, String outHd, String inCn, 
			String outCn, String tMnt, String oilCl, String price, String amnt) throws SQLException {

// triming vendor's id number 	
	vendor = vendor.substring(0, vendor.length() - 1);

	Statement st = con.createStatement();
	String query = "UPDATE rdim"+vendor+" SET CORE = '"+core+"', INHEAD = '"+inHd+"', OUTHEAD = '"+outHd+"'," +
					" INCON = '"+inCn+"', OUCON = '"+outCn+"', TMOUNT = '"+tMnt+"'," +
					" OILCOOL = '"+oilCl+"', PRICE = "+price+", AMOUNT = "+amnt+
				"WHERE P_NUMBER = '"+partsNum+"'";
	int rows = st.executeUpdate(query);
	System.out.println(rows + " rows modified (insert data by vendor)");
} // updateVendorData

public static int updatePart(String vendor, String partsNum, String core, String inHd, String outHd, String inCn, 
		   String outCn, String tMnt, String oilCl, String price, String amnt)throws SQLException {

	vendor = vendor.toUpperCase();
	vendor = vendor.substring(0, vendor.length() - 1);
	Statement st = con.createStatement(); 
	String supplier_table = "RDIM" + vendor;
	int newPartNum = 1 + getLargestPartNumberByVendor(partsNum, supplier_table);
	String stmntStr = "update " + supplier_table + " set P_NUMBER = '"
			+ newPartNum + "', " + "CORE = '" + core + "', "
			+ "INHEAD = '" + inHd + "', " + "OUTHEAD = '"
			+ outHd + "', " + "INCON = '" + inCn + "', "
			+ "OUCON = '" + outCn + "', " + "TMOUNT = '"
			+ tMnt + "', " + "OILCOOL = '" + oilCl
			+ "', " + "PRICE = " + price + ", " + "AMOUNT = "
			+ amnt + " " + "where P_NUMBER = '" + partsNum
			+ "'";

	String stmntStr1 = "update radcrx set " + vendor + "1 = '"
			+ newPartNum + "' where " + vendor + "1 = '" + partsNum+ "'";
	String stmntStr2 = "update radcrx set " + vendor + "2 = '"
			+ newPartNum + "' where " + vendor + "2 = '" + partsNum+ "'";
	String stmntStr3 = "update radcrx set " + vendor + "3 = '"
			+ newPartNum + "' where " + vendor + "3 = '" + partsNum+ "'";
	String stmntStr4 = "update radcrx set " + vendor + "4 = '"
			+ newPartNum + "' where " + vendor + "4 = '" + partsNum+ "'";
	System.out.println(stmntStr);
	//Statement stmnt = con.createStatement();
	int rows = st.executeUpdate(stmntStr);
	System.out.println(stmntStr);
	int rows1 = st.executeUpdate(stmntStr1);
	System.out.println(rows1 + " rows modified in" + vendor + "1");
	int rows2 = st.executeUpdate(stmntStr2);
	System.out.println(rows2 + " rows modified in" + vendor + "2");
	int rows3 = st.executeUpdate(stmntStr3);
	System.out.println(rows3 + " rows modified in" + vendor + "3");
	int rows4 = st.executeUpdate(stmntStr4);
	System.out.println(rows4 + " rows modified in" + vendor + "4");
	return newPartNum;

} // updatePart
/*
public static int getLargestPartNumberByVendor(String suppTable) throws SQLException{
	int largeNum;
	String rslt = "";
	String query= "SELELCT MAX(p_number) FROM "+suppTable;
	Statement st = con.createStatement(); 
    ResultSet rs = st.executeQuery(query);
  
    while (rs.next()) {
    	if(rs.getString(1) != null){
    		rslt = rs.getString(1);
    	}
    }
		
    largeNum = Integer.parseInt(rslt);
	return largeNum;
} // getLargestPartNumberByVendor
*/

public static int getLargestPartNumberByVendor(String pnum, String suppTable) throws SQLException{
	
	String query= "SELECT MAX("+pnum+") FROM "+suppTable;
	System.out.println(query);
	
	Statement st = con.createStatement(); 
    ResultSet rs = st.executeQuery(query);
    
    if (rs.next()) {
    	int num = Integer.parseInt(rs.getString(1));
    	rs.close();
    	st.close();
    	return num;
    } 
	return 0;
} // getLargestPartNumberByVendor
/*****************************************MISC.*****************************************/
    public static void insertNewRlink(String rlink) throws SQLException {
    	Statement st = con.createStatement();
    	
    	String query = "INSERT INTO radcrx VALUES("+rlink+ ", '', '', '', ''" +
    													   ", '', '', '', ''" +
    													   ", '', '', '', ''," +
    													   " '', '', '', '')";
    	int rows = st.executeUpdate(query);
    	System.out.println(rows + " rows modified (insert rlink by vendor)");
    } // insertNewRlink
    
	private static ArrayList<ArrayList<String>> insert2Ddata(String query) throws SQLException {
    	Statement st = con.createStatement();
    	ResultSet rs = st.executeQuery(query);
    	ResultSetMetaData rsmd = rs.getMetaData();
    	int numColumn = rsmd.getColumnCount();
        ArrayList<ArrayList<String>> array2D = new ArrayList<ArrayList<String>>();
        array2D.add(new ArrayList<String>());
        
        for (int i = 1; i <= numColumn; i++) {
        	array2D.get(0).add(rsmd.getColumnName(i));
    	}
        
        int numRow = 1; 
        while(rs.next()) {
        	array2D.add(new ArrayList<String>());
        	 for (int i = 1; i <= numColumn; i++) {
        		 array2D.get(numRow).add(rs.getString(i));
        	 }
        	 numRow++;
        }
        
        return array2D;
    } // insert2Ddata 
    
    private static List<String> insertList(String query) throws SQLException {
    	Statement st = con.createStatement();
    	ResultSet rs = st.executeQuery(query);
    	List<String> list = new ArrayList<String>();
 
    	while (rs.next()) {
    		if(rs.getString(1) != null){
    			list.add(rs.getString(1));
    		}
    	}
    	 	
    	rs.close();

    	Collections.sort(list);
    	
    	return list; 
    } // insertList
    
    private static String getAbbMaker(String maker) throws SQLException { 	
    	String query0 = "SELECT cod FROM maker WHERE mak = '"+maker+"'"; 
    	System.out.println(query0);
    	List<String> rslt = insertList(query0);
    	String abbMaker = rslt.get(0).toLowerCase();
    	return abbMaker;
    } // getAbbMaker
    
 } /// DBManager
