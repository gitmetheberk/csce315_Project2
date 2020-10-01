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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.ScrollPaneConstants;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;

public class searchAndJoin extends JPanel {
	private SQL_JDBC jdbc;
	private final JLabel label_title = new JLabel("<HTML><u>Find a path between two tables and join them</U></HTML>");
	private final JTextField textField_origin = new JTextField();
	private final JTextField textField_destination = new JTextField();
	private final JLabel label_origin = new JLabel("Origin table:");
	private final JLabel lblDestinationTable = new JLabel("Destination table:");
	private final JTextField textField_path = new JTextField();
	private final JLabel label_path = new JLabel("Path:");
	private final JScrollPane scrollPane_path = new JScrollPane();
	private final JButton button_submit = new JButton("Submit");
	private final JButton button_clear = new JButton("Clear");
	private final JTable table_results = new JTable();
	private final JScrollPane scrollPane_results = new JScrollPane();
	
	
	/**
	 * Create the panel.
	 */
	public searchAndJoin(SQL_JDBC JDBC) {
		textField_destination.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// On enter press, run the path search
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					runPATH();
				}
			}
		});
		textField_destination.setBackground(new Color(255, 255, 255));
		textField_destination.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_destination.setBounds(230, 98, 269, 35);
		textField_destination.setColumns(10);
		textField_origin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// On enter in origin table, go to dest field
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					textField_destination.requestFocusInWindow();
				}
			}
		});
		textField_origin.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_origin.setBounds(230, 53, 269, 35);
		textField_origin.setColumns(10);
		jdbc = JDBC;
		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		label_title.setFont(new Font("Tahoma", Font.BOLD, 25));
		label_title.setBounds(10, 10, 774, 33);
		
		add(label_title);
		
		add(textField_origin);
		
		add(textField_destination);
		label_origin.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_origin.setBounds(34, 53, 155, 35);
		
		add(label_origin);
		lblDestinationTable.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDestinationTable.setBounds(34, 98, 200, 35);
		
		add(lblDestinationTable);
		scrollPane_path.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_path.setBounds(99, 146, 922, 35);
		
		add(scrollPane_path);
		scrollPane_path.setViewportView(textField_path);
		textField_path.setEditable(false);
		textField_path.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_path.setColumns(10);
		textField_path.setBackground(Color.WHITE);
		label_path.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_path.setBounds(34, 143, 155, 35);
		
		add(label_path);
		button_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runPATH();
			}
		});
		button_submit.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_submit.setBounds(509, 53, 128, 80);
		
		add(button_submit);
		button_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_origin.setText("");
				textField_destination.setText("");
				textField_path.setText("");
			}
		});
		button_clear.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_clear.setBounds(892, 53, 128, 80);
		
		add(button_clear);
		scrollPane_results.setBounds(34, 188, 987, 434);
		
		add(scrollPane_results);
		scrollPane_results.setViewportView(table_results);
		table_results.setBackground(Color.LIGHT_GRAY);
		table_results.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	// Driver function
	private void runPATH() {
		String pathOut = "";
		
		// Grab user input
		String t1 = textField_origin.getText();
		String t2 = textField_destination.getText();
		
		// Catch empty input
		if (t1.isEmpty() || t2.isEmpty()) {
			pathOut += "Warning: Either the origin or destination is empty";
			textField_path.setText(pathOut);
			return;
		}
		
		//! Code copied from SQL_CommandLineInterpreter with slight modifications (9/30/2020) ----------------------------------------------------
		// Check t1 != t2
		if (t1.equals(t2)) {
			pathOut += "Warning: The origin table cannot be equal to the destination table";
			textField_path.setText(pathOut);
			return;
		}
		
		// Queue to hold tables to be processed
		Queue<TableNode> tableQ = new LinkedList<>();
		
		// Temporary queue to hold nodes to be processed while working through tableQ
		Queue<TableNode> tempQ = new LinkedList<>();
		
		// Hold the path to the current node from the original table
		ArrayList<String> path = new ArrayList<String>();
		
		// Hold all nodes
		ArrayList<TableNode> nodes = new ArrayList<TableNode>();
		
		// Process the first node outside of the while loop so there is 1 node to draw from
		
		TableNode firstNode = new TableNode(t1, path);
		tableQ.add(firstNode);
		nodes.add(firstNode);
		
		// --------------------------------------------------------------------------------------
		
		
		boolean breakBool = true;
		boolean foundBool = false;
		TableNode workingTable;
		while(breakBool) {
			// Run while queue for tables isn't empty
			while(!tableQ.isEmpty()) {
				// Expect workingTable to be a node without columns assigned
				workingTable = tableQ.remove();

				// Query the database for the columns of this table
				String query_columns = "Select distinct COLUMN_NAME From INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=\"" + workingTable.getName() + "\";";
				ResultSet rs = jdbc.query(query_columns);
				
				ArrayList<String> cols_of_node = new ArrayList<String>();
				
				try {
					// Loop through the results, add the columns to the array list
					while(rs.next()) {
						String col = rs.getString("COLUMN_NAME");
						
						// Only add columns that end in ID, if not ending in ID, not a true matching column
						if (col.endsWith("ID")){
							cols_of_node.add(col);
						}
					}
				} catch (SQLException e) {
					pathOut = pathOut.concat("ERROR: An error occured while processing your request");
					textField_path.setText(pathOut);
					System.out.println("Error: " + e + "\n");
					return;
				}
				
				// Set the columns for this node
				workingTable.setColumns(cols_of_node);
				
				// Check for other tables that share 1 or more of this tables ID columns
				Queue<String> table_names = new LinkedList<>();
				for (String column : cols_of_node) {
					String query_tables = "Select distinct TABLE_NAME From INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_NAME=\"" + column + "\";";
					ResultSet tables = jdbc.query(query_tables);
					
					try {
						// Loop through the results, add the columns to the table_name queue
						while(tables.next()) {
							String table = tables.getString("TABLE_NAME");
							
							// If the table is not this table, add to the queue
							if (!table.equals(workingTable.getName())){
								table_names.add(table);
							}
							
							
						}
					} catch (SQLException e) {
						pathOut = pathOut.concat("ERROR: An error occured while processing your request");
						textField_path.setText(pathOut);
						System.out.println("Error: " + e + "\n");
						return;
					}
				}
				
				// Setup correct path information
				path = new ArrayList<String>(workingTable.getPath_to());
				path.add(workingTable.getName());
				
				// Loop through the queue
				String tn;
				while(!table_names.isEmpty()) {
					tn = table_names.remove();
					
					// Check if tn is the end point table
					if (tn.equals(t2)) {
						breakBool = false;
						foundBool = true;
						path.add(tn);
						break;
					}
					
					// Check if tn aleady has a node
					boolean exists = false;
					for (TableNode n : nodes) {
						// If table already visited (aka in nodes) continue
						if (n.getName().equals(tn)) {
							exists = true;
							break;
						} 
					}
					
					// Check if the table was found in the for loop
					if (exists) {
						continue;
					}
					
					// Create a new empty node with the correct path and add it to two locations (should be shared...)
					TableNode node = new TableNode(tn, path);
					tempQ.add(node);
					nodes.add(node);
				
				
				}
				
				if (foundBool) {
					breakBool = false;
					break;
				}

				
			} // End !tableQ.isEmpty() loop
			
			// Check if tempQ is empty, if so, and not found, no path
			if (tempQ.isEmpty() && !foundBool) {
				breakBool = false;
				break;
			}
			
			// Assign the tempQ to the tableQ and continue
			tableQ = new LinkedList<>(tempQ);
			tempQ.clear();
		} // End outer while
		
		// Check if a path was found
		if (foundBool) {
			
			// Add the results to the toReturn string
			boolean first = true;
			for (String s : path) {
				if (first) {
					first = false;
					pathOut += s;
					
				} else {
					pathOut += " -> " + s;
				}
			}
			
			
			// Step 1: Create node for the final table (path isn't important at this point)
			TableNode lastNode = new TableNode(t2, null);
			
			// Code from main while loops:
			String query_columns = "Select distinct COLUMN_NAME From INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME=\"" + lastNode.getName() + "\";";
			ResultSet rs = jdbc.query(query_columns);
			
			ArrayList<String> cols_of_node = new ArrayList<String>();
			try {
				// Loop through the results, add the columns to the array list
				while(rs.next()) {
					String col = rs.getString("COLUMN_NAME");
					
					// Only add columns that end in ID, if not ending in ID, not a true matching column
					if (col.endsWith("ID")){
						cols_of_node.add(col);
					}
				}
			} catch (SQLException e) {
				pathOut = pathOut.concat("ERROR: An error occured while processing your request");
				textField_path.setText(pathOut);
				System.out.println("Error: " + e + "\n");
				return;
			}
			
			// Set the columns for this node
			lastNode.setColumns(cols_of_node);
			nodes.add(lastNode);
			
			// Step 2: Resolve nodes along path for actual TableNode in nodes (time to get n^2 inefficient!)
			ArrayList<TableNode> pathNodes = new ArrayList<TableNode>();
			
			// Resolve each table name in the path to a tableNode
			// Technically this could be slightly more efficient by not looking for firstNode and lastNode, but it's a minor improvement
			for (String name : path) {
				for (TableNode n : nodes) {
					if (name.equals(n.getName())) {
						pathNodes.add(n);
					}
				}
			}
			
			// Step 3: Find the matching columns (oh yeah, more inefficient code, lots more!)
			ArrayList<String> matchingColumns = new ArrayList<String>();
			
			boolean match;
			for (TableNode n : pathNodes) {
				// Check for last node in pathNodes, if so, break
				if (n == pathNodes.get(pathNodes.size() - 1)) {
					break;
				}
				
				// Grab the columns
				ArrayList<String> colsLeft = n.getColumns();
				ArrayList<String> colsRight = pathNodes.get(pathNodes.indexOf(n) + 1).getColumns();
				
				// Find the matching column
				match = false;
				for (String colL : colsLeft) {
					for (String colR : colsRight) {
						if (colL.equals(colR)) {
							matchingColumns.add(colL);
							match = true;
							break;
						}
					}
					if (match) {
						break;
					}
				}
				if (match) {
					continue;
				}
			}
			
			// Step 4: Write the SQL query
			String query = "SELECT * FROM " + t1 + " ";
			
			// Check for a direct link
			if (matchingColumns.size() == 1) {
				query = query.concat("INNER JOIN " + pathNodes.get(1).getName() + 
									 " ON " + pathNodes.get(1).getName() + "." + matchingColumns.get(0) +
									 "=" + pathNodes.get(0).getName() + "." + matchingColumns.get(0));
				
			} else {
				//System.out.println("LEN:" + pathNodes.size());
				for (int i = 1; i <= matchingColumns.size(); i++) {
					query = query.concat("INNER JOIN " + pathNodes.get(i).getName() + 
										 " ON " + pathNodes.get(i).getName() + "." + matchingColumns.get(i - 1) + 
										 "=" + pathNodes.get(i - 1).getName() + "." + matchingColumns.get(i - 1) + " ");
	
				}
			}
			
			query = query.concat(";");
			
			// Step 5: Execute the query
			ResultSet joinResults = jdbc.query(query);
			
			// Step 6: Display the results in a JTable using rs2xml
			table_results.setModel(DbUtils.resultSetToTableModel(joinResults));
			
	
		} else {
			pathOut += "WARNING: No path was found between " + t1 + " and " + t2;
		}
		
		// Set the path field and return
		textField_path.setText(pathOut);
		return;
	}
}
