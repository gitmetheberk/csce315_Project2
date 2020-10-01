import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class stat extends JPanel {
	private JTextField tableField;
	private JTextField columnField;
	private JTextPane maxText;
	private JTextPane minText;
	private JTextPane meanText;
	private JTextPane medianText;
	private JTextPane histText;

	public stat() {
		//eclipse did not like when when I did not have a default constructor
	}
	
	public stat(SQL_CommandLineInterpreter CLI) {
		initGUI(CLI);
	}
	
	private void initGUI(SQL_CommandLineInterpreter CLI) {
		setLayout(null);
		setBounds(100, 100, 1080, 720);
		
		JLabel panelLabel = new JLabel("Table Column Statistics");
		panelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelLabel.setFont(new Font("Tahoma", Font.BOLD, 55));
		panelLabel.setBounds(176, 11, 728, 127);
		add(panelLabel);
		
		JLabel tableLabel = new JLabel("Table:");
		tableLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tableLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableLabel.setBounds(100, 150, 100, 50);
		add(tableLabel);
		
		JLabel columnLabel = new JLabel("Column:");
		columnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		columnLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnLabel.setBounds(450, 150, 100, 50);
		add(columnLabel);
		
		JLabel maxLabel = new JLabel("MAX:");
		maxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		maxLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		maxLabel.setBounds(100, 300, 100, 50);
		add(maxLabel);
		
		JLabel minLabel = new JLabel("MIN:");
		minLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		minLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		minLabel.setBounds(100, 375, 100, 50);
		add(minLabel);
		
		JLabel meanLabel = new JLabel("Mean:");
		meanLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		meanLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		meanLabel.setBounds(100, 450, 100, 50);
		add(meanLabel);
		
		JLabel medianLabel = new JLabel("Median:");
		medianLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		medianLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		medianLabel.setBounds(100, 525, 100, 50);
		add(medianLabel);

		JLabel histLabel = new JLabel("Histogram");
		histLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		histLabel.setBounds(550, 300, 200, 50);
		add(histLabel);
		
		tableField = new JTextField();
		tableField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableField.setBounds(200, 150, 200, 50);
		add(tableField);
		tableField.setColumns(10);
		
		columnField = new JTextField();
		columnField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnField.setBounds(550, 150, 200, 50);
		add(columnField);
		columnField.setColumns(10);
		
		maxText = new JTextPane();
		maxText.setFont(new Font("Tahoma", Font.PLAIN, 25));
		maxText.setBounds(200, 300, 200, 50);
		add(maxText);
		
		minText = new JTextPane();
		minText.setFont(new Font("Tahoma", Font.PLAIN, 25));
		minText.setBounds(200, 375, 200, 50);
		add(minText);
		
		meanText = new JTextPane();
		meanText.setFont(new Font("Tahoma", Font.PLAIN, 25));
		meanText.setBounds(200, 450, 200, 50);
		add(meanText);
		
		medianText = new JTextPane();
		medianText.setFont(new Font("Tahoma", Font.PLAIN, 25));
		medianText.setBounds(200, 525, 200, 50);
		add(medianText);
		
		histText = new JTextPane();
		histText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		histText.setBounds(550, 350, 300, 250);
		add(histText);
		
		JButton submitButton = new JButton("Show Results");
		submitButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		submitButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String userIn = "jdb-stat " + tableField.getText() + " " + columnField.getText();
				String result = CLI.processInput(userIn);
				if (result.startsWith("ERROR")) {
					JOptionPane.showMessageDialog(null, "The table or column name does not exist in the database.");
				}else {
					result = result.substring(result.indexOf("MAX: "));
					maxText.setText(result.substring(5,result.indexOf("\n")));
					result = result.substring(result.indexOf("MIN: "));
					minText.setText(result.substring(5,result.indexOf("\n")));
					result = result.substring(result.indexOf("MEAN: "));
					meanText.setText(result.substring(6,result.indexOf("\n")));
					result = result.substring(result.indexOf("MEDIAN: "));
					medianText.setText(result.substring(8,result.indexOf("\n")));
					result = result.substring(result.indexOf("\n")+1);
					histText.setText(result);
				}
			}
		});
		submitButton.setBounds(775, 150, 200, 50);
		add(submitButton);
	}
}
