import java.awt.EventQueue;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EmployeeLocationMap {

	private JFrame frame;
	private SQL_JDBC jdbc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SQL_JDBC jdbc = new SQL_JDBC();
					EmployeeLocationMap window = new EmployeeLocationMap(jdbc);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeLocationMap(SQL_JDBC _jdbc) {
		initialize(_jdbc);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(SQL_JDBC _jdbc) {
		jdbc = _jdbc;
		frame = new JFrame();
		frame.setBounds(100, 100, 1080, 720);
		frame.setTitle("Current Adventure Works Office Locations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			
			//get the list of state provinces where employees reside and make markers for each of the state provinces
			jdbc.query("USE adventureworks;");
			ResultSet rs = jdbc.query("SELECT DISTINCT stateprovince.Name "
					+ "FROM employeeaddress "
					+ "JOIN address ON employeeaddress.AddressID = address.AddressID "
					+ "JOIN stateprovince ON "
					+ "address.StateProvinceID = stateprovince.StateProvinceID "
					+ "ORDER BY stateprovince.Name;");
			String markers = "markers=color:red%7Csize:tiny%7Clabel:L%7C"; //%7C is the url encoding for "|"
			while (rs.next()) {
				markers += rs.getString(1) + "%7C"; //column indices start at 1 for result sets,,, %2C is the url encoding for ","
			}
			markers = markers.substring(0, markers.length() - 3); //get rid of last %7C
			System.out.println(markers);
			
			//send HTTPS GET request to Google Static Map API to get a global view of the company
            String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?size=640x320&key=AIzaSyB02-tivryOguKZJ1sgnSb4HiSaWXPif0E&" + markers; //api request to google static maps API
            System.out.println(imageUrl);
            String destinationFile = "src/employeeMap.jpg"; //where the resulting static map image will be saved
            URL url = new URL(imageUrl); //point to web resource
            InputStream is = url.openStream(); //openStream() will send the HTTPS get request and will save the content of the HTTP reponse into an input stream
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) { //read the response data byte-by-byte in 2048 byte increments and write into the output stream (which is hooked up to the file we want to save the image in)
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException se) {
        	se.printStackTrace();
        }

        frame.add(new JLabel(new ImageIcon("src/employeeMap.jpg"))); //put an edited version of the image icon inside of a Jlabel

        frame.setVisible(true);

	}	

}
