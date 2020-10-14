import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Component;
import javax.swing.Box;

public class SQL_Dashboard extends JFrame {
	private SQL_JDBC jdbc;
	
	private JPanel contentPane_primary;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SQL_Dashboard frame = new SQL_Dashboard();
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
	public SQL_Dashboard() {
		// Get user credentials and initialize the jdbc
		// Connect to the database
		
		//* DEVELOPMENT ONLY *
//		jdbc = new SQL_JDBC();
//		tryConnectDEV();
//		initGUI();
//		return;
		
		if (login()) {
			initGUI();
			
		} else {
			// User has closed/cancelled connection, abort
			dispose();
			System.exit(1);
		}
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		
		setJMenuBar(menuBar);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// On-click, open the GUI (this is copied from SQL_GUI main)
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SQL_GUI frame = new SQL_GUI(jdbc);
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		menuBar.add(mntmNewMenuItem);
		
		menuBar.add(mntmNewMenuItem_1);
		
		menuBar.add(horizontalStrut);
		contentPane_primary = new JPanel();
		contentPane_primary.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane_primary);
		contentPane_primary.setLayout(null);
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
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenuItem mntmNewMenuItem = new JMenuItem("Launch database client");
	private final JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
	private final Component horizontalStrut = Box.createHorizontalStrut(1200);
	
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
		contentPane_primary = new JPanel();
		contentPane_primary.setBounds(100, 100, 320, 238);
		//contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane_primary.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_primary.setLayout(null);
		
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
		
		contentPane_primary.add(textField_user);
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
		
		contentPane_primary.add(textField_pass);
		
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
		
		contentPane_primary.add(textField_URL);
		label_url.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_url.setBounds(10, 10, 58, 30);
		
		contentPane_primary.add(label_url);
		label_userid.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_userid.setBounds(10, 56, 58, 30);
		
		contentPane_primary.add(label_userid);
		label_password.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_password.setBounds(10, 102, 68, 30);
		
		contentPane_primary.add(label_password);
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
		
		contentPane_primary.add(button_DEVLOGIN);
		
		// Initialize the JDBC in default mode
		jdbc = new SQL_JDBC(false);
		
		// Adjust JOptionPane to suit needs
		UIManager.put("OptionPane.minimumSize",new Dimension(350, 200)); 
		UIManager.put("OptionPane.yesButtonText", "Connect");
		UIManager.put("OptionPane.noButtonText", "Cancel");
		
		// Loop until connected or cancelled
		int result;
		while (!jdbc.isConnected()) {
			result = JOptionPane.showConfirmDialog(this, contentPane_primary, "AdventureWorks Client Login", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			
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
