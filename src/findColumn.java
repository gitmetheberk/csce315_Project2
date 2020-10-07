package project2;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ScrollPaneConstants;

public class findColumn extends JPanel {
	private SQL_CommandLineInterpreter CLI;
	private final JLabel label_title = new JLabel("<HTML><u>Find tables containing a column: </U></HTML>");
	private final JTextField textField_column = new JTextField();
	private final JLabel label_column = new JLabel("Column Name:");
	private final JTextPane textField_output = new JTextPane();
	private final JLabel label_output = new JLabel("Output:");
	private final JScrollPane scrollPane_output = new JScrollPane();
	private final JButton button_submit = new JButton("Submit");
	private final JButton button_clear = new JButton("Clear");
	
	public findColumn() {
		//eclipse did not like when when I did not have a default constructor
	}	
	/**
	 * Create the panel.
	 */
	public findColumn(SQL_CommandLineInterpreter cli) {
		textField_column.setBounds(169, 98, 269, 35);

		textField_column.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_column.setColumns(10);
		CLI = cli;
		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		label_title.setBounds(10, 10, 420, 33);
		label_title.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		add(label_title);
		
		add(textField_column);
		label_column.setBounds(10, 95, 155, 35);
		
		label_column.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		add(label_column);
		scrollPane_output.setBounds(99, 146, 921, 424);
		
		add(scrollPane_output);
		textField_output.setEditable(false);
		scrollPane_output.setViewportView(textField_output);
		textField_output.setFont(new Font("Arial", Font.PLAIN, 20));
		textField_output.setBackground(Color.WHITE);
		label_output.setBounds(10, 146, 155, 35);
		label_output.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		add(label_output);
		button_submit.setBounds(509, 53, 128, 80);
		button_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runFindColumn();
			}
		});
		button_submit.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		add(button_submit);
		button_clear.setBounds(892, 53, 128, 80);
		button_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_column.setText("");
				textField_output.setText("");
			}
		});
		button_clear.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		add(button_clear);
	}
	
	// Driver function
	private void runFindColumn() {
		String outputString = "";
		
		// Grab user input
		String t1 = textField_column.getText();
		
		// Catch empty input
		if (t1.isEmpty()) {
			outputString += "Warning: Column field is empty";
			textField_output.setText(outputString);
			return;
		}
		
		// Pass to CLI as command and display output after processing
		outputString = CLI.processInput("jdb-find-column " + t1);
		textField_output.setText(outputString);
		
		return;
	}
}
