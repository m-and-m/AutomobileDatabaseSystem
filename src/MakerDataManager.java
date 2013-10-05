import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class MakerDataManager extends JFrame implements ActionListener {
	// Panel
	private JPanel panel = new JPanel();

	// JLabel for Maker 
	private JLabel lblMaker = null;
	// JLabel for Maker Variable
	private JLabel lblMkrV = null;
	
	// JLabel for Model 
	private JLabel lblModel = null;
	// JLabel for Model Variable
	private JLabel lblMdlV = null;
	
	// JLabel for Year 
	private JLabel lblYear = null;
	// JLabel for Year Variable
	private JLabel lblYrV = null;
	
	// JLabel for Rlink 
	private JLabel lblRlink = null;
	// JLabel for Rlink Variable
	private JLabel lblRlinkV = null;
	
	// JLabel for Description 
	private JLabel lblDesc = null;
	// JLabel for Liters
	private JLabel lblLtr = null;	
	// JLabel for Engine_type
	private JLabel lblEng = null;
	// JLabel for Cubic_inches
	private JLabel lblCid = null;
	
	
	// Model JTextfield
	private JTextField txtModel = null;
	// Year JTextfield
	private JTextField txtYear = null;
	// Rlink JTextfield
	private JTextField txtRlink = null;
	// Description JTextfield
	private JTextField txtDesc = null;
	// Liters JTextfield
	private JTextField txtLtr = null;
	// Engine_type JTextfield
	private JTextField txtEng = null;
	// Cubic_inches JTextfield
	private JTextField txtCid = null;

	// Apply button
	private JButton btnApply = null;
	// Cancel button
	private JButton btnCancel = null;
	
	private String nameMaker = "";
	private String nameModel = "";
	private String valYear = "";
	private String valRlink = "";
	private String valCid = "";
	
	// Class SelectionComboModel's alias
	private SelectionComboModel comboMMaker;
	// Maker comboBox
	private JComboBox<String> comboMaker = null;
/*********BODY
 * @throws SQLException *********/
	/*
	 * Create NEW data with blank...
	 */
	public MakerDataManager() throws SQLException {
		super();
		frame_initialize();
		this.setTitle("Create New Data From Blank");
		
		comboMMaker= new SelectionComboModel(DBManager.getMakerList());
		getComboMaker().setModel(comboMMaker);

		getLblMkrV().setEnabled(false);
		getLblMdlV().setEnabled(false);
		getLblYrV().setEnabled(false);
		getLblRlinkV().setEnabled(false);
		
		getPnelComponent();
		getContentPane().add(panel);
		
		setVisible(true);

	} // MakerDataManager

	/*
	 * For create new data, passed only vendor name 
	 */
	public MakerDataManager(String maker) {
		super();
		frame_initialize();
		this.setTitle("Create New Data");
		
		nameMaker = maker;
		
		getLblMdlV().setEnabled(false);
		getLblYrV().setEnabled(false);
		getLblRlinkV().setEnabled(false);
		
		getPnelComponent();
		getContentPane().add(panel);
		
		setVisible(true);

	} // MakerDataManager
	
	/*
	 * For modify data, passed vendor name and parts number
	 */
	public MakerDataManager(String maker, String model, String year, String cid, String rlink) throws SQLException {
		super();
		frame_initialize();
		this.setTitle("Modify Data");
		
		nameMaker = maker;
		nameModel = model;
		valYear = year;
		valRlink = rlink;
		valCid = cid;
		
		getTxtModel().show(false);
		getTxtYear().show(false);
		getTxtRlink().show(false);
		 
		// copy all description from main win. into "Modify Data" win.
		copyDataIntoTxtFields(nameMaker, nameModel, valYear, valCid, valRlink);
		
		getPnelComponent();
		getContentPane().add(panel);
		
		setVisible(true);

	} // MakerDataManager
	
	private void copyDataIntoTxtFields(String mk, String md, String yr, String cid, String rl) throws SQLException {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

		data = DBManager.getDescriptionFromMkrMgmt(mk, md, yr, cid, rl);
		data.remove(0);
		
		System.out.println("@MakerDataMgmt/copydata");
		System.out.println(data.toString());
		
		for (int curridx = 0 ;curridx < 4; curridx++) {
			System.out.println(data.get(0).get(curridx));
			getFieldByIndex(curridx).setText(data.get(0).get(curridx));
		}
		
 	} // copyDataIntoTxtFields

	private JTextField getFieldByIndex(int index) {
		JTextField txtField = null;
		switch (index) {
	 		case 0: txtField = getTxtDesc();
	 				break;
	 		case 1: txtField = getTxtLtr();
					break;
	 		case 2: txtField = getTxtEng();
					break;
	 		case 3: txtField = getTxtCid();
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
				String desc = getTxtDesc().getText();
				String ltr = getTxtLtr().getText();
				String eng = getTxtEng().getText();
				String cid = getTxtCid().getText();
				
				if(this.getTitle().equalsIgnoreCase("Create New Data")){				
					String model = getTxtModel().getText();
					String year = getTxtYear().getText();
					String rlink = getTxtRlink().getText();
					
					try {
						DBManager.insertNewRlink(rlink);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						DBManager.insertNewMakerData(nameMaker, model, year, desc, ltr, eng, cid, rlink);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(this,
	                        "'"+nameMaker+" ("+model+" in "+year+") - " +
	                        		""+desc+" "+ltr+" "+eng+" "+cid+"' is added successfully");									
					dispose();
				} else if(this.getTitle().equalsIgnoreCase("Create New Data From Blank")) {
					String maker = getComboMaker().getSelectedItem().toString();
					String model = getTxtModel().getText();
					String year = getTxtYear().getText();
					String rlink = getTxtRlink().getText();
					
					try {
						DBManager.insertNewRlink(rlink);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						DBManager.insertNewMakerData(maker, model, year, desc, ltr, eng, cid, rlink);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(this,
	                        "'"+maker+" ("+model+" in "+year+") - " +
	                        		""+desc+" "+ltr+" "+eng+" "+cid+"' is added successfully");									
					dispose();
					
				} else if(this.getTitle().equalsIgnoreCase("Modify Data")) {
					String model = getLblMdlV().getText();
					String year = getLblYrV().getText();
					String rlink = getLblRlinkV().getText();
					
					try {
						DBManager.updateMakerData(nameMaker, model, year, desc, ltr, eng, cid, rlink, valCid);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(this,
							"'"+nameMaker+" ("+model+" in "+year+") - " +
	                        		""+desc+" "+ltr+" "+eng+" "+cid+"' is updated successfully");
					
//FIXME REFRESH				
					dispose();
				} // end of inside if
			} else if (e.getSource() == getBtnCancel()) {
				dispose();
			} // end of outside if

		} // actionPerformed
/*********JCOMBO*********/	
	private JComboBox<String> getComboMaker(){
		if(comboMaker  == null) {
			comboMaker  = new JComboBox<String>();
			comboMaker .addActionListener(this);
		}
		return comboMaker ;
	} // getComboMaker		
/*********JLABEL*********/	
	private JLabel getLblMaker(){
		if(lblMaker == null) {
			lblMaker = new JLabel("Maker: ");
		}
		return lblMaker;
	} // getLblMaker	

	private JLabel getLblMkrV(){
		if(lblMkrV == null) {
			lblMkrV = new JLabel("");
		}
		lblMkrV = new JLabel(nameMaker);
		return lblMkrV;
	} // getLblMkrV	

	private JLabel getLblModel(){
		if(lblModel == null) {
			lblModel = new JLabel("*Model: ");
			//lblModel.setHorizontalAlignment(defaultCloseOperation)
		}
		return lblModel;
	} // getLblModel	

	private JLabel getLblMdlV(){
		if(lblMdlV == null) {
			lblMdlV = new JLabel("");
		}
		lblMdlV = new JLabel(nameModel);
		return lblMdlV;
	} // getLblMdlV	

	private JLabel getLblYear(){
		if(lblYear == null) {
			lblYear = new JLabel("*Year: ");
		}
		return lblYear;
	} // getLblYear	

	private JLabel getLblYrV(){
		if(lblYrV == null) {
			lblYrV = new JLabel("");
			//lblModel.setHorizontalAlignment(defaultCloseOperation)
		}
		lblYrV = new JLabel(valYear);
		return lblYrV;
	} // getLblYrV	

	private JLabel getLblRlinkV(){
		if(lblRlinkV == null) {
			lblRlinkV = new JLabel("");
			//lblModel.setHorizontalAlignment(defaultCloseOperation)
		}
		lblRlinkV = new JLabel(valRlink);
		return lblRlinkV;
	} // getLblRlinkV	
	
	private JLabel getLblRlink(){
		if(lblRlink == null) {
			lblRlink = new JLabel("*Rlink(4 digits): ");
		}
		return lblRlink;
	} // getLblRlink	
	
	private JLabel getLblDesc(){
		if(lblDesc == null) {
			lblDesc = new JLabel("*Description: ");
		}
		return lblDesc;
	} // getLblDesc	

	private JLabel getLblLtr(){
		if(lblLtr == null) {
			lblLtr = new JLabel("*Liter: ");
		}
		return lblLtr;
	} // getLblLtr	

	private JLabel getLblEng(){
		if(lblEng == null) {
			lblEng = new JLabel("*Engine: ");
			//lblModel.setHorizontalAlignment(defaultCloseOperation)
		}
		return lblEng;
	} // getLblEng	

	private JLabel getLblCid(){
		if(lblCid == null) {
			lblCid = new JLabel("*Cubic_inches: ");
		}
		return lblCid;
	} // getLblCid

/*********JTEXTFIELD*********/	
	private JTextField getTxtModel() {
		if(txtModel == null) {
			txtModel = new JTextField();
		}
		return txtModel;
	} // getTxtModel
	
	private JTextField getTxtYear() {
		if(txtYear == null) {
			txtYear = new JTextField();
		}
		return txtYear;
	} // getTxtYear
	
	private JTextField getTxtRlink() {
		if(txtRlink == null) {
			txtRlink = new JTextField();
		}
		return txtRlink;
	} // getTxtRlink	

	private JTextField getTxtDesc() {
		if(txtDesc == null) {
			txtDesc = new JTextField();
		}
		return txtDesc;
	} // getTxtDesc
	
	private JTextField getTxtLtr() {
		if(txtLtr == null) {
			txtLtr = new JTextField();
		}
		return txtLtr;
	} // getTxtLtr
	
	private JTextField getTxtEng() {
		if(txtEng == null) {
			txtEng = new JTextField();
		}
		return txtEng;
	} // getTxtEng	
	
	private JTextField getTxtCid() {
		if(txtCid == null) {
			txtCid = new JTextField();
		}
		return txtCid;
	} // getTxtCid	

/*********JBUTTON*********/	
	private JButton getBtnApply() {
		if(btnApply == null) {
			btnApply = new JButton("Apply");
			//btnSearch.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnApply.addActionListener(this);
		}	
		return btnApply;
	} // getBtnApply
	
	private JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new JButton("Cancel");
			//btnSearch.setMnemonic(java.awt.event.KeyEvent.VK_S);
			btnCancel.addActionListener(this);
		}	
		return btnCancel;
	} // getBtnCancel
	
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
		
		//Insets(top, left, bottom, right)
		consGridBagConstraints1.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints1.ipady = 2;
		consGridBagConstraints1.ipadx = 3;
		consGridBagConstraints1.gridy = 4;
		consGridBagConstraints1.gridx = 0;	
		consGridBagConstraints2.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints2.ipady = 2;
		consGridBagConstraints2.ipadx = 200;
		consGridBagConstraints2.gridy = 4;
		consGridBagConstraints2.gridx = 1;
		
		consGridBagConstraints3.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints3.ipady = 2;
		consGridBagConstraints3.ipadx = 3;
		consGridBagConstraints3.gridy = 5;
		consGridBagConstraints3.gridx = 0;		
		consGridBagConstraints4.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints4.ipady = 2;
		consGridBagConstraints4.ipadx = 200;
		consGridBagConstraints4.gridy = 5;
		consGridBagConstraints4.gridx = 1;
		
		consGridBagConstraints5.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints5.ipady = 2;
		consGridBagConstraints5.ipadx = 3;
		consGridBagConstraints5.gridy = 6;
		consGridBagConstraints5.gridx = 0;		
		consGridBagConstraints6.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints6.ipady = 2;
		consGridBagConstraints6.ipadx = 200;
		consGridBagConstraints6.gridy = 6;
		consGridBagConstraints6.gridx = 1;

		consGridBagConstraints7.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints7.ipady = 2;
		consGridBagConstraints7.ipadx = 3;
		consGridBagConstraints7.gridy = 7;
		consGridBagConstraints7.gridx = 0;		
		consGridBagConstraints8.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints8.ipady = 2;
		consGridBagConstraints8.ipadx = 200;
		consGridBagConstraints8.gridy = 7;
		consGridBagConstraints8.gridx = 1;

		consGridBagConstraints9.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints9.ipady = 2;
		consGridBagConstraints9.ipadx = 3;
		consGridBagConstraints9.gridy = 8;
		consGridBagConstraints9.gridx = 0;		
		consGridBagConstraints10.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints10.ipady = 2;
		consGridBagConstraints10.ipadx = 3;
		consGridBagConstraints10.gridy = 8;
		consGridBagConstraints10.gridx = 1;
		
		consGridBagConstraints11.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints11.ipady = 2;
		consGridBagConstraints11.ipadx = 3;
		consGridBagConstraints11.gridy = 0;
		consGridBagConstraints11.gridx = 0;		
		consGridBagConstraints12.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints12.ipady = 2;
		consGridBagConstraints12.ipadx = 3;
		consGridBagConstraints12.gridy = 0;
		consGridBagConstraints12.gridx = 1;
		consGridBagConstraints22.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints22.ipady = 2;
		consGridBagConstraints22.ipadx = 3;
		consGridBagConstraints22.gridy = 0;
		consGridBagConstraints22.gridx = 1;
		
		consGridBagConstraints13.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints13.ipady = 2;
		consGridBagConstraints13.ipadx = 3;
		consGridBagConstraints13.gridy = 2;
		consGridBagConstraints13.gridx = 0;		
		consGridBagConstraints14.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints14.ipady = 2;
		consGridBagConstraints14.ipadx = 3;
		consGridBagConstraints14.gridy = 2;
		consGridBagConstraints14.gridx = 1;
		consGridBagConstraints15.insets = new java.awt.Insets(1,1,1,1);
		consGridBagConstraints15.ipady = 2;
		consGridBagConstraints15.ipadx = 200;
		consGridBagConstraints15.gridy = 2;
		consGridBagConstraints15.gridx = 1;		
		
		consGridBagConstraints16.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints16.ipady = 2;
		consGridBagConstraints16.ipadx = 3;
		consGridBagConstraints16.gridy = 3;
		consGridBagConstraints16.gridx = 0;
		consGridBagConstraints17.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints17.ipady = 2;
		consGridBagConstraints17.ipadx = 3;
		consGridBagConstraints17.gridy = 3;
		consGridBagConstraints17.gridx = 1;		
		consGridBagConstraints18.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints18.ipady = 2;
		consGridBagConstraints18.ipadx = 200;
		consGridBagConstraints18.gridy = 3;
		consGridBagConstraints18.gridx = 1;
		
		consGridBagConstraints19.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints19.ipady = 2;
		consGridBagConstraints19.ipadx = 3;
		consGridBagConstraints19.gridy = 1;
		consGridBagConstraints19.gridx = 0;		
		consGridBagConstraints20.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints20.ipady = 2;
		consGridBagConstraints20.ipadx = 3;
		consGridBagConstraints20.gridy = 1;
		consGridBagConstraints20.gridx = 1;
		consGridBagConstraints21.insets = new java.awt.Insets(20,1,20,1);
		consGridBagConstraints21.ipady = 2;
		consGridBagConstraints21.ipadx = 200;
		consGridBagConstraints21.gridy = 1;
		consGridBagConstraints21.gridx = 1;
		
		panel.setLayout(new GridBagLayout());
		panel.add(getLblDesc(), consGridBagConstraints1);
		panel.add(getTxtDesc(), consGridBagConstraints2);
		panel.add(getLblLtr(), consGridBagConstraints3);
		panel.add(getTxtLtr(), consGridBagConstraints4);
		panel.add(getLblEng(), consGridBagConstraints5);
		panel.add(getTxtEng(), consGridBagConstraints6);
		panel.add(getLblCid(), consGridBagConstraints7);
		panel.add(getTxtCid(), consGridBagConstraints8);

		panel.add(getBtnApply(), consGridBagConstraints9);
		panel.add(getBtnCancel(), consGridBagConstraints10);
		
		panel.add(getLblMaker(), consGridBagConstraints11);
		panel.add(getLblMkrV(), consGridBagConstraints12);
		panel.add(getLblModel(), consGridBagConstraints13);
		panel.add(getLblMdlV(), consGridBagConstraints14);
		panel.add(getTxtModel(), consGridBagConstraints15);
		panel.add(getLblYear(), consGridBagConstraints16);
		panel.add(getLblYrV(), consGridBagConstraints17);
		panel.add(getTxtYear(), consGridBagConstraints18);
		panel.add(getLblRlink(), consGridBagConstraints19);
		panel.add(getLblRlinkV(), consGridBagConstraints20);
		panel.add(getTxtRlink(), consGridBagConstraints21);
		
		panel.add(getComboMaker(), consGridBagConstraints22);
		
		
	} // getPnelComponent
	
} /// MakerDataManager
