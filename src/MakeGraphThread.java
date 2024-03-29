import javax.swing.JFrame;

//this class is used so that we can indicate to the user that the graph has not finished being created

public class MakeGraphThread implements Runnable {

	SQL_CommandLineInterpreter CLI;

    public MakeGraphThread(SQL_CommandLineInterpreter _CLI) {
        CLI = _CLI;
    }

    public void run() {
    	CLI.processInput("jdb-plot-schema");
    	JFrame schemaFrame = new jFrameForSchema();
		schemaFrame.setVisible(true);
    }
}
