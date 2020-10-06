

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
	SQL_CommandLineInterpreter CLI;
	/**
	 * Create the panel.
	 */
	public plotSchema(SQL_CommandLineInterpreter _CLI) {
		CLI = _CLI;
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("View Database Schema");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(406, 265, 241, 36);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Generate");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MakeGraphThread processGraphThread = new MakeGraphThread(CLI);
				Thread t1 = new Thread(processGraphThread);
				t1.start();
				btnNewButton.setText("Loading...");
		    	while (t1.isAlive()) { //indicate to the user that the graph is still being created
		    		;
		    	}
		    	btnNewButton.setText("Generate");
			}
		});
		btnNewButton.setBounds(480, 311, 101, 30);
		add(btnNewButton);
		

	}
}
