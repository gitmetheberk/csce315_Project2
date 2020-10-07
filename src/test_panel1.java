import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class test_panel1 extends JPanel {
	private final JLabel lblNewLabel = new JLabel("THIS IS TEST_PANEL1");

	/**
	 * Create the panel.
	 */
	public test_panel1() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		setBounds(100, 100, 1080, 720);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(264, 161, 538, 348);
		
		add(lblNewLabel);
	}

}
