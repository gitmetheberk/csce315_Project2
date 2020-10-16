import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.File;
//import java.sql.SQLException;

import javax.swing.JPanel;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartUtilities;
import org.jfree.data.category.DefaultCategoryDataset;


public class MonthlyOrders extends JPanel {
	
	private SQL_JDBC jdbc;

	public MonthlyOrders(SQL_JDBC _jdbc) {
		jdbc=_jdbc;
		// Create dataset
	    DefaultCategoryDataset dataset = createDataset();
	    // Create chart  
	    JFreeChart chart = ChartFactory.createLineChart("Monthly Orders", "Month", "Number of Orders", dataset, PlotOrientation.VERTICAL, true, true, false);
		// creatLineChart(Chart title, X-Axis Label, Y-Axis Label, dataset)
	    try {
			ChartUtilities. saveChartAsJPEG(new File("src/MonthlyOrderChart.jpeg"), chart,500,350);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ChartPanel panel = new ChartPanel(chart);
	    //setContentPane(panel);
	    
  }

  private DefaultCategoryDataset createDataset() {
	  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	  
	  jdbc.query("USE adventureworks;");
		ResultSet rs = jdbc.query("SELECT OrderDate FROM adventureworks.salesorderheader;");
		String firstDate="";
		//Define 12 counter
		int cntJan=0;
		int cntFeb=0;
		int cntMar=0;
		int cntApr=0;
		int cntMay=0;
		int cntJun=0;
		int cntJul=0;
		int cntAug=0;
		int cntSep=0;
		int cntOct=0;
		int cntNov=0;
		int cntDec=0;
		try {
			while (rs.next()) {
				firstDate = rs.getString(1);
				firstDate = firstDate.substring(5, 7);
				if (firstDate.equals("01")) {
					cntJan++;
				}
				else if (firstDate.equals("02")) {
					cntFeb++;
				}
				else if (firstDate.equals("03")) {
					cntMar++;
				}
				else if (firstDate.equals("04")) {
					cntApr++;
				}
				else if (firstDate.equals("05")) {
					cntMay++;
				}
				else if (firstDate.equals("06")) {
					cntJun++;
				}
				else if (firstDate.equals("07")) {
					cntJul++;
				}
				else if (firstDate.equals("08")) {
					cntAug++;
				}
				else if (firstDate.equals("09")) {
					cntSep++;
				}
				else if (firstDate.equals("10")) {
					cntOct++;
				}
				else if (firstDate.equals("11")) {
					cntNov++;
				}
				else if (firstDate.equals("12")) {
					cntDec++;
				} 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		String series1 = "Number of Orders";
		    
		 dataset.addValue(cntJan, series1, "January");
		 dataset.addValue(cntFeb, series1, "February");
		 dataset.addValue(cntMar, series1, "March");
		 dataset.addValue(cntApr, series1, "April");
		 dataset.addValue(cntMay, series1, "May");
		 dataset.addValue(cntJun, series1, "June");
		 dataset.addValue(cntJul, series1, "July");
		 dataset.addValue(cntAug, series1, "August");
		 dataset.addValue(cntSep, series1, "September");
		 dataset.addValue(cntOct, series1, "Octomber");
		 dataset.addValue(cntNov, series1, "November");
		 dataset.addValue(cntDec, series1, "December");
		  
    return dataset;
  }

}