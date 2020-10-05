import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;

public class showRelatedTables extends JPanel {
private SQL_CommandLineInterpreter Table;
private JTextField textField;
private JTextArea textArea = new JTextArea();	

	public showRelatedTables(SQL_CommandLineInterpreter CLI) {
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(38, 103, 228, 37);
		add(textField);
		textField.setColumns(10);
		Table = CLI;
		
		//String INput = textField.getText();
		
		JLabel lblNewLabel = new JLabel("Table Name: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(38, 76, 142, 20);
		add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(523, 93, 417, 400);
		add(scrollPane);
		
		//JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setCaretPosition(0); //0 will ensure the scroll bar isn't initialized to the bottom of the text area
		textArea.setVisible(true);
		scrollPane.setVisible(true);
		
		JLabel lblNewJgoodiesTitle = new JLabel("All Related Tables :");
		lblNewJgoodiesTitle.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewJgoodiesTitle.setBounds(523, 45, 309, 37);
		add(lblNewJgoodiesTitle);
		
		JButton btnNewButton = new JButton("Show");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//textArea.setText(CLI.processInput("jdb-show-related-tables "+ INput));
				runSearch();
	
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(38, 151, 117, 37);
		add(btnNewButton);
	}
		private void runSearch() {
			
			// Grab user input
			String t1 = textField.getText();			
			// Pass to CLI as command and display output after processing
			textArea.setText(Table.processInput("jdb-show-related-tables "+ t1));
			
			return;
		}
	
}

