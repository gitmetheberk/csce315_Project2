import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class findColumn extends JPanel {
	private SQL_CommandLineInterpreter CLI;
	private final JLabel label_title = new JLabel("<HTML><u>Find tables containing a column: </U></HTML>");
	private final JTextField textField_column = new JTextField();
	private final JLabel label_column = new JLabel("Column Name:");
	private final JTextField textField_output = new JTextField();
	private final JLabel label_output = new JLabel("Output:");
	private final JScrollPane scrollPane_output = new JScrollPane();
	private final JButton button_submit = new JButton("Submit");
	private final JButton button_clear = new JButton("Clear");
	
	
	/**
	 * Create the panel.
	 */
	public findColumn(SQL_CommandLineInterpreter cli) {

		textField_column.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_column.setBounds(169, 98, 269, 35);
		textField_column.setColumns(10);
		CLI = cli;
		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		label_title.setFont(new Font("Tahoma", Font.BOLD, 25));
		label_title.setBounds(10, 10, 420, 33);
		
		add(label_title);
		
		add(textField_column);
		
		label_column.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_column.setBounds(10, 95, 155, 35);
		
		add(label_column);
		
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
				runFindColumn();
			}
		});
		button_submit.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_submit.setBounds(509, 53, 128, 80);
		
		add(button_submit);
		button_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_column.setText("");
				textField_output.setText("");
			}
		});
		button_clear.setFont(new Font("Tahoma", Font.BOLD, 20));
		button_clear.setBounds(892, 53, 128, 80);
		
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
