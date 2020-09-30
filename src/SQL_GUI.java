import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import java.awt.List;
import javax.swing.JMenu;
import javax.swing.JTable;
import java.awt.Button;

public class SQL_GUI extends JFrame {
	private SQL_JDBC jdbc;
	private SQL_CommandLineInterpreter CLI;
	
	private JPanel contentPane;

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
					System.out.println("ERROR: An error has occured in the GUI");
					System.out.println(e);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SQL_GUI() {
		setTitle("AdventureWorks User Interface");
		setResizable(false);
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

		
		
		// GUI code -------------------------------------------------------
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuItem_dbInfo = new JMenu("Database Information");
		menuBar.add(menuItem_dbInfo);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("List Tables");
		menuItem_dbInfo.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("List Columns");
		menuItem_dbInfo.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
		menuBar.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
