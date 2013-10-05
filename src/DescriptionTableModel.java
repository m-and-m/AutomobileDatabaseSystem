import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class DescriptionTableModel extends AbstractTableModel {
	
	private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	private ArrayList<String> header = new ArrayList<String>();
	
 	public void updateData(ArrayList<ArrayList<String>> list) {
 		header.clear();
 		for (int i = 0; i < list.get(0).size(); i++) {
 			header.add(list.get(0).get(i));
 		}
 		list.remove(0);
 		data = list;
 		fireTableDataChanged();
/* 		for (ArrayList<String> item : list) {
 			for (String s : item) {
 				System.out.println("s:"+s);
 			}
 		}
 */		
 	} // updateData
 	
 	public String getLastCoulmnValue(int row) {
 		if(row >= 0) {
 			return data.get(row).get(getColumnCount()-1);
 		} 
 		return null;
 	} // getLastCoulmnValue
 	
 	public String getLast2CoulmnValue(int row) {
 		if(row >= 0) {
 			return data.get(row).get(getColumnCount()-2);
 		} 
 		return null;
 	} // getLast2CoulmnValue
 	
 	public ArrayList<ArrayList<String>> getData() {
 		return data;
 	} // getData
 	
 	public String getColumnName(int col) {
 		return header.get(col);
 	}
 	
	@Override
	public int getColumnCount() {
		return header.size();
	}
	
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row).get(col);
	}

}
