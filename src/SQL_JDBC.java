import java.sql.*;

// Utility class to query the database, expects SQL code and returns ResultSets
public class SQL_JDBC {
	// Note by setting the serverTimezone argument, date/time stamps may be incorrect
	// private static final String DB_URL = "jdbc:mysql://192.168.1.77:3306/adventureworks?serverTimezone=US/Central";
	private String DB_URL = "jdbc:mysql://localhost:3306";

	// Not a fan of just having the password in the java file, just saying
	// The right way to do this is probably referencing environment variables which van be git ignored or asking the user to login them selves
	private String USER = "user2";
	private String PASS = "c8kPA8eHaXsBNEPE";
	
	// Holds connection info for the DB, established in the constructor
	private Connection connection;

	// Status flag: Whether or not connection is valid
	private boolean connected;
	
	// This remains to provide legacy functionality to SQL_ComamandLine
	public SQL_JDBC() {
		// Establish DB connection with error handling
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			connected = true;
		} catch (SQLException e) {
			System.out.println("ERROR: SQL EXCEPTION WHILE CONNECTING TO DATABASE");
			System.out.println(e);
			e.printStackTrace();
			connection = null;
			connected = false;
		} catch (Exception e) {
			System.out.println("ERROR: EXCEPTION WHILE CONNECTING TO DATABASE");
			System.out.println(e);
			e.printStackTrace();
			connection = null;
			connected = false;
		}
	}
	
	// Needed a clean constructor for use in SQL_LOGIN, added a dummy parameter
	public SQL_JDBC(boolean notAValue) {
		connection = null;
		connected = false;
		DB_URL = "";
		PASS = "";
		USER = "";
	}
	
	// Utility function which attempts to connect to the DB and returns whether or not it was successful
	public boolean connect() {
		if (connected) {
			return true;
		} else {
			try {
				connection = DriverManager.getConnection(DB_URL, USER, PASS);
				connected = true;
			} catch (SQLException e) {
				System.out.println("ERROR: SQL EXCEPTION WHILE CONNECTING TO DATABASE");
				System.out.println(e);
				e.printStackTrace();
				connection = null;
				connected = false;
			} catch (Exception e) {
				System.out.println("ERROR: EXCEPTION WHILE CONNECTING TO DATABASE");
				System.out.println(e);
				e.printStackTrace();
				connection = null;
				connected = false;
			}
		}
		return connected;
	}
	
	// Attempt to connect to the DB with custom login
	public boolean connect(String url, String user, String pass) {
		DB_URL = url;
		USER = user;
		PASS = pass;
		
		return connect();
	}
	
	
	
	public ResultSet query(String query) {
		ResultSet rs = null;
		if (!connected) {
			System.out.println("ERROR: JDBC not connected to Database");
			return rs;
		}
		
		// Error handling surrounding queries
		try {
			// Initialize a statement from the connection
			Statement st = connection.createStatement();
			
			// Execute the query
			st.executeQuery("use adventureworks");
			rs = st.executeQuery(query);
			
			// Check for null ResultSet and return
			if (rs == null) {
				System.out.println("WARNING: null ResultSet returned in SQL_JDBC.query()");
				System.out.println("QUERY: " + query);
			}
			return rs;
			
			
		} catch (SQLException e) {
			System.out.println("ERROR: SQL EXCEPTION IN SQL_JDBC.query()");
			System.out.println("QUERY: " + query);
			System.out.println("Error: " + e);
			//System.out.println(e.getStackTrace());
			
			rs = null;
			return rs;
		}
		
	}
	
	// Function that executes an update to the DB (used for views)
	public int update(String updateQuery) {
		if (!connected) {
			return -1;
		}
		
		// Error handling surrounding queries
		try {
			// Initialize a statement from the connection
			Statement st = connection.createStatement();
			
			// Execute the update
			int returnVal = st.executeUpdate(updateQuery);
			
			// Return the int value from executeUpdate
			return returnVal;
			
		} catch (SQLException e) {
			System.out.println("ERROR: SQL EXCEPTION IN SQL_JDBC.update()");
			System.out.println("UPDATE: " + updateQuery);
			System.out.println("Error: " + e);
			//System.out.println(e.getStackTrace());
			
			return -1;
		}
	}
	
	// Returns a DatabaseMetaData object for the connected DB
	public DatabaseMetaData get_DatabaseMetaData() {
		DatabaseMetaData md = null;
		
		// Try to get the database meta data
		try {
			md = connection.getMetaData();
		} catch (SQLException e) {
			md = null;
			System.out.println("ERROR: SQL EXCEPTION IN SQL_JDBC.get_DatabaseMetaData()");
			System.out.println("Error: " + e);
		}
		
		return md;
	}


	@Override
	public String toString() {
		if (connected) {
			return "SQL_JDBC\n" + "CONNECTED WITH USER: " + USER + "\nON URL: " + DB_URL;
		} else {
			return "SQL_JDBC\n" + "NOT CONNECTED TO DB\n";
		}
	}
	
	// Returns true if the JDBC is connected to the data base
	public boolean isConnected() {
		return connected;
	}
	
	
	public String getDB_URL() {
		return DB_URL;
	}

	public String getUSER() {
		return USER;
	}
	
}
