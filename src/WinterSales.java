import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JPanel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class WinterSales {
	private SQL_JDBC jdbc;
	private JPanel barChart;

	public WinterSales(SQL_JDBC _jdbc) {
		if (_jdbc == null) {
			jdbc = new SQL_JDBC(false);
			jdbc.connect("jdbc:mysql://192.168.1.2:3306", "user2", "c8kPA8eHaXsBNEPE");
		} else {
			jdbc = _jdbc; 
		}

		CategoryDataset dataset = createDataset();

		JFreeChart chart = createChart(dataset);
		barChart = new ChartPanel(chart);
		
	}

	private CategoryDataset createDataset() {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		try {

			ResultSet topTenProducts = jdbc.query("SELECT salesorderdetail.ProductID, product.Name, sum(linetotal) as Total "
					+ "FROM purchaseorderdetail "
					+ "INNER JOIN salesorderheader ON salesorderdetail.SalesOrderID=saleorderheader.SalesOrderID"
					+ "INNER JOIN product ON salesorderdetail.ProductID=product.ProductID "
					+ "WHERE OrderDate like \"%-12-%\" "
					+ "OR OrderDate like \"%-01-%\" "
					+ "OR OrderDate like \"%-02-%\" "
					+ "GROUP BY ProductID "
					+ "ORDER BY Total DESC LIMIT 10;");
			
//			// Get all product names and store in a HashMap
//			HashMap<Integer, String> idName = new HashMap<Integer, String>();
//			

			while (topTenProducts.next()) {
				//1st column is the number of employees in the province, 2nd column is the province name
				data.setValue(topTenProducts.getFloat("Total"), "Total Profit", topTenProducts.getString("Name")); 
			}

			topTenProducts.close();

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return data; 
	}
	private JFreeChart createChart(CategoryDataset dataset) {

		JFreeChart barChartWorking = ChartFactory.createBarChart(
				"Top 10 Products in Winter",
				"Product",
				"Profit ($)",
				dataset,
				PlotOrientation.VERTICAL,
				false, true, false);

		return barChartWorking;
	}
	
	public JPanel getChart() {
		return barChart;
	}
	
}
