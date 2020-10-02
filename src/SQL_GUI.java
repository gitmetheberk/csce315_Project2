import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

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
	private final Component horizontalStrut = Box.createHorizontalStrut(523);
	private final JMenuItem menuItem_help = new JMenuItem("Help");
	private final JMenuItem menuItem_commandLine = new JMenuItem("Command line");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SQL_GUI frame = new SQL_GUI();
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
	public SQL_GUI() {
		// JDBC init -------------------------------------------------------
		
		// Initialize the jdbc
		jdbc = new SQL_JDBC();
		
		// While not connected, try to connect until connected or 10 attempts
		int c = 0;
		while (!jdbc.isConnected() && c < 9) {
			System.out.println("Connection attempt " + c);
			// Wait a second before trying again
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				System.out.println("ERROR: A thread exception has occured while attempting to connect to the Database");
				System.out.println(e1);
				e1.printStackTrace();
			}
			jdbc.connect();
			c++;
		}
		
		if (!jdbc.isConnected()) {
			System.out.println("ERROR: Could not connect to Database after 10 attempts");
			throw new RuntimeException();
		}
		
		// Initialize the CLI with the existing jdbc
		CLI = new SQL_CommandLineInterpreter(jdbc);
		
		
		initGUI();
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
		JPanel _showAllPrimaryKeys = 	new showAllPrimaryKeys();
		JPanel _plotSchema =			new plotSchema();
		JPanel _searchPath = 			new searchPath(CLI);
		JPanel _searchAndJoin = 		new searchAndJoin(jdbc);
		JPanel _joinAndShowTables = 	new joinAndShowTables();
		JPanel _showSelectedColumns = 	new showSelectedColumns(jdbc);
		JPanel _showRelatedTables = 	new showRelatedTables(jdbc);
		JPanel _ProductIDResolver = 	new ProductIDResolver(jdbc);
		JPanel _LocationIDResolver = 	new LocationIDResolver(jdbc);
		JPanel _stat = 					new stat(CLI);
		JPanel _findColumn = 			new findColumn();
		JPanel _getView = 				new getView();
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
		
		menu_tableRelationships.add(menuItem_joinAndShowTables);
		menuItem_joinAndShowTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "joinAndShowTables");
			}
		});
		
		menu_tableRelationships.add(menuItem_showSelectedColumns);
		menuItem_showSelectedColumns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "showSelectedColumns");
			}
		});
		
		menu_tableRelationships.add(menuItem_searchAndJoin);
		menuItem_showRelatedTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "showRelatedTables");
			}
		});
		
		menu_tableRelationships.add(menuItem_showRelatedTables);
		
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
}
