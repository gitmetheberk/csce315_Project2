import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;

import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;
import javax.swing.JTable;

public class getView extends JPanel {
	private SQL_CommandLineInterpreter CLI;
	private SQL_JDBC jdbc;
	private final JLabel label_title = new JLabel("<HTML><u>Create a View: </U></HTML>");
	private final JTextField textField_viewName = new JTextField();
	private final JTextField textField_query = new JTextField();
	private final JLabel label_viewName = new JLabel("View Name:");
	private final JLabel label_query = new JLabel("SQL Query:");
	private final JTextField textField_output = new JTextField();
	private final JLabel label_output = new JLabel("Output:");
	private final JScrollPane scrollPane_output = new JScrollPane();
	private final JButton button_submit = new JButton("Submit");
	private final JButton button_clear = new JButton("Clear");
	private final JTable table_results = new JTable();
	private final JScrollPane scrollPane_results = new JScrollPane();
	
//	public getView() {
//		//eclipse did not like when when I did not have a default constructor
//      Shouldn't matter as long as A constructor exists. 
//	}	
	
	/**
	 * Create the panel.
	 */
	public getView(SQL_CommandLineInterpreter cli, SQL_JDBC JDBC) {
		textField_query.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// On enter press, run the function
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					runView();
				}
			}
		});
		textField_query.setBackground(new Color(255, 255, 255));
		textField_query.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_query.setBounds(230, 98, 269, 35);
		textField_query.setColumns(10);
		textField_viewName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// On enter in origin table, go to dest field
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					textField_query.requestFocusInWindow();
				}
			}
		});
		textField_viewName.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_viewName.setBounds(230, 53, 269, 35);
		textField_viewName.setColumns(10);
		jdbc = JDBC;
		CLI = cli;
		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		label_title.setFont(new Font("Tahoma", Font.BOLD, 25));
		label_title.setBounds(10, 10, 420, 33);
		
		add(label_title);
		
		add(textField_viewName);
		
		add(textField_query);
		label_viewName.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_viewName.setBounds(34, 53, 155, 35);
		
		add(label_viewName);
		label_query.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_query.setBounds(34, 98, 200, 35);
		
		add(label_query);
		scrollPane_output.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_output.setBounds(99, 146, 922, 35);
		
		add(scrollPane_output);
		scrollPane_output.setViewportView(textField_output);
		textField_output.setEditable(false);
		textField_output.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_output.setColumns(10);
		textField_output.setBackground(Color.WHITE);
		label_output.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_output.setBounds(10, 146, 155, 35);
		
		add(label_output);
		button_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runView();
			}
		});
		button_submit.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_submit.setBounds(509, 53, 128, 80);
		
		add(button_submit);
		button_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_viewName.setText("");
				textField_query.setText("");
				textField_output.setText("");
				DefaultTableModel model = (DefaultTableModel) table_results.getModel();
				model.setRowCount(0);
			}
		});
		button_clear.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_clear.setBounds(892, 53, 128, 80);
		
		add(button_clear);
		scrollPane_results.setBounds(99, 191, 922, 450);
		
		add(scrollPane_results);
		scrollPane_results.setViewportView(table_results);
		table_results.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	// Driver function
	private void runView() {
		String outputString = "";
		
		// Grab user input
		String t1 = textField_viewName.getText();
		String t2 = textField_query.getText();
		
		// Catch empty input
		if (t1.isEmpty() || t2.isEmpty()) {
			outputString += "Warning: Either view name or query is empty";
			textField_output.setText(outputString);
			return;
		}
		
		// Pass to CLI as command and display output after processing
		outputString = CLI.processInput("jdb-get-view " + t1 + " " + t2);
		textField_output.setText(outputString);
		
		
		// Query to display resulting view in table
		ResultSet joinResults = jdbc.query("select * from " + t1);
		table_results.setModel(DbUtils.resultSetToTableModel(joinResults));
		return;
	}
}
