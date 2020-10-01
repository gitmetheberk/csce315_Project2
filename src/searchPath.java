import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

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
import javax.swing.ScrollPaneConstants;

public class searchPath extends JPanel {
	private SQL_CommandLineInterpreter CLI;
	private final JLabel label_title = new JLabel("<HTML><u>Find a path between two tables</U></HTML>");
	private final JTextField textField_origin = new JTextField();
	private final JTextField textField_destination = new JTextField();
	private final JLabel label_origin = new JLabel("Origin table:");
	private final JLabel lblDestinationTable = new JLabel("Destination table:");
	private final JTextField textField_path = new JTextField();
	private final JLabel label_path = new JLabel("Path:");
	private final JScrollPane scrollPane_path = new JScrollPane();
	private final JButton button_submit = new JButton("Submit");
	private final JButton button_clear = new JButton("Clear");
	
	
	/**
	 * Create the panel.
	 */
	public searchPath(SQL_CommandLineInterpreter cli) {
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
		CLI = cli;
		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		label_title.setFont(new Font("Tahoma", Font.BOLD, 25));
		label_title.setBounds(10, 10, 420, 33);
		
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
		
		// Pass to CLI as command and display output after processing
		pathOut = CLI.processInput("jdb-search-path " + t1 + " " + t2);
		pathOut = pathOut.replace("\n", "");
		pathOut = pathOut.replace("PATH:", "");
		textField_path.setText(pathOut);
		
		return;
	}
}
