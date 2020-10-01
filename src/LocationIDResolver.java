import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import javax.swing.JTable;

public class LocationIDResolver extends JPanel {
	private SQL_JDBC jdbc;
	private final JTextField textField_name = new JTextField();
	private final JLabel label_name = new JLabel("Partial Location Name:");
	private final JTextField textField_error = new JTextField();
	private final JTable table_results = new JTable();
	private final JScrollPane scrollPane = new JScrollPane();
	/**
	 * Create the panel.
	 */
	public LocationIDResolver(SQL_JDBC JDBC) {
		jdbc = JDBC;
		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		textField_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				runRESOLVER();
			}
		});
		textField_name.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_name.setColumns(10);
		textField_name.setBounds(10, 55, 269, 35);
		
		add(textField_name);
		label_name.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_name.setBounds(10, 10, 229, 35);
		
		add(label_name);
		textField_error.setEditable(false);
		textField_error.setBackground(Color.LIGHT_GRAY);
		textField_error.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_error.setColumns(10);
		textField_error.setBounds(286, 10, 749, 35);
		
		add(textField_error);
		scrollPane.setBounds(286, 55, 749, 542);
		
		add(scrollPane);
		scrollPane.setViewportView(table_results);
		table_results.setBackground(Color.LIGHT_GRAY);
	}
	
	private void runRESOLVER() {
		// Clear the error text field
		textField_error.setText("");
		
		// Grab string and check it's not empty
		String input = textField_name.getText();
		if (input.isEmpty()) {
			// Clear the table
			DefaultTableModel model = (DefaultTableModel) table_results.getModel();
			model.setRowCount(0);
			return;
		}
		
		// Define the query
		String query = 
				  " SELECT LocationID, Name" + 
				  " FROM location" + 
				  " WHERE NAME LIKE \"%" + input + "%\";";
		
		// Make the query and send to the JTable
		ResultSet rs = jdbc.query(query);
		if (rs == null) {
			textField_error.setText("ERROR: An error has occured");
		} else {
			table_results.setModel(DbUtils.resultSetToTableModel(rs));
		}
		
		if (table_results.getRowCount() == 0) {
			textField_error.setText("Warning: Your query produced zero results");
		}
	
	}
}
