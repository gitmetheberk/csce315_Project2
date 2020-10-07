

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.Color;

public class help extends JPanel {
	private final JTextPane textpane_help = new JTextPane();
	private final JLabel lblNewLabel = new JLabel("Help page");

	/**
	 * Create the panel.
	 */
	public help() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		textpane_help.setEditable(false);
		textpane_help.setContentType("text/html");
		textpane_help.setText("<html><b>Search path</b> - Finds a path between two tables, requires two table names<br><br>"
				+ "<b>Search and join</b> - Finds a path between two tables and joins them, requires two table names<br><br>"
				+ "<b>Command line</b> - Direct access to the terminal interface<br><br>"
				+ "<b>Get View</b> - Given a view name and SQL query, creates a view<br><br>"
				+ "<b>Find Column</b> -  Find all tables that have a given column name<br><br>"
				+ "<b>ProductID resolvers</b> - Given a partial product name or description, find it’s ID<br><br>"
				+ "<b>LocationID resolvers</b> - Given a partial location name, find it’s ID<br><br>"
				+ "<b>Join and show tables</b> - Join up to four tables by giving the table to join and the column to join on. Columns must have the same name in both tables.<br><br>"
				+ "<b>Stat</b> - Given table and column name give stats about the column including the min, max, mean, median, and a histogram of the data.<br><br>"
				+ "<b>Show selected columns</b> - Given a table select to show up to six columns or all of the columns in a table.<br><br>"
				+ "<b>Show All Tables</b> - This command simply provides a list of all the tables in the database. <br><br>"
				+ "<b>Show All Columns</b> - This command simply provides a list of all the columns in the database. Columns for a specific table are grouped together.<br><br>"
				+ "<b>Show All Primary Keys</b> - Display the primary keys for each tables  <br><br>"
				+ "<b>Show Related Tables</b> - Find all tables that shared a primary key with a given table<br><br>"
				+ "<b>Display DB Schema</b> - This command displays a digraph representing the relationships between the tables in the database. The tables are related by the primary keys. The source of each edge is a table containing a specific primary key. The destination of each edge is a table that contains a column matching the primary key of the source node. "
				+ "</html>");
		textpane_help.setFont(new Font("Arial", Font.PLAIN, 15));
		textpane_help.setBackground(Color.LIGHT_GRAY);
		textpane_help.setBounds(10, 60, 1040, 584);
		
		add(textpane_help);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 10, 102, 40);
		
		add(lblNewLabel);
	}
}
