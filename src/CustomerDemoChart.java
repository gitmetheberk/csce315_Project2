import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler.AnnotationType;

@SuppressWarnings("serial")
public class CustomerDemoChart extends JPanel {

final PieChart chart = new PieChartBuilder().width(300).height(300).title("Customer Demographics").build();
	
	public CustomerDemoChart(SQL_JDBC jdbc) {
		//setLayout(null);
		initChart(jdbc);
		JPanel chartPanel = new XChartPanel<PieChart>(chart);
		//chartPanel.setBounds(0, 0, 400, 400);
		//chartPanel.setLayout(null);
		add(chartPanel);
	}
	
	private void initChart(SQL_JDBC jdbc) {
		chart.getStyler().setLegendVisible(false);
	    chart.getStyler().setAnnotationType(AnnotationType.LabelAndPercentage);
	    chart.getStyler().setAnnotationDistance(.5);
	    chart.getStyler().setPlotContentSize(.7);
	    chart.getStyler().setStartAngleInDegrees(0);
	    chart.getStyler().setSeriesColors(new Color[] {Color.cyan, Color.PINK});

	    //gathering data from the database
	    int numMale = 0;
	    int numFemale = 0;
	    try {
		    String query = "USE adventureworks;";
			ResultSet rs = jdbc.query(query);
		    query = "SELECT COUNT(*) From individual WHERE Demographics LIKE '%<Gender>M</Gender>%'";
			rs = jdbc.query(query);
			if (rs.next()){
				numMale = rs.getInt(1);
			}
		    query = "SELECT COUNT(*) From individual WHERE Demographics LIKE '%<Gender>F</Gender>%'";
			rs = jdbc.query(query);
			if (rs.next()){
				numFemale = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    // Adding info to the pieChart
	    chart.addSeries("Male", numMale);
	    chart.addSeries("Female", numFemale);
	}

}
