import java.sql.*;

// Utility class which interprets command line input and makes queries to SQL_JDBC
public class SQL_CommandLineInterpreter {
	private SQL_JDBC jdbc;
	
	
	public SQL_CommandLineInterpreter() {
		jdbc = new SQL_JDBC();
		if (!jdbc.isConnected()) {
			System.out.println("ERROR: Could not establish database connection");
			// TODO Probably want to do something here
		}
	}


	public String processInput(String input) {
		String results = "";
		
		// Check for empty input
		if (input.isEmpty()) {
			//System.out.println("WARNING: User input is empty");
			return results;
		} 
		
		// Tokenize the input
		String[] tokens = input.split(" ");
		
		// if/else block to check for custom commands, else pass raw query
		if (tokens[0].equals("jdb-show-related-tables")) {
			if (tokens.length < 2) {
				results = results.concat("ERROR: No table name provided after jdb-show-related-tables\n");
				return results;
			} else {
				results = results.concat(show_related_tables(tokens[1]));
			}
			
		} else if (tokens[0].equals("jdb-show-all-primary-keys")) {
			results = results.concat(show_all_primary_keys());
			
		} else if (tokens[0].equals("jdb-find-column")) {
			if (tokens.length < 2) {
				results = results.concat("ERROR: No column name provided after jdb-find-column\n");
				return results;
			} else {
				results = results.concat(find_column(tokens[1]));
			}
			
		} else if (tokens[0].equals("jdb-search-path")) {
			if (tokens.length < 3) {
				results = results.concat("ERROR: Less than 2 table names were provided after jdb-search-path\n");
				return results;
			} else {
				results = results.concat(search_path(tokens[1], tokens[2]));
			}
			
		} else if (tokens[0].equals("jdb-search-and-join")) {
			if (tokens.length < 3) {
				results = results.concat("ERROR: Less than 2 table names were provided after jdb-search-and-join\n");
				return results;
			} else {
				results = results.concat(search_path(tokens[1], tokens[2]));
			}
			
		} else if (tokens[0].equals("jdb-get-view")) {
			if (tokens.length < 3) {
				results = results.concat("ERROR: View name or SQL query not provided after jdb-get-view\n");
				return results;
			} else {
				// Need to process the SQL query from the string before calling the function
				String query = "";
				query = input.replace(tokens[0] + " " + tokens[1] + " ", "");

				results = results.concat(search_path(tokens[1], query));
			}
			
		} else if (tokens[0].equals("jdb-get-view")) {
			if (tokens.length < 3) {
				results = results.concat("ERROR: View name or SQL query not provided after jdb-get-view\n");
				return results;
			} else {
				// Need to process the SQL query from the string before calling the function
				String query = "";
				query = input.replace(tokens[0] + " " + tokens[1] + " ", "");

				results = results.concat(search_path(tokens[1], query));
			}
			
		// Catch invalid jdb- type command
		} else if (tokens[0].startsWith("jdb-")) {
			results = results.concat("ERROR: Invalid command\n");
			return results;
			
		} else {
			ResultSet rs = jdbc.query(input);
			results = results.concat(parse_ResultSet(rs));
		}
		
	
		// Return the string
		results = results.concat("\n");
		return results;
	}
	
	private String show_related_tables(String table){
		String toReturn = "";
		
		// TODO Code from Zhengnan
		// All results to be shown to the user should be appended to toReturn in the following format
		// toReturn = toReturn.concat(SomeStringToRreturn);
		
		
		return toReturn;
	}
	
	private String show_all_primary_keys(){
		String toReturn = "";
		
		// TODO Code from Holden
		// All results to be shown to the user should be appended to toReturn in the following format
		// toReturn = toReturn.concat(SomeStringToRreturn);
		
		
		return toReturn;
	}
	
	private String find_column(String column){
		String toReturn = "";
		
		// TODO Code from Holden
		// All results to be shown to the user should be appended to toReturn in the following format
		// toReturn = toReturn.concat(SomeStringToRreturn);
		
		
		return toReturn;
	}
	
	private String search_path(String t1, String t2){
		String toReturn = "";
		
		// TODO Code from Matthew
		// All results to be shown to the user should be appended to toReturn in the following format
		// toReturn = toReturn.concat(SomeStringToRreturn);
		
		
		return toReturn;
	}
	
	private String search_and_join(String t1, String t2){
		String toReturn = "";
		
		// TODO Code from Matthew
		// All results to be shown to the user should be appended to toReturn in the following format
		// toReturn = toReturn.concat(SomeStringToRreturn);
		
		
		return toReturn;
	}
	
	private String get_view(String view_name, String query){
		String toReturn = "";
		
		// TODO Code from Nima
		// All results to be shown to the user should be appended to toReturn in the following format
		// toReturn = toReturn.concat(SomeStringToRreturn);
		
		
		return toReturn;
	}
	
	private String stat(String table, String column){
		String toReturn = "";
		
		// TODO Code from Alex
		// All results to be shown to the user should be appended to toReturn in the following format
		// toReturn = toReturn.concat(SomeStringToRreturn);
		
		
		return toReturn;
	}
	
	// Utility function, takes a ResultSet in, returns a formatted string
	private String parse_ResultSet(ResultSet rs) {
		String results = "";
		try {
			// Check for null result set
			if (rs == null) {
				results = results.concat("WARNING: Your query produced zero results\n");
				return results;
			} else {
				// Source: https://coderwall.com/p/609ppa/printing-the-result-of-resultset
				
				// Grab the meta data for the result set to get the number of columns
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				
				// Print row headers
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) results = results.concat(",  ");
					results = results.concat(rsmd.getColumnName(i));
				}
				results = results.concat("\n");
				
				// Print returned data
				while (rs.next()) {
					for (int i = 1; i <= columnsNumber; i++) {
						if (i > 1) results = results.concat(",  ");
						String columnValue = rs.getString(i);
						
						// This check is needed otherwise concat throws a nullptr exception
						if (columnValue == null) {
							continue;
						}
						results = results.concat(columnValue);
					}
					results = results.concat("\n");
				}	
			}
		} catch (SQLException e) {
			results = results.concat("ERROR: An error occured while parsing query results\n");
			results = results.concat(e + "\n");
			return results;
		}
		
		
		return results;
	}
}
