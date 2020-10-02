import net.proteanit.sql.DbUtils;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JCheckBox;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.ResultSet;

public class showSelectedColumns extends JPanel {
	private JTable resultTable;
	private JTextField tableField;
	private JTextField columnField1;
	private JTextField columnField2;
	private JTextField columnField3;
	private JTextField columnField4;
	private JTextField columnField5;
	private JTextField columnField6;
	
	private boolean showAll;
	private boolean showc2;
	private boolean showc3;
	private boolean showc4;
	private boolean showc5;
	private boolean showc6;

	public showSelectedColumns() {
		//
	}
	
	public showSelectedColumns(SQL_JDBC jdbc) {
		initGUI(jdbc);
	}


	private void initGUI(SQL_JDBC jdbc) {
		setLayout(null);
		setBounds(100, 100, 1080, 720);
		
		showAll = true;
		showc2 = false;
		showc3 = false;
		showc4 = false;
		showc5 = false;
		showc6 = false;
		
		JLabel panelLabel = new JLabel("Show Columns");
		panelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelLabel.setFont(new Font("Tahoma", Font.BOLD, 55));
		panelLabel.setBounds(176, 11, 728, 79);
		add(panelLabel);
		
		JLabel tableLabel = new JLabel("Table:");
		tableLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tableLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableLabel.setBounds(50, 100, 100, 50);
		add(tableLabel);
		
		JLabel columnLabel1 = new JLabel("Column:");
		columnLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		columnLabel1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnLabel1.setBounds(0, 175, 150, 50);
		add(columnLabel1);
		
		JLabel columnLabel2 = new JLabel("Column:");
		columnLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		columnLabel2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnLabel2.setBounds(0, 235, 150, 50);
		add(columnLabel2);
		columnLabel2.setVisible(false);
		
		JLabel columnLabel3 = new JLabel("Column:");
		columnLabel3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		columnLabel3.setBounds(0, 295, 150, 50);
		add(columnLabel3);
		columnLabel3.setVisible(false);
		
		JLabel columnLabel4 = new JLabel("Column:");
		columnLabel4.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		columnLabel4.setBounds(0, 355, 150, 50);
		add(columnLabel4);
		columnLabel4.setVisible(false);
		
		JLabel columnLabel5 = new JLabel("Column:");
		columnLabel5.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		columnLabel5.setBounds(0, 415, 150, 50);
		add(columnLabel5);
		columnLabel5.setVisible(false);
		
		JLabel columnLabel6 = new JLabel("Column:");
		columnLabel6.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		columnLabel6.setBounds(0, 475, 150, 50);
		add(columnLabel6);
		columnLabel6.setVisible(false);
		
		tableField = new JTextField();
		tableField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		tableField.setBounds(150, 100, 200, 50);
		add(tableField);
		tableField.setColumns(10);
		
		columnField1 = new JTextField();
		columnField1.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnField1.setBounds(150, 175, 200, 50);
		columnField1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!columnField1.getText().equals("")) {
					showc2 = true;
					if(!columnField2.getText().equals(""))showc3 = true;
					if(!columnField3.getText().equals("") && showc3)showc4 = true;
					if(!columnField4.getText().equals("") && showc4)showc5 = true;
					if(!columnField5.getText().equals("") && showc5)showc6 = true;
					columnLabel2.setVisible(showc2);
					columnField2.setVisible(showc2);
				}else {
					showc2 = false;
					showc3 = false;
					showc4 = false;
					showc5 = false;
					showc6 = false;
					columnLabel2.setVisible(showc2);
					columnField2.setVisible(showc2);
					columnLabel3.setVisible(showc3);
					columnField3.setVisible(showc3);
					columnLabel4.setVisible(showc4);
					columnField4.setVisible(showc4);
					columnLabel5.setVisible(showc5);
					columnField5.setVisible(showc5);
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
				}
			}
		});
		add(columnField1);
		columnField1.setColumns(10);
		
		columnField2 = new JTextField();
		columnField2.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnField2.setBounds(150, 235, 200, 50);
		columnField2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!columnField2.getText().equals("")) {
					showc3 = true;
					if(!columnField3.getText().equals(""))showc4 = true;
					if(!columnField4.getText().equals("") && showc4)showc5 = true;
					if(!columnField5.getText().equals("") && showc5)showc6 = true;
					columnLabel3.setVisible(showc3);
					columnField3.setVisible(showc3);
					columnLabel4.setVisible(showc4);
					columnField4.setVisible(showc4);
					columnLabel5.setVisible(showc5);
					columnField5.setVisible(showc5);
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
					
				}else {
					showc3 = false;
					showc4 = false;
					showc5 = false;
					showc6 = false;
					columnLabel3.setVisible(showc3);
					columnField3.setVisible(showc3);
					columnLabel4.setVisible(showc4);
					columnField4.setVisible(showc4);
					columnLabel5.setVisible(showc5);
					columnField5.setVisible(showc5);
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
				}
			}
		});
		add(columnField2);
		columnField2.setColumns(10);
		columnField2.setVisible(false);
		
		columnField3 = new JTextField();
		columnField3.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnField3.setBounds(150, 295, 200, 50);
		columnField3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!columnField3.getText().equals("")) {
					showc4 = true;
					if(!columnField4.getText().equals(""))showc5 = true;
					if(!columnField5.getText().equals("") && showc5)showc6 = true;
					columnLabel4.setVisible(showc4);
					columnField4.setVisible(showc4);
					columnLabel5.setVisible(showc5);
					columnField5.setVisible(showc5);
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
				}else {
					showc4 = false;
					showc5 = false;
					showc6 = false;
					columnLabel4.setVisible(showc4);
					columnField4.setVisible(showc4);
					columnLabel5.setVisible(showc5);
					columnField5.setVisible(showc5);
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
				}
			}
		});
		add(columnField3);
		columnField3.setColumns(10);
		columnField3.setVisible(false);
		
		columnField4 = new JTextField();
		columnField4.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnField4.setBounds(150, 355, 200, 50);
		columnField4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!columnField4.getText().equals("")) {
					showc5 = true;
					if(!columnField6.getText().equals(""))showc6 = true;
					columnLabel5.setVisible(showc5);
					columnField5.setVisible(showc5);
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
				}else {
					showc5 = false;
					showc6 = false;
					columnLabel5.setVisible(showc5);
					columnField5.setVisible(showc5);
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
				}
			}
		});
		add(columnField4);
		columnField4.setColumns(10);
		columnField4.setVisible(false);
		
		columnField5 = new JTextField();
		columnField5.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnField5.setBounds(150, 415, 200, 50);
		columnField5.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!columnField5.getText().equals("")) {
					showc6 = true;
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
				}else {
					showc5 = false;
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
				}
			}
		});
		add(columnField5);
		columnField5.setColumns(10);
		columnField5.setVisible(false);
			
		columnField6 = new JTextField();
		columnField6.setFont(new Font("Tahoma", Font.PLAIN, 25));
		columnField6.setBounds(150, 475, 200, 50);
		add(columnField6);
		columnField6.setColumns(10);
		columnField6.setVisible(false);
		
		JScrollPane resultPane = new JScrollPane();
		resultPane.setBounds(381, 101, 660, 600);
		add(resultPane);
		
		resultTable = new JTable();
		resultPane.setViewportView(resultTable);
		
		JCheckBox allCheckBox = new JCheckBox("Select all Columns");
		allCheckBox.setSelected(false);
		allCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showAll = !showAll;
				if (showAll) {
					columnLabel1.setVisible(showAll);
					columnField1.setVisible(showAll);
					columnLabel2.setVisible(showc2);
					columnField2.setVisible(showc2);
					columnLabel3.setVisible(showc3);
					columnField3.setVisible(showc3);
					columnLabel4.setVisible(showc4);
					columnField4.setVisible(showc4);
					columnLabel5.setVisible(showc5);
					columnField5.setVisible(showc5);
					columnLabel6.setVisible(showc6);
					columnField6.setVisible(showc6);
					allCheckBox.setSelected(!showAll);
				}else {
					columnLabel1.setVisible(showAll);
					columnField1.setVisible(showAll);
					columnLabel2.setVisible(showAll);
					columnField2.setVisible(showAll);
					columnLabel3.setVisible(showAll);
					columnField3.setVisible(showAll);
					columnLabel4.setVisible(showAll);
					columnField4.setVisible(showAll);
					columnLabel5.setVisible(showAll);
					columnField5.setVisible(showAll);
					columnLabel6.setVisible(showAll);
					columnField6.setVisible(showAll);
					allCheckBox.setSelected(!showAll);
				}
			}
		});
		allCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 25));
		allCheckBox.setBounds(150, 550, 230, 50);
		add(allCheckBox);
		
		JButton submitButton = new JButton("Show Results");
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in the table name.");
					return;
				}
				if (columnField1.getText().equals("") && !allCheckBox.isSelected()) {
					JOptionPane.showMessageDialog(null, "Please fill in at least one column or select the box.");
					return;
				}
				String query = "SELECT ";
				if (allCheckBox.isSelected()) {
					query = query + "*";
				}else {
					query = query + columnField1.getText();
					if (!columnField2.getText().equals("") && showc2) {
						query = query + ", " + columnField2.getText();
					}
					if (!columnField3.getText().equals("") && showc3) {
						query = query + ", " + columnField3.getText();
					}
					if (!columnField4.getText().equals("") && showc4) {
						query = query + ", " + columnField4.getText();
					}
					if (!columnField5.getText().equals("") && showc5) {
						query = query + ", " + columnField5.getText();
					}
					if (!columnField6.getText().equals("") && showc6) {
						query = query + ", " + columnField6.getText();
					}
				}
				
				query = query + " FROM " + tableField.getText();
				query = query + ";";
				
				String sql = "USE adventureworks;";
				ResultSet rs = jdbc.query(sql);
				rs = jdbc.query(query);

				
				if (rs == null) {
					JOptionPane.showMessageDialog(null, "No columns to return.");
					return;
				}else {
					resultTable.setModel(DbUtils.resultSetToTableModel(rs));
				}
				
				if (resultTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Warning: Your query produced zero results");
				}
			}
		});
		submitButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		submitButton.setBounds(150, 625, 200, 50);
		add(submitButton);
	}
}
