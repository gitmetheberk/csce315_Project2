

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;


public class plotSchema extends JPanel {
	SQL_CommandLineInterpreter CLI;
	/**
	 * Create the panel.
	 */
	public plotSchema(SQL_CommandLineInterpreter _CLI) {
		CLI = _CLI;
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("View Database Schema");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(227, 95, 626, 76);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 65));
		btnNewButton.setBackground(new Color(139, 0, 0));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MakeGraphThread processGraphThread = new MakeGraphThread(CLI);
				Thread t1 = new Thread(processGraphThread);
				t1.start();
		    	while (t1.isAlive()) { //indicate to the user that the graph is still being created
		    		;
		    	}
			}
		});
		btnNewButton.setBounds(74, 200, 931, 319);
		add(btnNewButton);
		

	}
}
