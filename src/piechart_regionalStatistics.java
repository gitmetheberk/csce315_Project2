import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.util.HashMap;
import java.awt.Dimension;
import java.sql.*;

public class piechart_regionalStatistics {
	private SQL_JDBC jdbc;
	private JFreeChart chartUS;
	private JFreeChart chartOther;
	
	/**
	 * Create the panel.
	 */
	public piechart_regionalStatistics(SQL_JDBC _jdbc) {
		jdbc = _jdbc;
			
		// Define dictionaries(hashmaps) to hold important info
		HashMap<Integer, String> idName = new HashMap<Integer, String>();
		HashMap<Integer, Integer> idValueUS = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> idValueOther = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> addressIDStateProvinceID = new HashMap<Integer, Integer>();
		
		try {
			// Get data from StateProvince and load into data structures
			ResultSet rs = jdbc.query("SELECT StateProvinceID, Name, CountryRegionCode FROM stateprovince");
			Integer id;
			String name;
			
			while (rs.next()) {
				id = rs.getInt("StateProvinceID");
				name = rs.getString("Name");
				
				idName.put(id, name);
				
				// Depending on CountryRegionCode, place in US or other dataset
				if (rs.getString("CountryRegionCode").compareTo("US") == 0) {
					idValueUS.put(id, 0);
				} else {
					idValueOther.put(id, 0);
				}

				
			}
			
			// Setup HashMap to connect AddressID's to StateProvinceID's
			rs = jdbc.query("SELECT AddressID, StateProvinceID FROM address");

			while (rs.next()) {
				// Map AddressID to StateProvinceID
				addressIDStateProvinceID.put(rs.getInt("AddressID"), rs.getInt("StateProvinceID"));
			}

			// Get data from salesorderheader
			rs = jdbc.query("SELECT SubTotal, ShipToAddressID FROM salesorderheader");
			
			//int count = 0;
			while (rs.next()) {
				// Get the ShipToAddressID and use it to find the StateProvinceID
				id = addressIDStateProvinceID.get(rs.getInt("ShipToAddressID"));
				
				// Increment the value in idValueUS or idValueOther
				if (idValueUS.get(id) != null) {
					idValueUS.put(id, idValueUS.get(id) + rs.getInt("SubTotal"));
				} else {
					idValueOther.put(id, idValueOther.get(id) + rs.getInt("Subtotal"));
				}
				
			//	count++;
			}
			//System.out.println("Loop iterations:" + count);
			
			
			
		} catch (SQLException e) {
			System.out.println("ERROR: An error has occured while loading the dashboard");
			System.out.println(e);
			e.printStackTrace();
			return;
		}
		
		// Loop through data and construct piecharts
		DefaultPieDataset datasetUS = new DefaultPieDataset();
		DefaultPieDataset datasetOther = new DefaultPieDataset();
		for (Integer id : idValueUS.keySet()) {
			if (idValueUS.get(id) != 0) {
					datasetUS.setValue(idName.get(id), idValueUS.get(id));
			}
		}
		
		for (Integer id : idValueOther.keySet()) {
			if (idValueOther.get(id) != 0) {
					datasetOther.setValue(idName.get(id), idValueOther.get(id));
			}
		}
		
		// Create the charts
		chartUS = ChartFactory.createPieChart(
				"US Sales by State", datasetUS, false, true, false);
		chartOther = ChartFactory.createPieChart(
				"Sales Outside the US", datasetOther, false, true, false);
	}
	
	public JPanel getChartPanel_US(){
		ChartPanel cp = new ChartPanel(chartUS);
		cp.setSize(new Dimension(700,575));
		return cp;
	}
	
	public JPanel getChartPanel_Other() {
		ChartPanel cp = new ChartPanel(chartOther);
		cp.setSize(new Dimension(700,475));
		return cp;
	}
}
