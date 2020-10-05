

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class plotSchema extends JPanel {

	/**
	 * Create the panel.
	 */
	public plotSchema() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("View Database Schema");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(412, 52, 241, 36);
		add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 162, 1009, 473);
		scrollPane.setVisible(false);
		add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(getClass().getResource("/img/schema.png")));
		scrollPane.setViewportView(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);
		
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel_1.setVisible(true);
				scrollPane.setVisible(true);
			}
		});
		btnNewButton.setBounds(497, 100, 101, 30);
		add(btnNewButton);
		

	}
}
