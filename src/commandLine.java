import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class commandLine extends JPanel {
	private SQL_CommandLineInterpreter CLI;
	private final JTextPane textPane_CLO = new JTextPane();
	private final JTextField textField_commandLineInput = new JTextField();
	private final JLabel label_jdbPrompt = new JLabel("jdb>");
	private final JButton button_clearCommandLine = new JButton("Clear");
	private final JButton button_submitCommand = new JButton("Submit");
	private final JScrollPane scrollPane_CLO = new JScrollPane();
	
	
	/**
	 * Create the panel.
	 */
	public commandLine(SQL_CommandLineInterpreter cli) {
		textField_commandLineInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Verify key pressed was enter and then runCLI()
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					runCLI();
				}
			}
		});
		textField_commandLineInput.setToolTipText("Command line input");
		textField_commandLineInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_commandLineInput.setBounds(59, 604, 774, 30);
		textField_commandLineInput.setColumns(10);
		CLI = cli;
		initGUI();
	}
	private void initGUI() {
		setLayout(null);
		scrollPane_CLO.setBounds(0, 0, 1059, 584);
		
		add(scrollPane_CLO);
		scrollPane_CLO.setViewportView(textPane_CLO);
		textPane_CLO.setToolTipText("Command line output");
		textPane_CLO.setEditable(false);
		textPane_CLO.setBackground(Color.LIGHT_GRAY);
		
		add(textField_commandLineInput);
		label_jdbPrompt.setFont(new Font("Times New Roman", Font.BOLD, 25));
		label_jdbPrompt.setBounds(0, 604, 78, 30);
		
		add(label_jdbPrompt);
		button_clearCommandLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane_CLO.setText("");
			}
		});
		button_clearCommandLine.setToolTipText("Clear the command line output");
		button_clearCommandLine.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_clearCommandLine.setBounds(962, 604, 85, 30);
		
		add(button_clearCommandLine);
		button_submitCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runCLI();
			}
		});
		button_submitCommand.setFont(new Font("Tahoma", Font.BOLD, 15));
		button_submitCommand.setBounds(843, 604, 109, 30);
		
		add(button_submitCommand);
	}
	
	// Driver function
	private void runCLI() {
		String toPane = "";
		
		// Grab user input and pass to CLI
		String input = textField_commandLineInput.getText();
		
		// Catch empty input
		if (input.isEmpty()) {
			return;
		}
		
		// Catch user attempting to exit with deprecated command
		if (input.equalsIgnoreCase("EXIT CLI")) {
			toPane += "WARNING: To exit the CLI, please close this window\n";
			try {
				textPane_CLO.getDocument().insertString(textPane_CLO.getDocument().getLength(), toPane, null);
			} catch (BadLocationException e) {
				System.out.println("ERROR: An error occured while appending command line output to the JTextPane");
				e.printStackTrace();
			}
			textField_commandLineInput.setText("");
			return;
		}

		// Error for comments in SQL query, usually resulting from a copy-paste of a query
		if (input.contains("-- ")) {
			toPane += "ERROR: Comments in SQL queries can have unexpected consequences\n";
			try {
				textPane_CLO.getDocument().insertString(textPane_CLO.getDocument().getLength(), toPane, null);
			} catch (BadLocationException e) {
				System.out.println("ERROR: An error occured while appending command line output to the JTextPane");
				e.printStackTrace();
			}
			textField_commandLineInput.setText("");
			return;
		}
		
		// Pass input to CLI and append the results
		toPane += CLI.processInput(input);
		
		try {
			textPane_CLO.getDocument().insertString(textPane_CLO.getDocument().getLength(), toPane, null);
		} catch (BadLocationException e) {
			System.out.println("ERROR: An error occured while appending command line output to the JTextPane");
			e.printStackTrace();
		}
		textField_commandLineInput.setText("");
		return;
	}
}
