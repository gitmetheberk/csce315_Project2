import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

public class showAllPrimaryKeys extends JPanel {

	public showAllPrimaryKeys(SQL_CommandLineInterpreter CLI) {
		setLayout(null);
		
		JLabel lblNewJgoodiesTitle = new JLabel("Show All Primary Keys");
		lblNewJgoodiesTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewJgoodiesTitle.setBounds(414, 55, 252, 35);
		add(lblNewJgoodiesTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(85, 147, 909, 367);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton = new JButton("Show");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(CLI.processInput("jdb-show-all-primary-keys"));
				textArea.setCaretPosition(0); //0 will ensure the scroll bar isn't initialized to the bottom of the text area
				textArea.setVisible(true);
				scrollPane.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(466, 101, 148, 35);
		add(btnNewButton);
		

	
		
		

	}
}
