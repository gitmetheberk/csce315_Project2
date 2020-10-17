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
		jdbc = _jdbc; 

		CategoryDataset dataset = createDataset();

		JFreeChart chart = createChart(dataset);
		barChart = new ChartPanel(chart);
		
	}

	private CategoryDataset createDataset() {
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		try {

//			ResultSet topTenProducts = jdbc.query("SELECT salesorderdetail.ProductID, product.Name, sum(linetotal) as Total "
//					+ "FROM purchaseorderdetail "
//					+ "INNER JOIN salesorderheader ON salesorderdetail.SalesOrderID=saleorderheader.SalesOrderID"
//					+ "INNER JOIN product ON salesorderdetail.ProductID=product.ProductID "
//					+ "WHERE OrderDate like \"%-12-%\" "
//					+ "OR OrderDate like \"%-01-%\" "
//					+ "OR OrderDate like \"%-02-%\" "
//					+ "GROUP BY ProductID "
//					+ "ORDER BY Total DESC LIMIT 10;");

			
			ResultSet topTenProducts = jdbc.query("SELECT Total, sales.ProductID, product.Name "
					+ "FROM (SELECT ProductID, sum(LineTotal) AS Total "
					+ "	FROM salesorderdetail "
					+ "	INNER JOIN (SELECT * FROM salesorderheader "
					+ "	where "
					+ "		orderdate like '%-12-%' OR "
					+ "		orderdate like '%-01-%' OR "
					+ "		orderdate like '%-02-%') AS header "
					+ "	ON salesorderdetail.SalesOrderID = header.SalesOrderID "
					+ "	GROUP BY ProductID "
					+ "	ORDER BY Total DESC "
					+ "	LIMIT 10) AS sales "
					+ "INNER JOIN product "
					+ "ON sales.ProductID=product.ProductID;");

			while (topTenProducts.next()) {
				//1st column is the number of employees in the province, 2nd column is the province name
				data.setValue(topTenProducts.getFloat("Total"), "Total Profit", topTenProducts.getString("ProductID")); 
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
				"ProductID",
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
