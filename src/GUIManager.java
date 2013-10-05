import java.sql.*;

import javax.swing.*;

public class GUIManager extends JFrame  {	
	// create a JTabbedPane
	private JTabbedPane tabbed = null;
	
	public static void main(String[] args) throws SQLException {
		GUIManager f = new GUIManager();
		f.setVisible(true);
	} // main
	
	public GUIManager() throws SQLException {
		super();
		new DBManager();
		initialize();
		
		//add a content into JTabbedPane
		tabbed = new JTabbedPane();
		tabbed.add("ByUser", new ByUser());
		tabbed.add("ByMaker", new ByMaker());
		tabbed.add("ByVendor", new ByVendor());

		// add tabbs to the window
		getContentPane().add(tabbed);
	} // GUIManager
	
	private void initialize() {
		this.setSize(800,600);
		this.setTitle("Auto Search");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	} // initialize

} /// GUIManager
