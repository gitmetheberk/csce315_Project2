import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class SQL_GUI extends JFrame {
	// JDBC -------------------------------------------------------
	private SQL_JDBC jdbc;
	private SQL_CommandLineInterpreter CLI;

	private JPanel primaryPanel;
	
	CardLayout layout = new CardLayout();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menu_DBInformation = new JMenu("Database Information");
	private final JMenuItem menuItem_showAllTables = new JMenuItem("Show all tables");
	private final JMenuItem menuItem_showAllColumns = new JMenuItem("Show all columns");
	private final JMenu menu_tableRelationships = new JMenu("Table Relationships");
	private final JMenuItem menuItem_showRelatedTables = new JMenuItem("Show related tables");
	private final JMenuItem menuItem_searchPath = new JMenuItem("Search path");
	private final JMenuItem menuItem_searchAndJoin = new JMenuItem("Search path and join");
	private final JMenuItem menuItem_joinAndShowTables = new JMenuItem("Join and show tables");
	private final JMenuItem menuItem_showSelectedColumns = new JMenuItem("Show selected columns");
	private final JMenu menu_IDResolvers = new JMenu("ID Resolvers");
	private final JMenuItem menuItem_ProductIDResolver = new JMenuItem("ProductID");
	private final JMenuItem menuItem_LocationIDResolver = new JMenuItem("LocationID");
	private final JMenuItem menuItem_showAllPrimaryKeys = new JMenuItem("Show all primary keys");
	private final JMenu menu_analytics = new JMenu("Analytics");
	private final JMenuItem menuItem_plotSchema = new JMenuItem("Show schema plot");
	private final JMenuItem menuItem_stat = new JMenuItem("Column statistics");
	private final JMenu menu_misc = new JMenu("Miscellaneous ");
	private final JMenuItem menuItem_findColumn = new JMenuItem("Find column");
	private final JMenuItem menuItem_getView = new JMenuItem("Create view");
	private final Component horizontalStrut = Box.createHorizontalStrut(520);
	private final JMenuItem menuItem_help = new JMenuItem("Help");
	private final JMenuItem menuItem_commandLine = new JMenuItem("Command line");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SQL_GUI frame = new SQL_GUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SQL_GUI(SQL_JDBC _jdbc) {
		// JDBC init -------------------------------------------------------
		// Determine whether or not to launch login prompt
		if (_jdbc == null) {
			// Connect to the database
			if (login()) {
				// Initialize the CLI with the existing jdbc
				CLI = new SQL_CommandLineInterpreter(jdbc);
				
				initGUI();
				
			} else {
				// User has closed/cancelled connection, abort
				dispose();
				System.exit(1);
			}
		} else {
			// Must have been launched with a pre-existing JDBC
			jdbc = _jdbc;
			CLI = new SQL_CommandLineInterpreter(jdbc);
			initGUI();
		}
		
	}
	private void initGUI() {
		setTitle("AdventureWorks Database Client");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		primaryPanel = new JPanel();
		primaryPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		primaryPanel.setLayout(new BorderLayout(0, 0));
		//setContentPane(primaryPanel);
		
		// Panel setup -------------------------------------------------------
		
		// Define JPanels and initialize each
		JPanel _showAllTables = 		new showAllTables(CLI);
		JPanel _showAllColumns =		new showAllColumns(CLI);
		JPanel _showAllPrimaryKeys = 	new showAllPrimaryKeys(CLI);
		JPanel _plotSchema =			new plotSchema(CLI);
		JPanel _searchPath = 			new searchPath(CLI);
		JPanel _searchAndJoin = 		new searchAndJoin(jdbc);
		JPanel _joinAndShowTables = 	new joinAndShowTables(jdbc);
		JPanel _showSelectedColumns = 	new showSelectedColumns(jdbc);
		JPanel _showRelatedTables = 	new showRelatedTables(CLI);
		JPanel _ProductIDResolver = 	new ProductIDResolver(jdbc);
		JPanel _LocationIDResolver = 	new LocationIDResolver(jdbc);
		JPanel _stat = 					new stat(CLI);
		JPanel _findColumn = 			new findColumn(CLI);
		JPanel _getView = 				new getView(CLI, jdbc);
		JPanel _help = 					new help();
		JPanel _commandLine = 			new commandLine(CLI);
		
		// Add JPanels to the primaryPanel with their constraints
		primaryPanel.setLayout(layout);
		primaryPanel.add(_showAllTables, 		"showAllTables");
		primaryPanel.add(_showAllColumns, 		"showAllColumns");
		primaryPanel.add(_showAllPrimaryKeys, 	"showAllPrimaryKeys");
		primaryPanel.add(_plotSchema, 			"plotSchema");
		primaryPanel.add(_searchPath, 			"searchPath");
		primaryPanel.add(_searchAndJoin, 		"searchAndJoin");
		primaryPanel.add(_joinAndShowTables, 	"joinAndShowTables");
		primaryPanel.add(_showSelectedColumns, 	"showSelectedColumns");
		primaryPanel.add(_showRelatedTables, 	"showRelatedTables");
		primaryPanel.add(_ProductIDResolver, 	"ProductIDResolver");
		primaryPanel.add(_LocationIDResolver, 	"LocationIDResolver");
		primaryPanel.add(_stat, 				"stat");
		primaryPanel.add(_findColumn, 			"findColumn");
		primaryPanel.add(_getView, 				"getView");
		primaryPanel.add(_help, 				"help");
		primaryPanel.add(_commandLine, 			"commandLine");
		
		// Set default panel
		layout.show(primaryPanel, "help");
		
		getContentPane().add(primaryPanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		// GUI -------------------------------------------------------
		
		setJMenuBar(menuBar);
		
		menuBar.add(menu_DBInformation);
		menuItem_showAllTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "showAllTables");
			}
		});
		
		menu_DBInformation.add(menuItem_showAllTables);
		menuItem_showAllColumns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "showAllColumns");
			}
		});
		
		menu_DBInformation.add(menuItem_showAllColumns);
		menuItem_showAllPrimaryKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "showAllPrimaryKeys");
			}
		});
		
		menu_DBInformation.add(menuItem_showAllPrimaryKeys);
		menuItem_plotSchema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "plotSchema");
			}
		});
		
		menu_DBInformation.add(menuItem_plotSchema);
		
		menuBar.add(menu_tableRelationships);
		menuItem_searchPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "searchPath");
			}
		});
		
		menu_tableRelationships.add(menuItem_searchPath);
		menuItem_searchAndJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "searchAndJoin");
			}
		});
		
		menu_tableRelationships.add(menuItem_searchAndJoin);
		menuItem_showRelatedTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "showRelatedTables");
			}
		});
		
		menu_tableRelationships.add(menuItem_showRelatedTables);
		
		menu_tableRelationships.add(menuItem_joinAndShowTables);
		menuItem_joinAndShowTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "joinAndShowTables");
			}
		});
		
		menuBar.add(menu_IDResolvers);
		menuItem_ProductIDResolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "ProductIDResolver");
			}
		});
		
		menu_IDResolvers.add(menuItem_ProductIDResolver);
		menuItem_LocationIDResolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "LocationIDResolver");
			}
		});
		
		menu_IDResolvers.add(menuItem_LocationIDResolver);
		
		menuBar.add(menu_analytics);
		menuItem_stat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "stat");
			}
		});
		
		menu_analytics.add(menuItem_stat);
		
		menuBar.add(menu_misc);
		menuItem_findColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "findColumn");
			}
		});
		
		menu_misc.add(menuItem_findColumn);
		menuItem_getView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "getView");
			}
		});
		menu_misc.add(menuItem_showSelectedColumns);
		menuItem_showSelectedColumns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "showSelectedColumns");
			}
		});
		
		menu_misc.add(menuItem_getView);
		menuItem_commandLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "commandLine");
			}
		});
		
		menu_misc.add(menuItem_commandLine);
		
		menuBar.add(horizontalStrut);
		menuItem_help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "help");
			}
		});
		
		menuBar.add(menuItem_help);
	}
	
	// Login panel ------------------------------------------------------
	private JPanel contentPane;
	private final JTextField textField_user = new JTextField();
	private final JTextField textField_pass = new JTextField();
	private final JTextField textField_URL = new JTextField();
	private final JLabel label_url = new JLabel("DB URL");
	private final JLabel label_userid = new JLabel("UserID");
	private final JLabel label_password = new JLabel("Password");
	private final JButton button_DEVLOGIN = new JButton("DEV");
	
	boolean tryConnect() {
		String url = textField_URL.getText();
		String user = textField_user.getText();
		String pass = textField_pass.getText();
		
		if (url.isEmpty() || user.isEmpty() || pass.isEmpty()) {
			JOptionPane.showMessageDialog(null, "ERROR: One or more fields is empty");
			return false;
		}
		
		// Append jdbc:mysql:// if needed
		if (!url.contains("jdbc:mysql://")) {	
			url = "jdbc:mysql://" + url;
		}
		
		// Try to connect
		boolean connected = jdbc.connect(url, user, pass);
		
		if (!connected) {
			JOptionPane.showMessageDialog(null, "ERROR: Could not connect to database, please try again");
		}
		
		return connected;
	}
	
	// Function used to login with oneclick for development
	boolean tryConnectDEV() {
		return jdbc.connect("jdbc:mysql://localhost:3306", "user2", "c8kPA8eHaXsBNEPE");
	}
	
	boolean login() {
		// Initialize the login panel
		contentPane = new JPanel();
		contentPane.setBounds(100, 100, 320, 238);
		//contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		textField_user.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					textField_pass.requestFocusInWindow();
				}
			}
		});
		textField_user.setBounds(85, 56, 212, 30);
		textField_user.setColumns(10);
		
		contentPane.add(textField_user);
		textField_pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tryConnect();
				}
			}
		});
		textField_pass.setColumns(10);
		textField_pass.setBounds(85, 102, 212, 30);
		
		contentPane.add(textField_pass);
		
		textField_URL.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
					textField_user.requestFocusInWindow();
				}
			}
		});
		textField_URL.setColumns(10);
		textField_URL.setBounds(85, 10, 212, 30);
		
		contentPane.add(textField_URL);
		label_url.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_url.setBounds(10, 10, 58, 30);
		
		contentPane.add(label_url);
		label_userid.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_userid.setBounds(10, 56, 58, 30);
		
		contentPane.add(label_userid);
		label_password.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_password.setBounds(10, 102, 68, 30);
		
		contentPane.add(label_password);
		button_DEVLOGIN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_URL.setText("DEV credentials");
				if (tryConnectDEV()) {
					textField_user.setText("Status: Connected");
					textField_pass.setText("Click \"Connect\" to continue");
				} else {
					textField_user.setText("Status: Unable to connect");
					textField_pass.setText("Click \"DEV\" to try again");
				}
			}
		});
		button_DEVLOGIN.setBackground(Color.RED);
		button_DEVLOGIN.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_DEVLOGIN.setBounds(7, 38, 68, 19);
		
		//contentPane.add(button_DEVLOGIN);
		
		// Initialize the JDBC in default mode
		jdbc = new SQL_JDBC(false);
		
		// Adjust JOptionPane to suit needs
		UIManager.put("OptionPane.minimumSize",new Dimension(350, 200)); 
		UIManager.put("OptionPane.yesButtonText", "Connect");
		UIManager.put("OptionPane.noButtonText", "Cancel");
		
		// Loop until connected or cancelled
		int result;
		while (!jdbc.isConnected()) {
			result = JOptionPane.showConfirmDialog(this, contentPane, "AdventureWorks Client Login", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.YES_OPTION) {
				if (!jdbc.isConnected()) {
					tryConnect();
				} else {
					break;
				}
			} else {
				return false;
			}
		}
		
		// Change options back
		UIManager.put("OptionPane.minimumSize",new Dimension(200,100)); 
		UIManager.put("OptionPane.yesButtonText", "Yes");
		UIManager.put("OptionPane.noButtonText", "No");
		
		return true;
	}
}
