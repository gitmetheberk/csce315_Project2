
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class ProductIDResolver extends JPanel {
	private final JLabel lblNewLabel = new JLabel("This is the productid resolver");

	/**
	 * Create the panel.
	 */
	public ProductIDResolver() {

		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(56, 64, 308, 152);
		
		add(lblNewLabel);
	}

}
