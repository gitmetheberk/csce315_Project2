
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
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
		frame.setTitle("Employee Locations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jdbc.query("USE adventureworks;");
		generateMap(frame);
		generatePieChart(frame);
		frame.getContentPane().setLayout(null);
		
		JLabel labelForChart = new JLabel("");
		labelForChart.setBounds(531, 309, 500, 350);
		frame.getContentPane().add(labelForChart);
		labelForChart.setIcon(new ImageIcon("src/employeePieChart.jpeg"));
		
		JLabel labelForMap = new JLabel("");
		labelForMap.setBounds(30, 74, 640, 320);
		frame.getContentPane().add(labelForMap);
		labelForMap.setIcon(new ImageIcon("src/employeeMap.jpeg"));
		
	}
	
	private void generateMap(JFrame frame) {
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
			
			//send HTTPS GET request to Google Static Map API to get a global view of the company
			String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?size=640x320&key=AIzaSyB02-tivryOguKZJ1sgnSb4HiSaWXPif0E&" + markers; //api request to google static maps API
			String destinationFile = "src/employeeMap.jpeg"; //where the resulting static map image will be saved
			URL url = new URL(imageUrl); //point to web resource
			InputStream is = url.openStream(); //openStream() will send the HTTPS get request and will save the content of the HTTP reponse into an input stream
			OutputStream os = new FileOutputStream(destinationFile);
			
			byte[] b = new byte[2048];
			int length;
			
			while ((length = is.read(b)) != -1) { //read the response data byte-by-byte in 2048 byte increments and write into the output stream (which is hooked up to the file we want to save the image in)
	            os.write(b, 0, length);
	        }
			
			rs.close();
	        is.close();
	        os.close();
		    
		} catch (IOException e) {
		    e.printStackTrace();
		    System.exit(1);
		} catch (SQLException se) {
			se.printStackTrace();
		}

	}
	
	private void generatePieChart(JFrame frame) {
		try {
			
			JFreeChart employeeChart = ChartFactory.createPieChart("Number of Employees in State Provinces", createDataset(), true, false, false);
			
			//changing label formats
			PiePlot plotForChart = (PiePlot) employeeChart.getPlot();
			plotForChart.setLabelGenerator(new StandardPieSectionLabelGenerator("{1} employees ({2})")); //formats label for each of the pie slices where 1 is the value for the key, 2 is the percentage
			
			//change pie slice colors and cut one of the slices off
			plotForChart.setSectionPaint(0, new Color(64, 224, 208));
			plotForChart.setExplodePercent(0, 0.20);
			plotForChart.setSectionPaint(1, new Color(65, 105, 225));
			
			plotForChart.setLabelBackgroundPaint(new Color(220, 220, 220)); 
			plotForChart.setLabelFont(new Font("Tahoma", Font.PLAIN, 15)); //JFree default font is Tahoma
			
			plotForChart.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}")); //formats legend labels
			
			//change legend font
			LegendTitle legend = employeeChart.getLegend();
			Font labelFont = new Font("Tahoma", Font.PLAIN, 15);
			legend.setItemFont(labelFont);
			legend.setMargin(5, 5, 7.5, 5);
			
			//get rid of pie chart shadow
			plotForChart.setShadowPaint(null);
			
			//change background color
			employeeChart.setBackgroundPaint(new Color(168, 168, 168));
			
			ChartUtilities.saveChartAsJPEG(new File("src/employeePieChart.jpeg"), employeeChart, 500, 350);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private PieDataset createDataset() {
		DefaultPieDataset data = new DefaultPieDataset();
		try {
			jdbc.query("USE adventureworks;");
			
			ResultSet numEmployeesInWA = jdbc.query("SELECT COUNT(employeeaddress.EmployeeID) as NumEmployeesInProvince "
									+ "FROM employeeaddress "
									+ "JOIN address ON employeeaddress.AddressID = address.AddressID "
									+ "JOIN stateprovince ON address.StateProvinceID = stateprovince.StateProvinceID "
									+ "WHERE stateprovince.Name LIKE \"Washington\";");
			
			ResultSet numEmployeesNotInWA = jdbc.query("SELECT COUNT(employeeaddress.EmployeeID) as NumEmployeesInProvince "
									+ "FROM employeeaddress "
									+ "JOIN address ON employeeaddress.AddressID = address.AddressID "
									+ "JOIN stateprovince ON address.StateProvinceID = stateprovince.StateProvinceID "
									+ "WHERE stateprovince.Name NOT LIKE \"Washington\";");
			
			while (numEmployeesInWA.next()) {
				data.setValue("Washington", Double.parseDouble(numEmployeesInWA.getString(1))); //1st column is the number of employees in the province, 2nd column is the province name
			}
			
			while (numEmployeesNotInWA.next()) {
				data.setValue("All Others", Double.parseDouble(numEmployeesNotInWA.getString(1))); //1st column is the number of employees in the province, 2nd column is the province name
			}
			
			numEmployeesInWA.close();
			numEmployeesNotInWA.close();
			
		} catch (SQLException se) {
			se.printStackTrace();
		}
		return data;         
	}
	
}
