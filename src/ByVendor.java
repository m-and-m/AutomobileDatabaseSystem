import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

public class ByVendor extends JPanel implements ActionListener {
	// Search button
	private JButton btnSearch = null;
	// Create button
	private JButton btnCreate = null;
	// Modify button
	private JButton btnModify = null;
	// Delete button
	private JButton btnDelete = null;
	// JLabel for Parts comboBox
	private JLabel lblVendor = null;
	// JLabel for Parts comboBox
	private JLabel lblParts = null;
	// Parts comboBox
	private JComboBox<String> comboVendor = null;
	// Parts comboBox
	private JComboBox<String> comboParts = null;
	// JTable for all description
	private static JTable tblDscpn = null;
	// JScroll pane
	private JScrollPane jScrollPane = null;
	
	// Class DescriptionComboModel's alias
	private static DescriptionTableModel dscpnModel;
	// Class SelectionComboModel's alias
	private SelectionComboModel comboMVendor;
	// Class SelectionComboModel's alias
	private SelectionComboModel comboMParts;
	private String[] vendors = {
			"ARS1", "ARS2", "ARS3", "ARS4", 
			"BEH1", "BEH2", "BEH3", "BEH4",
			"DAN1", "DAN2", "DAN3", "DAN4",
			"MOD1", "MOD2", "MOD3", "MOD4"
			};
	
	public ByVendor() {
		super();
		getJContentPane();
		
		// convert arrays into arraylist
		List list = Arrays.asList(vendors);
		ArrayList<String> vendorlist = new ArrayList(list);
		
		// Disable Edit buttons
		getBtnModify().setEnabled(false);
		getBtnDelete().setEnabled(false);
		
		comboMVendor = new SelectionComboModel(vendorlist);
		getComboVendor().setModel(comboMVendor);

	} // ByVendor
	
	public static void refreshDescTable(String v, String p) {
		dscpnModel = new DescriptionTableModel();
		
		try {
			dscpnModel.updateData(DBManager.getResultByVendor(v,p));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		getTblDscpn().setModel(dscpnModel);	
	} // refreshDescTable
/*********ActionSelectionListener*********/
/*************actionPerformed*********/	
		@Override
		public void actionPerformed(ActionEvent e) {
			String vendor = "";
			String parts = "";

			if(e.getSource() == getComboVendor()){
				vendor = getComboVendor().getSelectedItem().toString();
				System.out.println("vendor: "+vendor);
				
				try {
					comboMParts = new SelectionComboModel(DBManager.getAllParts(vendor));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				getComboParts().setModel(comboMParts);	
			} else if (e.getSource() == getBtnSearch()){
				vendor = getComboVendor().getSelectedItem().toString();
				parts = getComboParts().getSelectedItem().toString();
				
				System.out.println("parts id: "+parts);
				
				dscpnModel = new DescriptionTableModel();
				
				try {
					dscpnModel.updateData(DBManager.getResultByVendor(vendor,parts));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				getTblDscpn().setModel(dscpnModel);	
				
				// Enable Edit buttons
				getBtnModify().setEnabled(true);
				getBtnDelete().setEnabled(true);
				
			} else if (e.getSource() == getBtnCreate()) {
				vendor = getComboVendor().getSelectedItem().toString();
				new VendorDataManage(vendor);
				
			} else if (e.getSource() == getBtnModify()) {
				vendor = getComboVendor().getSelectedItem().toString();
				parts = getComboParts().getSelectedItem().toString();
				
				try {
					new VendorDataManage(vendor, parts);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			} else if (e.getSource() == getBtnDelete()) {
				vendor = getComboVendor().getSelectedItem().toString();
				parts = getComboParts().getSelectedItem().toString();						

                int n = JOptionPane.showConfirmDialog(
                        this, "The part number '"+parts+"' in "+vendor+
                        " will be deleted.\n",
                        "Confirm Delete",
                        JOptionPane.WARNING_MESSAGE,
                        JOptionPane.OK_CANCEL_OPTION);
                
                if (n == JOptionPane.OK_OPTION) {
                    System.out.println("Ok option");
                    /**
                     * To delete parts number from system,
                     * change/delete the parts number from radcrx FIRST,
                     * and then delete data from rdim***
                     */
                    try {
                    	DBManager.deleteDataFromRADCRX(vendor, parts);
             		} catch (SQLException e1) {
             			e1.printStackTrace();
             		}
                             
                    try {
						DBManager.deleteDataFromRDIM(vendor, parts);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
                
                    // refresh parts comboBox + desc table
					try {
						comboMParts = new SelectionComboModel(DBManager.getAllParts(vendor));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}						
					getComboParts().setModel(comboMParts);
					
					try {
						dscpnModel.updateData(DBManager.getResultByVendor(vendor,parts));
					} catch (SQLException e1) {	
						e1.printStackTrace();
					}
                } else if (n == JOptionPane.CANCEL_OPTION) {
                	System.out.println("Cancel option");
                } 
			} //  end of else-if
		} // actionPerformed
		
/*********JBUTTON*********/	
		private JButton getBtnSearch() {
			if(btnSearch == null) {
				btnSearch = new JButton("Search");
				//btnSearch.setMnemonic(java.awt.event.KeyEvent.VK_S);
				btnSearch.addActionListener(this);
			}	
			return btnSearch;
		} // getBtnSearch
		
		private JButton getBtnCreate() {
			if(btnCreate == null) {
				btnCreate = new JButton("Create");
				//btnSearch.setMnemonic(java.awt.event.KeyEvent.VK_S);
				btnCreate.addActionListener(this);
			}	
			return btnCreate;
		} // getBtnCreate
		
		private JButton getBtnModify() {
			if(btnModify == null) {
				btnModify = new JButton("Modify");
				//btnModify.setMnemonic(java.awt.event.KeyEvent.VK_S);
				btnModify.addActionListener(this);
			}	
			return btnModify;
		} // getBtnModify
		
		private JButton getBtnDelete() {
			if(btnDelete == null) {
				btnDelete = new JButton("Delete");
				//btnDelete.setMnemonic(java.awt.event.KeyEvent.VK_S);
				btnDelete.addActionListener(this);
			}	
			return btnDelete;
		} // getBtnDelete
		
/*********JLABEL*********/	
		private JLabel getLblVendor(){
			if(lblVendor == null) {
				lblVendor = new JLabel("Vendor: ");
				//lblModel.setHorizontalAlignment(defaultCloseOperation)
			}
			return lblVendor;
		} // getLblVendor	
		
		private JLabel getLblParts(){
			if(lblParts  == null) {
				lblParts  = new JLabel("Parts: ");
				//lblModel.setHorizontalAlignment(defaultCloseOperation)
			}
			return lblParts ;
		} // getLblParts	
		
/*********JCOMBO*********/	
		private JComboBox<String> getComboVendor(){
			if(comboVendor == null) {
				comboVendor = new JComboBox<String>();
				comboVendor.addActionListener(this);
			}
			return comboVendor;
		} // getComboVendor
		
		private JComboBox<String> getComboParts(){
			if(comboParts == null) {
				comboParts = new JComboBox<String>();
				comboParts.addActionListener(this);
			}
			return comboParts;
		} // getComboParts
		
/*********JTABLE*********/	
		private static JTable getTblDscpn() {
			if(tblDscpn == null) {
				tblDscpn = new JTable();
				//tblDscpn.setEnabled(false);
				//tblDscpn.getSelectionModel().addListSelectionListener(this);
				//tblDscpn.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			}
			return tblDscpn;
		} // getTblDscpn

/*********JSCROLL*********/	
		private JScrollPane getJScrollPane() {
			if(jScrollPane == null) {
				jScrollPane = new JScrollPane();
				jScrollPane.setViewportView(getTblDscpn());
			}
			return jScrollPane;
		} // getJScrollPane	
				
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
		//Insets(top, left, bottom, right)
		consGridBagConstraints1.insets = new java.awt.Insets(1,1,1,1);
		//consGridBagConstraints1.gridheight = 0;
		consGridBagConstraints1.ipady = 2;
		consGridBagConstraints1.ipadx = 3;
		consGridBagConstraints1.gridy = 0;
		consGridBagConstraints1.gridx = 0;
	
		consGridBagConstraints2.insets = new java.awt.Insets(1,1,1,1);
		//consGridBagConstraints2.gridheight = 0;
		consGridBagConstraints2.ipadx = 3;
		consGridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints2.weightx = 1;
		//consGridBagConstraints2.gridwidth = 2;
		consGridBagConstraints2.gridy = 0;
		consGridBagConstraints2.gridx = 1;
		
		consGridBagConstraints3.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints3.ipady = 2;
		consGridBagConstraints3.ipadx = 3;
		consGridBagConstraints3.gridy = 0;
		consGridBagConstraints3.gridx = 4;
		
		consGridBagConstraints4.insets = new java.awt.Insets(3,1,3,1);
		consGridBagConstraints4.ipady = 200;
		consGridBagConstraints4.ipadx = 500;
		consGridBagConstraints4.fill = java.awt.GridBagConstraints.BOTH;
		//consGridBagConstraints3.weighty = 10;
		consGridBagConstraints4.weightx = 10;
		consGridBagConstraints4.gridwidth = 5;
		consGridBagConstraints4.gridy = 1;
		consGridBagConstraints4.gridx = 0;
			
		consGridBagConstraints5.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints5.ipady = 0;
		consGridBagConstraints5.ipadx = 3;
		consGridBagConstraints5.gridy = 0;
		consGridBagConstraints5.gridx = 2;
	
		consGridBagConstraints6.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints6.ipadx = 10;
		consGridBagConstraints6.fill = java.awt.GridBagConstraints.HORIZONTAL;
		consGridBagConstraints6.weightx = 1;
		//consGridBagConstraints6.weightx = 1.0;
		//consGridBagConstraints6.gridwidth = 2;
		consGridBagConstraints6.gridy = 0;
		consGridBagConstraints6.gridx = 3;
		
		
		consGridBagConstraints7.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints7.ipady = 2;
		consGridBagConstraints7.ipadx = 3;
		consGridBagConstraints7.gridy = 5;
		consGridBagConstraints7.gridx = 1;
		
		
		consGridBagConstraints8.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints8.ipady = 2;
		consGridBagConstraints8.ipadx = 3;
		consGridBagConstraints8.gridy = 5;
		consGridBagConstraints8.gridx = 2;
		
		
		consGridBagConstraints9.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints9.ipady = 2;
		consGridBagConstraints9.ipadx = 3;
		consGridBagConstraints9.gridy = 5;
		consGridBagConstraints9.gridx = 3;
		
		this.setLayout(new java.awt.GridBagLayout());
		this.add(getLblVendor(), consGridBagConstraints1);
		this.add(getComboVendor(), consGridBagConstraints2);
		this.add(getBtnSearch(), consGridBagConstraints3);
		this.add(getJScrollPane(), consGridBagConstraints4);
		this.add(getLblParts(), consGridBagConstraints5);
		this.add(getComboParts(), consGridBagConstraints6);
		this.add(getBtnCreate(), consGridBagConstraints7);
		this.add(getBtnModify(), consGridBagConstraints8);
		this.add(getBtnDelete(), consGridBagConstraints9);
		
	} // getJContentPane
} /// ByVendor
