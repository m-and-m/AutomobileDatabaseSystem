import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ByUser extends JPanel implements ActionListener, ListSelectionListener{
	// Search button
	private JButton btnDesc = null;
	// Search button
	private JButton btnRslt = null;			
	// Maker comboBox
	private JComboBox<String> comboMaker = null;
	// Model comboBox
	private JComboBox<String> comboModel = null;
	// Year comboBox
	private JComboBox<String> comboYear = null;
	// Vendor comboBox
	private JComboBox<String> comboVendor = null;
	// JLabel for Maker comboBox
	private JLabel lblMaker = null;
	// JLabel for Model comboBox
	private JLabel lblModel = null;
	// JLabel for Year comboBox
	private JLabel lblYear = null;
	// JLabel for Rlink JTextfield
	private JLabel lblRlink = null;	
	// JLabel for Vendor comboBox
	private JLabel lblVendor = null;	
	// JTable for all description
	private JTable tblDscpn = null;
	// JTable for result
	private JTable tblRslt = null;
	// Rlink JTextfield
	private JTextField txtRlink = null;
	// JScroll pane
	private JScrollPane jScrollPane1 = null;
	// JScroll pane
	private JScrollPane jScrollPane2 = null;
	
	// Class DescriptionComboModel's alias
	private DescriptionTableModel dscpnModel;
	// Class DescriptionComboModel's alias
	private DescriptionTableModel rsltModel;
	// Class SelectionComboModel's alias
	private SelectionComboModel comboMMaker;
	// Class SelectionComboModel's alias
	private SelectionComboModel comboMModel;
	// Class SelectionComboModel's alias
	private SelectionComboModel comboMYear;
	// Class SelectionComboModel's alias
	private SelectionComboModel comboMVendor;
	
	@SuppressWarnings("unchecked")
	public ByUser() throws SQLException {
		super();
		getJContentPane();
		
		comboMMaker= new SelectionComboModel(DBManager.getMakerList());
		getComboMaker().setModel(comboMMaker);

	} // ByModel

/*********JBUTTON*********/	
	private JButton getBtnDesc() {
		if(btnDesc == null) {
			btnDesc = new JButton("Description");
			btnDesc.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnDesc.addActionListener(this);
		}	
		return btnDesc;
	} // getBtnDesc
	
	private JButton getBtnRslt() {
		if(btnRslt == null) {
			btnRslt = new JButton("Result");
			btnRslt.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnRslt.addActionListener(this);
		}	
		return btnRslt;
	} // getBtnRslt	

/*********JCOMBO*********/	
	private JComboBox<String> getComboMaker(){
		if(comboMaker  == null) {
			comboMaker  = new JComboBox<String>();
			comboMaker .addActionListener(this);
		}
		return comboMaker ;
	} // getComboMaker
	
	private JComboBox<String> getComboModel(){
		if(comboModel == null) {
			comboModel = new JComboBox<String>();
			comboModel.addActionListener(this);
		}
		return comboModel;
	} // getComboModel
	
	private JComboBox<String> getcomboVendor(){
		if(comboVendor == null) {
			comboVendor = new JComboBox<String>();
			comboVendor.addActionListener(this);
		}
		return comboVendor;
	} // getcomboVendor
	
	private JComboBox<String> getComboYear(){
		if(comboYear == null) {
			comboYear = new JComboBox<String>();
			comboYear.addActionListener(this);
		}
		return comboYear;
	} // getComboYear

/*********JLABEL*********/	
	private JLabel getLblMaker(){
		if(lblMaker == null) {
			lblMaker = new JLabel("Maker: ");
			//lblModel.setHorizontalAlignment(defaultCloseOperation)
		}
		return lblMaker;
	} // getLblModel
	
	private JLabel getLblModel(){
		if(lblModel == null) {
			lblModel = new JLabel("Model: ");
			//lblModel.setHorizontalAlignment(defaultCloseOperation)
		}
		return lblModel;
	} // getLblModel
	
	private JLabel getLblYear(){
		if(lblYear == null) {
			lblYear = new JLabel("Year: ");
			//lblModel.setHorizontalAlignment(defaultCloseOperation)
		}
		return lblYear;
	} // getLblModel

	private JLabel getLblRlink() {
		if(lblRlink == null) {
			lblRlink = new JLabel("Rlink:");
			lblRlink.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return lblRlink;
	} // getLblRlink
	
	private JLabel getlblVendor() {
		if(lblVendor == null) {
			lblVendor = new JLabel("Vendor:");
			lblVendor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return lblVendor;
	} // getlblVendor
	
/*********JTABLE*********/	
	private JTable getTblDscpn() {
		if(tblDscpn == null) {
			tblDscpn = new JTable();
			//tblDscpn.setEnabled(false);
			tblDscpn.getSelectionModel().addListSelectionListener(this);
			tblDscpn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return tblDscpn;
	} // getTblDscpn
	
	private JTable getTblRslt() {
		if(tblRslt == null) {
			tblRslt = new JTable();
			//tblDscpn.setEnabled(false);
			//tblRslt.getSelectionModel().addListSelectionListener(this);
			//tblRslt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return tblRslt;
	} // getTblRslt

/*********JSCROLL*********/	
	private JScrollPane getJScrollPane1() {
		if(jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getTblDscpn());
		}
		return jScrollPane1;
	} // getJScrollPane
	
	private JScrollPane getJScrollPane2() {
		if(jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getTblRslt());
		}
		return jScrollPane2;
	} // getJScrollPane2
	
/*********JTEXTFIELD*********/	
	private JTextField getTxtRlink() {
		if(txtRlink == null) {
			txtRlink = new JTextField();
		}
		return txtRlink;
	} // getTxtRlink
	
/*********ListSelectionListener*********/
/*************valueChanged*********/	
	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(ListSelectionEvent e) {
		String rlink = "";
		if(!e.getValueIsAdjusting()){
			int numRow = tblDscpn.getSelectedRow();
			//System.out.println(numRow);
			rlink = dscpnModel.getLastCoulmnValue(numRow);
			getTxtRlink().setText(rlink);
			//System.out.println(dscpnModel.getLastCoulmnValue(numRow));
			try {
				System.out.println("Rlink: "+rlink);
				List<String> partslist = extractPartsId(DBManager.getVendor(rlink));
				//System.out.println(partslist.toString());
				comboMVendor = new SelectionComboModel(partslist);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//HERE			
			getcomboVendor().setModel(comboMVendor);
		}
	} // valueChanged

	public List<String> extractPartsId(ArrayList<ArrayList<String>> array2d){
		List<String> partslist = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();
		
        /* stack partsId, which has some value with, 
		* and, push the partsId into stack
		* also, removes rlink(title, vlaue)
        */
        for(int i = 1; i < array2d.get(0).size(); i++) {
        	if (array2d.get(1).get(i) != null){
        		stack.push(array2d.get(0).get(i));
            }
        }
        
        while(!stack.isEmpty()){
        	partslist.add(stack.pop());
        }
        
        Collections.sort(partslist);
        
        return partslist;
	} // extractPartsId
	
/*********ActionSelectionListener*********/
/*************actionPerformed*********/	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		String maker = "";
		String model = "";
		String year = "";
		String vendor = "";
		if (e.getSource() == getComboMaker()) {
			maker = getComboMaker().getSelectedItem().toString();
			System.out.println("Maker: "+maker);

			try {
				comboMModel = new SelectionComboModel(DBManager.getModelList(maker));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			getComboModel().setModel(comboMModel);				
			
		} else if(e.getSource() == getComboModel()){
			maker = getComboMaker().getSelectedItem().toString();
			model = getComboModel().getSelectedItem().toString();
			System.out.println("Model name:"+model);
			
			try {
				comboMYear = new SelectionComboModel(DBManager.getYearList(maker, model));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			getComboYear().setModel(comboMYear);
		} else if (e.getSource() == getComboYear()) {
			year = getComboYear().getSelectedItem().toString();
			System.out.println("Year :"+year);
		} else if (e.getSource() == getBtnDesc()) {
			try {
				maker = getComboMaker().getSelectedItem().toString();
				model = getComboModel().getSelectedItem().toString();
				year = getComboYear().getSelectedItem().toString();
				
				dscpnModel = new DescriptionTableModel();
				
				dscpnModel.updateData(showRlink(maker, model, year));
				getTblDscpn().setModel(dscpnModel);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == getBtnRslt()) {
			vendor = getcomboVendor().getSelectedItem().toString();
			System.out.println("Vendor: "+vendor);
			String rlink = getTxtRlink().getText();
			
			rsltModel = new DescriptionTableModel();
			
			try {
				rsltModel.updateData(DBManager.getResultByMaker(vendor, rlink));
				// in the case of rslt is empty (i.e., NA/NS)
				if (rsltModel.getRowCount() < 1) {

					ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
			 		
					ArrayList<String> hdr = new ArrayList<String>();
			 		ArrayList<String> bdy = new ArrayList<String>();
					for (int i = 0; i < rsltModel.getColumnCount(); i++) {
						hdr.add(rsltModel.getColumnName(i));
			 			bdy.add("-");		 			
			 		}
					data.add(hdr);
					data.add(bdy);
					rsltModel.updateData(data);
					getTblRslt().setModel(rsltModel);	
				} else {
					getTblRslt().setModel(rsltModel);
				}

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // try-catch	
		} // end of else-if
	} // actionPerformed
	
	private ArrayList<ArrayList<String>> showRlink(String maker, String model, String year) throws SQLException {
		//System.out.println("h "+model+" yr "+year);
		return  DBManager.getDiscription(maker, model, year);
	} //showRlink

/*************JCONTENTPANE*********/		
	private void getJContentPane(){

		java.awt.GridBagConstraints consGridBagConstraints1 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints2 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints3 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints4 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints5 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints6 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints7 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints8 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints9 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints10 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints11 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints12 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints13 
											= new java.awt.GridBagConstraints();
		java.awt.GridBagConstraints consGridBagConstraints14 
											= new java.awt.GridBagConstraints();
		
		//Insets(top, left, bottom, right)
		consGridBagConstraints1.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints1.ipady = 2;
		consGridBagConstraints1.ipadx = 3;
		consGridBagConstraints1.gridy = 1;
		consGridBagConstraints1.gridx = 0;
		
		consGridBagConstraints2.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints2.ipadx = 3;
		consGridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints2.weightx = 1;
		//consGridBagConstraints2.gridwidth = 1;
		consGridBagConstraints2.gridy = 1;
		consGridBagConstraints2.gridx = 1;
		
		consGridBagConstraints3.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints3.ipady = 2;
		consGridBagConstraints3.ipadx = 3;
		consGridBagConstraints3.gridy = 1;
		consGridBagConstraints3.gridx = 2;
		
		consGridBagConstraints4.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints4.ipadx = 3;
		consGridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints4.weightx = 1;
		//consGridBagConstraints4.gridwidth = 1;
		consGridBagConstraints4.gridy = 1;
		consGridBagConstraints4.gridx = 3;
			
		consGridBagConstraints5.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints5.ipady = 2;
		consGridBagConstraints5.gridy = 1;
		consGridBagConstraints5.gridx = 4;
		
		consGridBagConstraints6.insets = new java.awt.Insets(10,1,10,1);
		//consGridBagConstraints6.ipady = 200;
		//consGridBagConstraints6.ipadx = 500;
		consGridBagConstraints6.fill = java.awt.GridBagConstraints.BOTH;
		consGridBagConstraints6.weighty = 1.0;
		//consGridBagConstraints6.weightx = 1.0;
		consGridBagConstraints6.gridwidth = 5;
		consGridBagConstraints6.gridy = 2;
		consGridBagConstraints6.gridx = 0;
		
		consGridBagConstraints7.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints7.ipady = 62;
		consGridBagConstraints7.ipadx = 1;
		//consGridBagConstraints7.gridwidth = 2;
		consGridBagConstraints7.gridy = 3;
		consGridBagConstraints7.gridx = 0;
		
		consGridBagConstraints8.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints8.ipadx = 62;
		consGridBagConstraints8.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints8.weightx = 0;
		consGridBagConstraints8.gridy = 3;
		consGridBagConstraints8.gridx = 1;
		
		consGridBagConstraints9.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints9.ipady = 6;
		consGridBagConstraints9.ipadx = 0;
		//consGridBagConstraints9.gridwidth = 2;
		consGridBagConstraints9.gridy = 3;
		consGridBagConstraints9.gridx = 2;
		
		consGridBagConstraints10.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints10.ipadx = 1;
		consGridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints10.weightx = 8;
		consGridBagConstraints10.gridy = 3;
		consGridBagConstraints10.gridx = 3;
		
		consGridBagConstraints11.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints11.ipady = 0;
		consGridBagConstraints11.gridy = 3;
		consGridBagConstraints11.gridx = 4;
		
		consGridBagConstraints12.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints12.ipady = 200;
		consGridBagConstraints12.ipadx = 500;
		consGridBagConstraints12.fill = java.awt.GridBagConstraints.BOTH;
		//consGridBagConstraints12.weighty = 1.0;
		consGridBagConstraints12.weightx = 10;
		consGridBagConstraints12.gridwidth = 5;
		consGridBagConstraints12.gridy = 4;
		consGridBagConstraints12.gridx = 0;
		
		consGridBagConstraints13.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints13.weightx = 1;
		consGridBagConstraints13.ipady = 2;
		consGridBagConstraints13.ipadx = 3;
		consGridBagConstraints13.gridy = 0;
		consGridBagConstraints13.gridx = 1;
		
		consGridBagConstraints14.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints14.ipady = 2;
		consGridBagConstraints14.ipadx = 3;
		consGridBagConstraints14.gridy = 0;
		consGridBagConstraints14.gridx = 0;
		
		this.setLayout(new java.awt.GridBagLayout());
		this.add(getLblModel(), consGridBagConstraints1);
		this.add(getComboModel(), consGridBagConstraints2);
		this.add(getLblYear(), consGridBagConstraints3);
		this.add(getComboYear(), consGridBagConstraints4);
		this.add(getBtnDesc(), consGridBagConstraints5);
		this.add(getJScrollPane1(), consGridBagConstraints6);
		this.add(getLblRlink(), consGridBagConstraints7);
		this.add(getTxtRlink(), consGridBagConstraints8);
		this.add(getlblVendor(), consGridBagConstraints9);
		this.add(getcomboVendor(), consGridBagConstraints10);
		this.add(getBtnRslt(), consGridBagConstraints11);	
		this.add(getJScrollPane2(), consGridBagConstraints12);
		this.add(getComboMaker(), consGridBagConstraints13);
		this.add(getLblMaker(), consGridBagConstraints14);
	} // getJContentPane
	
} /// ByUser