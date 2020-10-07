

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class showAllColumns extends JPanel {
	SQL_CommandLineInterpreter CLI;
	/**
	 * Create the panel.
	 */
	public showAllColumns(SQL_CommandLineInterpreter _CLI) {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(273, 125, 533, 422);
		scrollPane.setVisible(false);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setVisible(false);
		CLI = _CLI;
		
		JButton btnNewButton = new JButton("Go");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(CLI.processInput("jdb-show-all-columns"));
				textArea.setCaretPosition(0); //0 will ensure the scroll bar isn't initialized to the bottom of the text area
				textArea.setVisible(true);
				scrollPane.setVisible(true);
			}
		});
		btnNewButton.setBounds(483, 77, 114, 30);
		add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("View All Columns For the Database Tables");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(331, 25, 418, 42);
		add(lblNewLabel);
		
	}
}
