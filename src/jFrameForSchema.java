import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class jFrameForSchema extends JFrame{
	
	public jFrameForSchema(){
		
		//setting title and other things for the db schema jframe
		setTitle("Database Schema");
		setResizable(false);
		setBounds(30, 30, 1500, 800);
		getContentPane().setLayout(null);
		
		//set up schema image container
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1486, 763);
		getContentPane().add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 1486, 763);
		scrollPane.setViewportView(lblNewLabel);
		
		//convert .dot file to .png file
		try {
			Runtime rt = Runtime.getRuntime();
		    String[] args = {"dot", "-Tpng", "src/schema.dot", "-o", "src/schema.png"};
		    Process p = rt.exec(args); //create a new process that runs the "dot" graphviz command
		    p.waitFor(); //wait until dot executable has generated the picture of the schema graph
		    BufferedImage img = ImageIO.read(new File("src/schema.png")); //use buffered image type to make the image smaller (it's like 8000x3000 bytes when generated by the dot command)
		    Image resizedImg = img.getScaledInstance(4171, 1712, Image.SCALE_SMOOTH); //scale down image by a factor of 2
			lblNewLabel.setIcon(new ImageIcon(resizedImg));
		}
		
		catch (IOException ioe) {
			System.out.println("ERROR: An I/O exception occurred while trying to create the database schema image");
			ioe.printStackTrace();
		}
		catch (InterruptedException ie) {
			System.out.println("ERROR: An interrupted exception occurred while trying to create the database schema image");
			ie.printStackTrace();
		}
	}
}