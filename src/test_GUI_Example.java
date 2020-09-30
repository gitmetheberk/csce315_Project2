import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SQL_GUI extends JFrame {

	private JPanel primaryPanel;
	
	CardLayout layout = new CardLayout();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menu_DBInformation = new JMenu("Database Information");
	private final JMenuItem test_gotoPanel2 = new JMenuItem("Goto Panel 2");
	private final JMenuItem menuItem_showAllTables = new JMenuItem("Show all tables");
	private final JMenuItem menuItem_ShowAllColumns = new JMenuItem("Show all columns");
	private final JMenuItem test_gotoPanel1 = new JMenuItem("Goto Panel 1");

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
		
		
		initGUI();
	}
	private void initGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		primaryPanel = new JPanel();
		primaryPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		primaryPanel.setLayout(new BorderLayout(0, 0));
		//setContentPane(primaryPanel);
		
		JPanel p1 = new test_panel1();
		JPanel p2 = new test_panel2();
		
		primaryPanel.setLayout(layout);
		primaryPanel.add(p1, "1");
		primaryPanel.add(p2, "2");
		layout.show(primaryPanel, "1");
		
		getContentPane().add(primaryPanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//pack();
		setVisible(true);
		
		setJMenuBar(menuBar);
		
		menuBar.add(menu_DBInformation);
		
		menu_DBInformation.add(menuItem_showAllTables);
		
		menu_DBInformation.add(menuItem_ShowAllColumns);
		test_gotoPanel1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "1");
			}
		});
		
		menuBar.add(test_gotoPanel1);
		test_gotoPanel2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(primaryPanel, "2");
			}
		});
		
		menuBar.add(test_gotoPanel2);
	}
}
