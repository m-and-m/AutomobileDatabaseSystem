import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class VendorDataManage extends JFrame implements ActionListener {
	// Panel
	private JPanel panel = new JPanel();
	
	// JLabel for Vendor 
	private JLabel lblVendor = null;
	// JLabel for Vendor Variable
	private JLabel lblVndrV = null;
	
	// JLabel for Rlink 
	private JLabel lblRlink = null;
	
	// JLabel for Parts
	private JLabel lblParts = null;
	// JLabel for Parts Variable
	private JLabel lblPrtsV = null;
	
	// JLabel for Core 
	private JLabel lblCore = null;
	// JLabel for Inhead 
	private JLabel lblInHd = null;
	// JLabel for Outhead 
	private JLabel lblOutHd = null;
	// JLabel for Incon 
	private JLabel lblInCn = null;
	// JLabel for Outcon 
	private JLabel lblOutCn = null;
	// JLabel for Tmount 
	private JLabel lblTmnt = null;
	// JLabel for Oilcool 
	private JLabel lblOil = null;
	// JLabel for Price 
	private JLabel lblPrice = null;
	// JLabel for Amount 
	private JLabel lblAmnt = null;

	// Rlink JTextfield
	private JTextField txtRlink = null;
	// Parts JTextfield
	private JTextField txtParts = null;
	// Core JTextfield
	private JTextField txtCore = null;
	// Inhead JTextfield
	private JTextField txtInHd = null;
	// Outhead JTextfield
	private JTextField txtOutHd = null;
	// Incon JTextfield
	private JTextField txtInCn = null;
	// Outcon JTextfield
	private JTextField txtOutCn = null;
	// Tmount JTextfield
	private JTextField txtTmnt = null;
	// Oilcool JTextfield
	private JTextField txtOil = null;
	// Price JTextfield
	private JTextField txtPrice = null;
	// Amount JTextfield
	private JTextField txtAmnt = null;
	
	// Apply button
	private JButton btnApply = null;
	// UpdatePart button
	private JButton btnUpdt = null;
	// Cancel button
	private JButton btnCancel = null;
	
	private String nameVendor = "";
	private String valParts = "";
	
/*********BODY*********/
	/*
	 * For create new data, passed only vendor name 
	 */
	public VendorDataManage(String vendor) {
		super();
		frame_initialize();
		this.setTitle("Create New Data");
		
		System.out.println("@VendorDataM");
		System.out.println("vendor: "+vendor);
		
		nameVendor = vendor;
		
		getBtnUpdt().setEnabled(false);
		
		getLblPrtsVariable().setEnabled(false);
		
		getPnelComponent();
		getContentPane().add(panel);
		
		setVisible(true);
	} // VendorDataManage
	
	/*
	 * For modify data, passed vendor name and parts number
	 */
	public VendorDataManage(String vendor, String parts) throws SQLException {
		super();
		frame_initialize();
		this.setTitle("Modify Data");
		
		nameVendor = vendor;
		//valParts = parts;
		
		//getTxtParts().show(false);
		getLblRlink().setEnabled(false);
		getTxtRlink().setEnabled(false);
		
		// copy all description from main win. into "Modify Data" win.
		copyDataIntoTxtFields(vendor, parts);
		
		getPnelComponent();
		getContentPane().add(panel);
		
		setVisible(true);
	} // VendorDataManage

	private void copyDataIntoTxtFields(String v, String p) throws SQLException {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

		data = DBManager.getResultByVendor(v, p);
		data.remove(0);
		
		System.out.println("@VendorDataMgmt/copydata");
		System.out.println(data.toString());
		
		
		for (int curridx = 0 ;curridx < 10; curridx++) {
			getFieldByIndex(curridx).setText(data.get(0).get(curridx));
		}
		
 	} // copyDataIntoTxtFields
	
	private JTextField getFieldByIndex(int index) {
		JTextField txtField = null;
		switch (index) {
			case 0: txtField = getTxtParts();
				break;
	 		case 1: txtField = getTxtCore();
	 				break;
	 		case 2: txtField = getTxtInHd();
					break;
			case 3: txtField = getTxtOutHd();
					break;
			case 4: txtField = getTxtInCn();
					break;
			case 5: txtField = getTxtOutCn();
					break;
			case 6: txtField = getTxtTmnt();
					break;
			case 7: txtField = getTxtOil();
					break;
			case 8: txtField = getTxtPrice();
					break;
			case 9: txtField = getTxtAmnt();
					break;
		}
		
		return txtField;
	} // getFieldByIndex
	
	private void frame_initialize() {		
	    Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    int screenHeight = screenSize.height;
	    
		this.setSize(500,600);
		setLocation(600, screenHeight / 4);
	} // frame_initialize

/*********ActionSelectionListener*********/
/*************actionPerformed*********/		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == getBtnApply()) {
			
			//FIXME
			// 1: be able to check type of values
			// 2: be able to check if parts number is already taken
						
			String core = getTxtCore().getText();
			String inHd = getTxtInHd().getText();
			String outHd = getTxtOutHd().getText();
			String inCn = getTxtInCn().getText();
			String outCn = getTxtOutCn().getText();
			String tMnt = getTxtTmnt().getText();
			String oilCl = getTxtOil().getText();
			String price = getTxtPrice().getText();
			String amnt = getTxtAmnt().getText();
			
			if(this.getTitle().equalsIgnoreCase("Create New Data")){				
				String partsNum = getTxtParts().getText();
				String rlink = getTxtRlink().getText();

//TRANSITION MGMT STARTS	
				
				try {
					DBManager.insertNewRlink(rlink);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				try {
					DBManager.insertNewVendorData(nameVendor, partsNum, core, inHd, outHd, inCn, 
							   outCn, tMnt, oilCl, price, amnt);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					DBManager.insertNewPartsNum(rlink, nameVendor, partsNum);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(this,
                        "Part number '"+partsNum+"' is added successfully in "+nameVendor);	
							
//TRANSITION MGMT ENDS: COMMIT, OTHERWISE...DO ROLLBACK
							
				dispose();
				
			} else if(this.getTitle().equalsIgnoreCase("Modify Data")) {

				// update modified values into RDIM***
				String partsNum = getTxtParts().getText();
				System.out.println("Parts number:"+partsNum);
				
				try {
					DBManager.updateVendorData(nameVendor, partsNum, core, inHd, outHd, inCn, 
											   outCn, tMnt, oilCl, price, amnt);
					} catch (SQLException e1) {
					e1.printStackTrace();
					}
				
				JOptionPane.showMessageDialog(this,
							"Part number '"+partsNum+"' is updated successfully in "+nameVendor);
				// refresh desc table
				ByVendor.refreshDescTable(nameVendor, partsNum);
				
				dispose();
			} // end of inside if
		} else if (e.getSource() == getBtnUpdt()) {
			String partsNum = getTxtParts().getText();
			String core = getTxtCore().getText();
			String inHd = getTxtInHd().getText();
			String outHd = getTxtOutHd().getText();
			String inCn = getTxtInCn().getText();
			String outCn = getTxtOutCn().getText();
			String tMnt = getTxtTmnt().getText();
			String oilCl = getTxtOil().getText();
			String price = getTxtPrice().getText();
			String amnt = getTxtAmnt().getText();
			
			try {
				DBManager.updatePart(nameVendor, partsNum, core, inHd, outHd, inCn, 
						   outCn, tMnt, oilCl, price, amnt);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == getBtnCancel()) {
			dispose();
		} // end of outside if
		
	} // actionPerformed
	
/*********JBUTTON*********/	
	private JButton getBtnApply() {
		if(btnApply == null) {
			btnApply = new JButton("Apply");
			//btnSearch.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnApply.addActionListener(this);
		}	
		return btnApply;
	} // getBtnApply

	private JButton getBtnUpdt() {
		if(btnUpdt == null) {
			btnUpdt = new JButton("Update Part");
			//btnSearch.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnUpdt.addActionListener(this);
		}	
		return btnUpdt;
	} // getBtnUpdt
	
	private JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new JButton("Cancel");
			//btnSearch.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnCancel.addActionListener(this);
		}	
		return btnCancel;
	} // getBtnCancel
	
/*********JLABEL*********/	
	private JLabel getLblVendor(){
		if(lblVendor == null) {
			lblVendor = new JLabel("Vendor: ");
		}
		return lblVendor;
	} // getLblVendor	
	
	private JLabel getLblVndrVariable(){
		if(lblVndrV == null) {
			lblVndrV = new JLabel("");
		} 	
		lblVndrV = new JLabel(nameVendor);
		return lblVndrV;
	} // getLblVndrVariable
	
	private JLabel getLblRlink(){
		if(lblRlink == null) {
			lblRlink = new JLabel("*Rlink(4 digits): ");
		}
		return lblRlink;
	} // getLblRlink
	
	private JLabel getLblParts(){
		if(lblParts == null) {
			lblParts = new JLabel("*Parts Number(3 letters): ");
		}
		return lblParts;
	} // getLblParts
	
	private JLabel getLblPrtsVariable(){
		if(lblPrtsV == null) {
			lblPrtsV = new JLabel("");
		}
		lblPrtsV = new JLabel(valParts);
		return lblPrtsV;
	} // getLblPrtsVariable
	
	private JLabel getLblCore(){
		if(lblCore == null) {
			lblCore = new JLabel("Core: ");
		}
		return lblCore;
	} // getLblCore	

	private JLabel getLblInHd(){
		if(lblInHd == null) {
			lblInHd = new JLabel("Inhead: ");
		}
		return lblInHd;
	} // getLblInHd	

	private JLabel getLblOutHd(){
		if(lblOutHd == null) {
			lblOutHd = new JLabel("Outhead: ");
		}
		return lblOutHd;
	} // getLblOutHd	

	private JLabel getLblInCn(){
		if(lblInCn == null) {
			lblInCn = new JLabel("InCon: ");
		}
		return lblInCn;
	} // getLblInCn	

	private JLabel getLblOutCn(){
		if(lblOutCn == null) {
			lblOutCn = new JLabel("OutCon: ");
		}
		return lblOutCn;
	} // getLblOutCn	

	private JLabel getLblTmnt(){
		if(lblTmnt == null) {
			lblTmnt = new JLabel("Tmount: ");
		}
		return lblTmnt;
	} // getLblTmnt	

	private JLabel getLblOil(){
		if(lblOil == null) {
			lblOil = new JLabel("Oilcool: ");
		}
		return lblOil;
	} // getLblOil	

	private JLabel getLblPrice(){
		if(lblPrice == null) {
			lblPrice = new JLabel("Price($): ");
		}
		return lblPrice;
	} // getLblPrice	

	private JLabel getLblAmnt(){
		if(lblAmnt == null) {
			lblAmnt = new JLabel("Amount(#): ");
		}
		return lblAmnt;
	} // getLblAmnt	
	
/*********JTEXTFIELD*********/	
	private JTextField getTxtRlink() {
		if(txtRlink == null) {
			txtRlink = new JTextField();
		}
		return txtRlink;
	} // getTxtRlink
	
	private JTextField getTxtParts() {
		if(txtParts == null) {
			txtParts = new JTextField();
		}
		return txtParts;
	} // getTxtParts
	
	private JTextField getTxtCore() {
		if(txtCore == null) {
			txtCore = new JTextField();
		}
		return txtCore;
	} // getTxtCore
	
	private JTextField getTxtInHd() {
		if(txtInHd == null) {
			txtInHd = new JTextField();
		}
		return txtInHd;
	} // getTxtInHd	
	
	private JTextField getTxtOutHd() {
		if(txtOutHd == null) {
			txtOutHd = new JTextField();
		}
		return txtOutHd;
	} // getTxtOutHd	
	
	private JTextField getTxtInCn() {
		if(txtInCn == null) {
			txtInCn = new JTextField();
		}
		return txtInCn;
	} // getTxtInCn	
	
	private JTextField getTxtOutCn() {
		if(txtOutCn == null) {
			txtOutCn = new JTextField();
		}
		return txtOutCn;
	} // getTxtOutCn	
	
	private JTextField getTxtTmnt() {
		if(txtTmnt == null) {
			txtTmnt = new JTextField();
		}
		return txtTmnt;
	} // getTxtTmnt
	
	private JTextField getTxtOil() {
		if(txtOil == null) {
			txtOil = new JTextField();
		}
		return txtOil;
	} // getTxtOil	
	
	private JTextField getTxtPrice() {
		if(txtPrice == null) {
			txtPrice = new JTextField();
		}
		return txtPrice;
	} // getTxtPrice
	
	private JTextField getTxtAmnt() {
		if(txtAmnt == null) {
			txtAmnt = new JTextField();
		}
		return txtAmnt;
	} // getTxtAmnt
		
/*************PANELCOMPONENT*********/	
	private void getPnelComponent() {
		GridBagConstraints consGridBagConstraints1 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints2 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints3 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints4 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints5 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints6 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints7 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints8 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints9 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints10 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints11 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints12 
			= new GridBagConstraints();		
		GridBagConstraints consGridBagConstraints13 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints14 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints15 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints16 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints17 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints18 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints19 
			= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints20 
			= new GridBagConstraints();
		
		GridBagConstraints consGridBagConstraints21 
		= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints22 
		= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints23 
		= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints24 
		= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints25 
		= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints26 
		= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints27 
		= new GridBagConstraints();
		GridBagConstraints consGridBagConstraints28
		= new GridBagConstraints();
		
		//Insets(top, left, bottom, right)
		consGridBagConstraints21.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints21.ipady = 2;
		consGridBagConstraints21.ipadx = 3;
		consGridBagConstraints21.gridy = 0;
		consGridBagConstraints21.gridx = 0;	
		consGridBagConstraints22.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints22.ipady = 2;
		consGridBagConstraints22.ipadx = 200;
		consGridBagConstraints22.gridy = 0;
		consGridBagConstraints22.gridx = 1;
		
		consGridBagConstraints26.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints26.ipady = 2;
		consGridBagConstraints26.ipadx = 3;
		consGridBagConstraints26.gridy = 1;
		consGridBagConstraints26.gridx = 0;	
		consGridBagConstraints27.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints22.ipady = 2;
		consGridBagConstraints27.ipadx = 200;
		consGridBagConstraints27.gridy = 1;
		consGridBagConstraints27.gridx = 1;
		
		consGridBagConstraints23.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints23.ipady = 2;
		consGridBagConstraints23.ipadx = 3;
		consGridBagConstraints23.gridy = 2;
		consGridBagConstraints23.gridx = 0;	
		consGridBagConstraints24.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints24.ipady = 2;
		consGridBagConstraints24.ipadx = 200;
		consGridBagConstraints24.gridy = 2;
		consGridBagConstraints24.gridx = 1;
		consGridBagConstraints25.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints25.ipady = 2;
		consGridBagConstraints25.ipadx = 200;
		consGridBagConstraints25.gridy = 2;
		consGridBagConstraints25.gridx = 1;
		
		consGridBagConstraints1.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints1.ipady = 2;
		consGridBagConstraints1.ipadx = 3;
		consGridBagConstraints1.gridy = 3;
		consGridBagConstraints1.gridx = 0;	
		consGridBagConstraints10.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints10.ipady = 2;
		consGridBagConstraints10.ipadx = 200;
		consGridBagConstraints10.gridy = 3;
		consGridBagConstraints10.gridx = 1;
		
		consGridBagConstraints2.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints2.ipady = 2;
		consGridBagConstraints2.ipadx = 3;
		consGridBagConstraints2.gridy = 4;
		consGridBagConstraints2.gridx = 0;
		consGridBagConstraints11.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints11.ipady = 2;
		consGridBagConstraints11.ipadx = 200;
		consGridBagConstraints11.gridy = 4;
		consGridBagConstraints11.gridx = 1;	
		
		consGridBagConstraints3.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints3.ipady = 2;
		consGridBagConstraints3.ipadx = 3;
		consGridBagConstraints3.gridy = 5;
		consGridBagConstraints3.gridx = 0;		
		consGridBagConstraints12.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints12.ipady = 2;
		consGridBagConstraints12.ipadx = 200;
		consGridBagConstraints12.gridy = 5;
		consGridBagConstraints12.gridx = 1;
		
		consGridBagConstraints4.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints4.ipady = 2;
		consGridBagConstraints4.ipadx = 3;
		consGridBagConstraints4.gridy = 6;
		consGridBagConstraints4.gridx = 0;
		consGridBagConstraints13.insets = new java.awt.Insets(1,1,1,1);	
		consGridBagConstraints13.ipady = 2;
		consGridBagConstraints13.ipadx = 200;
		consGridBagConstraints13.gridy = 6;
		consGridBagConstraints13.gridx = 1;	
		
		consGridBagConstraints5.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints5.ipady = 2;
		consGridBagConstraints5.ipadx = 3;
		consGridBagConstraints5.gridy = 7;
		consGridBagConstraints5.gridx = 0;		
		consGridBagConstraints14.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints14.ipady = 2;
		consGridBagConstraints14.ipadx = 200;
		consGridBagConstraints14.gridy = 7;
		consGridBagConstraints14.gridx = 1;
		
		consGridBagConstraints6.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints6.ipady = 2;
		consGridBagConstraints6.ipadx = 3;
		consGridBagConstraints6.gridy = 8;
		consGridBagConstraints6.gridx = 0;
		consGridBagConstraints15.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints15.ipady = 2;
		consGridBagConstraints15.ipadx = 200;
		consGridBagConstraints15.gridy = 8;
		consGridBagConstraints15.gridx = 1;	
		
		consGridBagConstraints7.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints7.ipady = 2;
		consGridBagConstraints7.ipadx = 3;
		consGridBagConstraints7.gridy = 9;
		consGridBagConstraints7.gridx = 0;	
		consGridBagConstraints16.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints16.ipady = 2;
		consGridBagConstraints16.ipadx = 200;
		consGridBagConstraints16.gridy = 9;
		consGridBagConstraints16.gridx = 1;
		
		consGridBagConstraints8.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints8.ipady = 2;
		consGridBagConstraints8.ipadx = 3;
		consGridBagConstraints8.gridy = 10;
		consGridBagConstraints8.gridx = 0;
		consGridBagConstraints17.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints17.ipady = 2;
		consGridBagConstraints17.ipadx = 200;
		consGridBagConstraints17.gridy = 10;
		consGridBagConstraints17.gridx = 1;	
		
		consGridBagConstraints9.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints9.ipady = 2;
		consGridBagConstraints9.ipadx = 3;
		consGridBagConstraints9.gridy = 11;
		consGridBagConstraints9.gridx = 0;		
		consGridBagConstraints18.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints18.ipady = 2;
		consGridBagConstraints18.ipadx = 200;
		consGridBagConstraints18.gridy = 11;
		consGridBagConstraints18.gridx = 1;
		
		consGridBagConstraints19.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints19.ipady = 2;
		consGridBagConstraints19.ipadx = 3;
		consGridBagConstraints19.gridy = 12;
		consGridBagConstraints19.gridx = 0;		
		consGridBagConstraints20.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints20.ipady = 2;
		consGridBagConstraints20.ipadx = 3;
		consGridBagConstraints20.gridy = 12;
		consGridBagConstraints20.gridx = 2;
		consGridBagConstraints28.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints28.ipady = 2;
		consGridBagConstraints28.ipadx = 3;
		consGridBagConstraints28.gridy = 12;
		consGridBagConstraints28.gridx = 1;
		
		panel.setLayout(new GridBagLayout());

		panel.add(getLblCore(), consGridBagConstraints1);
		panel.add(getLblInHd(), consGridBagConstraints2);
		panel.add(getLblOutHd(), consGridBagConstraints3);
		panel.add(getLblInCn(), consGridBagConstraints4);
		panel.add(getLblOutCn(), consGridBagConstraints5);
		panel.add(getLblTmnt(), consGridBagConstraints6);
		panel.add(getLblOil(), consGridBagConstraints7);
		panel.add(getLblPrice(), consGridBagConstraints8);
		panel.add(getLblAmnt(), consGridBagConstraints9);

		panel.add(getTxtCore(), consGridBagConstraints10);
		panel.add(getTxtInHd(), consGridBagConstraints11);
		panel.add(getTxtOutHd(), consGridBagConstraints12);
		panel.add(getTxtInCn(), consGridBagConstraints13);
		panel.add(getTxtOutCn(), consGridBagConstraints14);
		panel.add(getTxtTmnt(), consGridBagConstraints15);
		panel.add(getTxtOil(), consGridBagConstraints16);
		panel.add(getTxtPrice(), consGridBagConstraints17);
		panel.add(getTxtAmnt(), consGridBagConstraints18);
		
		panel.add(getBtnApply(), consGridBagConstraints19);
		panel.add(getBtnCancel(), consGridBagConstraints20);
		panel.add(getBtnUpdt(), consGridBagConstraints28);
		
		panel.add(getLblVendor(), consGridBagConstraints21);
		panel.add(getLblVndrVariable(), consGridBagConstraints22);
		panel.add(getLblParts(), consGridBagConstraints23);
		panel.add(getLblPrtsVariable(), consGridBagConstraints24);
		panel.add(getTxtParts(), consGridBagConstraints25);
		panel.add(getLblRlink(), consGridBagConstraints26);
		panel.add(getTxtRlink(), consGridBagConstraints27);
		
	} // getPnelComponent	

} /// VendorDataManage
