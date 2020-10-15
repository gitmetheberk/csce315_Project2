import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WinterSales extends JFrame {

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
					WinterSales window = new WinterSales(jdbc);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public WinterSales(SQL_JDBC _jdbc) {
		initialize(_jdbc);
	}

	private void initialize(SQL_JDBC _jdbc) {
		CategoryDataset dataset = createDataset();

		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		add(chartPanel);

		pack();
		setTitle("Bar Chart");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private CategoryDataset createDataset() {


		DefaultCategoryDataset data = new DefaultCategoryDataset();
		try {
			jdbc.query("USE adventureworks;");

			ResultSet topTenProducts = jdbc.query("SELECT purchaseorderdetail.ProductID, sum(linetotal) as Total "
					+ "FROM purchaseorderdetail "
					+ "WHERE DueDate like \"%-12-%\" "
					+ "OR DueDate like \"%-01-%\" "
					+ "OR DueDate like \"%-02-%\" "
					+ "GROUP BY ProductID "
					+ "ORDER BY Total DESC LIMIT 10;");

			while (topTenProducts.next()) {
				data.setValue(Double.parseDouble(topTenProducts.getString(2)), "Total Profit", topTenProducts.getString(1)); //1st column is the number of employees in the province, 2nd column is the province name
			}

			topTenProducts.close();

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return data; 
	}
	private JFreeChart createChart(CategoryDataset dataset) {

		JFreeChart barChart = ChartFactory.createBarChart(
				"Top 10 Products in Winter",
				"",
				"Profit ($)",
				dataset,
				PlotOrientation.VERTICAL,
				false, true, false);

		return barChart;
	}
}
