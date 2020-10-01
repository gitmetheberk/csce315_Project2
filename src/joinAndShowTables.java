import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class joinAndShowTables extends JPanel {
	private JTable resultTable;
	private JTextField tableField;
	private JTextField tableField1;
	private JTextField joinField1;
	private JTextField tableField2;
	private JTextField joinField2;
	private JTextField tableField3;
	private JTextField joinField3;
	
	private boolean showTable2;
	private boolean showTable3;

	public joinAndShowTables() {
		initGUI();
	}
	
	private void initGUI() {
		setLayout(null);
		setBounds(100, 100, 1080, 720);
		
		showTable2 = false;
		showTable3 = false;
		
		JLabel panelLabel = new JLabel("Join Tables");
		panelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelLabel.setFont(new Font("Tahoma", Font.BOLD, 55));
		panelLabel.setBounds(176, 11, 728, 79);
		add(panelLabel);
		
		JLabel tableLabel = new JLabel("Table:");
		tableLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tableLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableLabel.setBounds(50, 100, 100, 50);
		add(tableLabel);
		
		JLabel tableLabel1 = new JLabel("JOIN Table:");
		tableLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		tableLabel1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableLabel1.setBounds(0, 175, 150, 50);
		add(tableLabel1);
		
		JLabel onLabel1 = new JLabel("ON:");
		onLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		onLabel1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		onLabel1.setBounds(0, 225, 150, 50);
		add(onLabel1);
		
		JLabel tableLabel2 = new JLabel("JOIN Table:");
		tableLabel2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		tableLabel2.setBounds(0, 300, 150, 50);
		add(tableLabel2);
		tableLabel2.setVisible(false);
		
		JLabel onLabel2 = new JLabel("ON:");
		onLabel2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		onLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		onLabel2.setBounds(0, 350, 150, 50);
		add(onLabel2);
		onLabel2.setVisible(false);
		
		JLabel tableLabel3 = new JLabel("JOIN Table:");
		tableLabel3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		tableLabel3.setBounds(0, 425, 150, 50);
		add(tableLabel3);
		tableLabel3.setVisible(false);
		
		JLabel onLabel3 = new JLabel("ON:");
		onLabel3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		onLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		onLabel3.setBounds(0, 475, 150, 50);
		add(onLabel3);
		onLabel3.setVisible(false);
		
		tableField = new JTextField();
		tableField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableField.setBounds(150, 100, 200, 50);
		add(tableField);
		tableField.setColumns(10);
		
		tableField1 = new JTextField();
		tableField1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableField1.setBounds(150, 175, 200, 50);
		add(tableField1);
		tableField1.setColumns(10);
		
		joinField1 = new JTextField();
		joinField1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		joinField1.setBounds(150, 225, 200, 50);
		add(joinField1);
		joinField1.setColumns(10);
		
		tableField2 = new JTextField();
		tableField2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableField2.setBounds(150, 300, 200, 50);
		add(tableField2);
		tableField2.setColumns(10);
		tableField2.setVisible(false);
		
		joinField2 = new JTextField();
		joinField2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		joinField2.setBounds(150, 350, 200, 50);
		add(joinField2);
		joinField2.setColumns(10);
		joinField2.setVisible(false);
		
		tableField3 = new JTextField();
		tableField3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableField3.setBounds(150, 425, 200, 50);
		add(tableField3);
		tableField3.setColumns(10);
		tableField3.setVisible(false);
			
		joinField3 = new JTextField();
		joinField3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		joinField3.setBounds(150, 475, 200, 50);
		add(joinField3);
		joinField3.setColumns(10);
		joinField3.setVisible(false);
		
		resultTable = new JTable();
		resultTable.setBounds(381, 101, 660, 600);
		add(resultTable);
		
		JButton submitButton = new JButton("Show Results");
		submitButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//replace with stuff
				showTable2 = !showTable2;
				tableLabel2.setVisible(showTable2);
				onLabel2.setVisible(showTable2);
				tableField2.setVisible(showTable2);
				joinField2.setVisible(showTable2);
				
				showTable3 = !showTable3;
				tableLabel3.setVisible(showTable3);
				onLabel3.setVisible(showTable3);
				tableField3.setVisible(showTable3);
				joinField3.setVisible(showTable3);
				
			}
		});
		submitButton.setBounds(150, 550, 200, 50);
		add(submitButton);
	}
}
