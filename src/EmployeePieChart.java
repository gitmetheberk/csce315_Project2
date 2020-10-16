import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class EmployeePieChart extends JPanel{
	private SQL_JDBC jdbc;
	
	public EmployeePieChart(SQL_JDBC _jdbc) {
		jdbc = _jdbc;
		generatePieChart();
	}
	
	private void generatePieChart() {
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
