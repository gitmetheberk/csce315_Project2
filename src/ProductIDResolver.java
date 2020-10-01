import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;

public class ProductIDResolver extends JPanel {
	private SQL_JDBC jdbc;
	private final JTextField textField_name = new JTextField();
	private final JLabel lblPartialProductName = new JLabel("Partial Product Name:");
	private final JTextField textField_error = new JTextField();
	private final JTable table_results = new JTable();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JLabel lblPartialProductDescription = new JLabel("Partial Product Description:");
	private final JTextField textField_description = new JTextField();
	/**
	 * Create the panel.
	 */
	public ProductIDResolver(SQL_JDBC JDBC) {
		jdbc = JDBC;
		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		textField_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				runRESOLVER(true);
			}
		});
		textField_name.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_name.setColumns(10);
		textField_name.setBounds(10, 55, 269, 35);
		
		add(textField_name);
		lblPartialProductName.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblPartialProductName.setBounds(10, 10, 229, 35);
		
		add(lblPartialProductName);
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
		lblPartialProductDescription.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblPartialProductDescription.setBounds(10, 121, 269, 35);
		
		add(lblPartialProductDescription);
		textField_description.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				runRESOLVER(false);
			}
		});
		textField_description.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_description.setColumns(10);
		textField_description.setBounds(10, 166, 269, 35);
		
		add(textField_description);
	}
	
	private void runRESOLVER(boolean name) {
		// Clear the error text field
		textField_error.setText("");
		
		// Grab string from correct pane
		String input;
		if (name) {
			input = textField_name.getText();
		} else {
			input = textField_description.getText();
		}
		
		
		// Check it's not empty
		if (input.isEmpty()) {
			// Clear the table
			DefaultTableModel model = (DefaultTableModel) table_results.getModel();
			model.setRowCount(0);
			return;
		}
		
		// Define the query
		String query;
		if (name) {
			query = 
					  "SELECT ProductID, Name "
					+ "FROM product "
					+ "WHERE NAME LIKE \"%" + input + "%\";";
		} else {
			query = 
					  "SELECT product.ProductID, product.Name, productdescription.Description" + 
					  " FROM productdescription" + 
					  "	INNER JOIN productmodelproductdescriptionculture" + 
					  "	ON productmodelproductdescriptionculture.ProductDescriptionID=productdescription.ProductDescriptionID" + 
					  "	INNER JOIN product" + 
					  "	ON product.ProductModelID=productmodelproductdescriptionculture.ProductModelID" + 
					  " WHERE productdescription.Description LIKE \"%" + input + "%\";";
		}
		
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
