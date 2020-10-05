<<<<<<< HEAD
import javax.swing.JPanel;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

public class showAllPrimaryKeys extends JPanel {
	
	private SQL_CommandLineInterpreter Keys;
	
	public showAllPrimaryKeys(SQL_CommandLineInterpreter CLI) {
		setLayout(null);
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Show All Primary Keys");
		lblNewJgoodiesTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewJgoodiesTitle.setBounds(332, 55, 308, 35);
		add(lblNewJgoodiesTitle);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 147, 909, 367);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		Keys=CLI;
		
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
		btnNewButton.setBounds(379, 101, 148, 35);
		add(btnNewButton);
		

	
		
		

	}
}
=======

import javax.swing.JPanel;

public class showAllPrimaryKeys extends JPanel {

	/**
	 * Create the panel.
	 */
	public showAllPrimaryKeys() {

	}

}
>>>>>>> 4e3dc40093763bc66f77babce1a903f0ec76e0ab
