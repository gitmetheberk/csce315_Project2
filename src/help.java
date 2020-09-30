

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class help extends JPanel {
	private final JLabel lblNewLabel = new JLabel("This is the HELP page");

	/**
	 * Create the panel.
	 */
	public help() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(408, 278, 432, 129);
		
		add(lblNewLabel);
	}

}
