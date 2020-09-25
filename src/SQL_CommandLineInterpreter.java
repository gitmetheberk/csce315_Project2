import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

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
			
		} else if (tokens[0].equals("jdb-show-all-tables")) {
			results = results.concat(show_all_tables());
			
		} else if (tokens[0].equals("jdb-show-all-columns")) {
			results = results.concat(show_all_columns());
			
		} else if (tokens[0].startsWith("jdb-productid-from-name")) {
			if (tokens.length < 2) {
				results = results.concat("ERROR: Name missing after jdb-productid-from-name\n");
				return results;
			} else {
				// Grab the full text from the original input and pass to the function
				String name = "";
				name = input.replace(tokens[0] + " ", "");

				results = results.concat(productid_from_name(name));
			}
			
		} else if (tokens[0].startsWith("jdb-productid-from-description")) {
			if (tokens.length < 2) {
				results = results.concat("ERROR: Description missing after jdb-productid-from-description\n");
				return results;
			} else {
				// Grab the full text from the original input and pass to the function
				String desc = "";
				desc = input.replace(tokens[0] + " ", "");

				results = results.concat(productid_from_description(desc));
			}
			
		} else if (tokens[0].startsWith("jdb-locationid-from-name")) {
			if (tokens.length < 2) {
				results = results.concat("ERROR: Name missing after jdb-locationid-from-name\n");
				return results;
			} else {
				// Grab the full text from the original input and pass to the function
				String name = "";
				name = input.replace(tokens[0] + " ", "");

				results = results.concat(locationid_from_name(name));
			}
		} else if (tokens[0].startsWith("jdb-stat")) {
			if (tokens.length < 3) {
				results = results.concat("ERROR: Syntax needs to follow:\njdb-stat <table|viewname> <column>\n");
				return results;
			} else {
				results = results.concat(stat(tokens[1],tokens[2]));
			}
			
			
		// Catch invalid "jdb-" type command
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
	
	// Utility function, takes a ResultSet in, returns a formatted string
	private String parse_ResultSet(ResultSet rs) {
		String results = "";
		try {
			// Check for null result set
			if (rs == null) {
				results = results.concat("WARNING: Your query produced an error\n");
				return results;
			} else {
				// Source: https://coderwall.com/p/609ppa/printing-the-result-of-resultset
				// Grab the meta data for the ResultSet to get the number of columns
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				
				// Print row headers
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) results = results.concat(",  ");
					results = results.concat(rsmd.getColumnName(i));
				}
				results = results.concat("\n");
				
				// Flag to avoid returning just column titles for empty ResultSet
				boolean data = false;
				
				// Append returned data
				while (rs.next()) {
					data = true;
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
				
				if (!data) {
					results = "WARNING: Your query produced zero results\n";
				}
			}
		} catch (SQLException e) {
			results = results.concat("ERROR: An error occured while parsing query results\n");
			results = results.concat(e + "\n");
			return results;
		}
		
		
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
		
		try {
			  DatabaseMetaData metaData = jdbc.get_DatabaseMetaData();
			  ResultSet tableList = metaData.getTables("adventureworks", null, null, new String[]{"TABLE"}); //first arg specifies database name, last arg indicates we want the entire list of tables
			  
			  String sql = "USE adventureworks;";
			  jdbc.query(sql);
			   
			  boolean hasPrimaryKey;
			  while(tableList.next()) {//to get each table name, move the result set cursor down and access the value for the "TABLE_NAME" key
				   
				  String tableName = tableList.getString("TABLE_NAME"); 
				  ResultSet keyList = metaData.getPrimaryKeys("adventureworks", null, tableName);

				  toReturn += "(" + tableName + ", ";

				  hasPrimaryKey = false;
				  while (keyList.next()) { //it needs to be a while loop because each row contains data about each primary key (there could possibly be multiple primary keys)
					  hasPrimaryKey = true;
					  toReturn += keyList.getString("COLUMN_NAME") + ", ";
				  }
				  if (!hasPrimaryKey) {
					  toReturn += "No Primary Key)\n"; //one of the tablles (purchaseorderheader) doesn't have a primary key???
				  }
				  else {
					  toReturn = toReturn.substring(0, toReturn.length() - 2); //get rid of last comma and space
					  toReturn += ")\n";
				  }
			   }
			   
			   //deallocate resources
			   tableList.close();
		   
		   }catch(SQLException se){
			   toReturn = toReturn.concat("ERROR: An error occured while processing jdb-show-all-primary-keys\n");
			   toReturn = toReturn.concat("Error: " + se + "\n");
			   return toReturn;
		   }
		
		   return toReturn;
	}
	
	private String find_column(String column){
		String toReturn = "";
		
		try {
			  DatabaseMetaData metaData = jdbc.get_DatabaseMetaData();
			  ResultSet tableList = metaData.getTables("adventureworks", null, null, new String[]{"TABLE"}); //first arg specifies database name, last arg indicates we want the entire list of tables
			  
			  String sql = "USE adventureworks;";
			  ResultSet rs = jdbc.query(sql);
		      
			  // Flag to provide the user with a message if no tables found
			  boolean anyFound = false;
			  
			  while(tableList.next()) {//to get each table name, move the result set cursor down and access the value for the "TABLE_NAME" key
				  String tableName = tableList.getString("TABLE_NAME"); 
				  sql = "SHOW COLUMNS FROM " + tableName + " LIKE '" + column + "'";
				  rs = jdbc.query(sql);

				  if(rs.next()) { //if rs.next() returns false, then there are no columns for the table that match the column passed into the function
					  anyFound = true;
					  toReturn += tableName + "\n"; //storing in vector for now , just in case we need to do something with the tables that contain the column later
				  }
		    	  
			   }
			   
			   //deallocate resources used
			   tableList.close();
			   rs.close();
			   
			   // Provide the user with a message if no data found
			   if (!anyFound) {
				   toReturn = toReturn.concat("WARNING: No tables found with column " + column + "\n");
			   }
					
	           
	   }catch(SQLException se){
		   toReturn = toReturn.concat("ERROR: An error occured while processing jdb-find-column for column" + column + "\n");
		   toReturn = toReturn.concat("Error: " + se + "\n");
		   return toReturn;
	   }
	
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
		if (!check_for_table(table)){
			toReturn = toReturn.concat("Error: Table \"" + table + "\" not found in database.");
			return toReturn;
		}
		if (!check_for_column(table,column)){
			toReturn = toReturn.concat("Error: Column \"" + column + "\" not found in Table \"" + table + "\".");
			return toReturn;
		}
		
		try {
			  DatabaseMetaData metaData = jdbc.get_DatabaseMetaData();
			  ResultSet tableList = metaData.getTables("adventureworks", null, null, new String[]{"TABLE"}); //first arg specifies database name, last arg indicates we want the entire list of tables
			  
			  String sql = "USE adventureworks;";
			  ResultSet rs = jdbc.query(sql);
		      sql = "SELECT " + column + " FROM " + table + ";";
		      rs = jdbc.query(sql);
		      //query gets the column of data we want
		      ArrayList<Double> results = new ArrayList<Double>(0);//using list because we do not know how many data point we have
			   while(rs.next()){
				   results.add(rs.getDouble(column));
			   }
			   //do not need this because the data base throws an error when it cannot find table or column 
			   if (results.size() == 0){toReturn = toReturn.concat("Column " + column + "not found in table " + table); return toReturn;}
			   Double sum=0.0;
			   for (Double num : results) {
			        sum += num;
			   }
			   double mean = sum.doubleValue() / results.size();
			   Collections.sort(results);
			   double median;
			   if (results.size() % 2 == 0)
			       median = ((double)results.get(results.size()/2) + (double)results.get(results.size()/2-1))/2;
			   else
			       median = (double) results.get(results.size()/2);
			   double max = Collections.max(results);
			   double min = Collections.min(results);
			   toReturn = toReturn.concat("Stats form " + table + " from " + column + ":\n");
			   toReturn = toReturn.concat("MAX: " + max + "\n");
			   toReturn = toReturn.concat("MIN: " + min + "\n");
			   toReturn = toReturn.concat("MEAN: " + mean + "\n");
			   toReturn = toReturn.concat("MEDIAN: " + median + "\n");
			   
			   //creating histogram with 5 buckets
			   int[] hist = {0,0,0,0,0};
			   int gap = (int)((max - min)/5.0);//how wide each bucket is
			   //starting number of each bucket
			   int b1 = gap+(int)(min);
			   int b2 = gap+b1;
			   int b3 = gap+b2;
			   int b4 = gap+b3;
			   //filling buckets
			   for (Double num : results){
				   if(num < b1)hist[0]++;
				   else if(num < b2)hist[1]++;
				   else if(num < b3)hist[2]++;
				   else if(num < b4)hist[3]++;
				   else hist[4]++;
			   }
			  int bucketmax = 0;//finding the bucket max to scale x-axis
			  for (int num : hist){
				  if (num > bucketmax)bucketmax = num;
			  }
			  bucketmax = bucketmax + (bucketmax/5);//add a little space on the end
			  int bucketspace = bucketmax/5;
			  int starValue = bucketspace/2-1;//value of each star printed
			  toReturn = toReturn + String.format("%7s","");//space given to write y-axis
			  toReturn = toReturn + String.format("0_%d_%d_%d_%d_%d\n",bucketspace,bucketspace*2,bucketspace*3,bucketspace*4,bucketmax);
			  //printing each bucket
			  toReturn = toReturn + String.format("%7s",(int)min + "-" + b1 + "|");
			  for(int i=0; i<hist[0]/starValue+1; i++){toReturn = toReturn + String.format("*");}
			  toReturn = toReturn + String.format("%n%7s",b1 + "-" + b2 + "|");
			  for(int i=0; i<hist[1]/starValue+1; i++){toReturn = toReturn + String.format("*");}
			  toReturn = toReturn + String.format("%n%7s",b2 + "-" + b3 + "|");
			  for(int i=0; i<hist[2]/starValue+1; i++){toReturn = toReturn + String.format("*");}
			  toReturn = toReturn + String.format("%n%7s",b3 + "-" + b4 + "|");
			  for(int i=0; i<hist[3]/starValue+1; i++){toReturn = toReturn + String.format("*");}
			  toReturn = toReturn + String.format("%n%7s",b4 + "-" + (int)max + "|");
			  for(int i=0; i<hist[4]/starValue+1; i++){toReturn = toReturn + String.format("*");}
			  toReturn = toReturn + String.format("\n");
			  
			   //deallocate resources used
			   tableList.close();
			   rs.close();
			   	           
	   }catch(SQLException se){
		   toReturn = toReturn.concat("ERROR: An error occured while processing jdb-stat for table " + table + "and  for column" + column + "\n");
		   toReturn = toReturn.concat("Error: " + se + "\n");
		   return toReturn;
	   }
		
		return toReturn;
	}
	
	private String show_all_tables() {
		String toReturn = "";
		
		try {
			DatabaseMetaData metaData = jdbc.get_DatabaseMetaData();
			ResultSet tableList = metaData.getTables("adventureworks", null, null, new String[]{"TABLE"}); //first arg specifies database name, last arg indicates we want the entire list of tables
			
			while(tableList.next()) {//to get each table name, move the result set cursor down and access the value for the "TABLE_NAME" key
				   
				 String tableName = tableList.getString("TABLE_NAME");
				 toReturn += tableName + "\n";
				 
			}
		}catch(SQLException se){
			   toReturn = toReturn.concat("ERROR: An error occured while processing jdb-show-all-tables\n");
			   toReturn = toReturn.concat("Error: " + se + "\n");
			   return toReturn;
		}
		
		return toReturn;
	}
	
	private String show_all_columns() {
		String toReturn = "";
		
		try {
			DatabaseMetaData metaData = jdbc.get_DatabaseMetaData();
			ResultSet tableList = metaData.getTables("adventureworks", null, null, new String[]{"TABLE"}); //first arg specifies database name, last arg indicates we want the entire list of tables
			
			while(tableList.next()) {//to get each table name, move the result set cursor down and access the value for the "TABLE_NAME" key
				   
				 String tableName = tableList.getString("TABLE_NAME");
				 toReturn += "Columns for the " + tableName + " table:\n";
				 ResultSet columns = metaData.getColumns("adventureworks", null, tableName, null);
				 while(columns.next()) {
					 toReturn += columns.getString("COLUMN_NAME") + "\n";
				 }
				 toReturn += "\n"; //space out columns for different tables 
			}
		}catch(SQLException se){
			   toReturn = toReturn.concat("ERROR: An error occured while processing jdb-show-all-columns\n");
			   toReturn = toReturn.concat("Error: " + se + "\n");
			   return toReturn;
		}
		
		return toReturn;
	}
	
	private String productid_from_name(String name){
		String toReturn = "";
		
		// Define the query
		String query = 
				  "SELECT ProductID, Name "
				+ "FROM product "
				+ "WHERE NAME LIKE \"%" + name + "%\";";
		
		// Make the query, parse it, and return the results
		toReturn = toReturn.concat(parse_ResultSet(jdbc.query(query)));
		return toReturn;
	}
	
	private String productid_from_description(String description){
		String toReturn = "";
		
		// Define the query
		String query = 
				  "SELECT product.ProductID, product.Name, productdescription.Description" + 
				  " FROM productdescription" + 
				  "	INNER JOIN productmodelproductdescriptionculture" + 
				  "	ON productmodelproductdescriptionculture.ProductDescriptionID=productdescription.ProductDescriptionID" + 
				  "	INNER JOIN product" + 
				  "	ON product.ProductModelID=productmodelproductdescriptionculture.ProductModelID" + 
				  " WHERE productdescription.Description LIKE \"%" + description + "%\";";
		
		// Make the query, parse it, and return the results
		toReturn = toReturn.concat(parse_ResultSet(jdbc.query(query)));
		return toReturn;
	}
	
	
	private String locationid_from_name(String name){
		String toReturn = "";
		
		// Define the query
		String query = 
				  " SELECT LocationID, Name" + 
				  " FROM location" + 
				  " WHERE NAME LIKE \"%" + name + "%\";";
		
		// Make the query, parse it, and return the results
		toReturn = toReturn.concat(parse_ResultSet(jdbc.query(query)));
		return toReturn;
	}
	
	private boolean check_for_table(String table){
		//A simple function to check if the table is in the database
		try {
			  DatabaseMetaData metaData = jdbc.get_DatabaseMetaData();
			  ResultSet tableList = metaData.getTables("adventureworks", null, null, new String[]{"TABLE"}); //first arg specifies database name, last arg indicates we want the entire list of tables
			  
			  while(tableList.next()){//check if the given table name was in the list of tables
				  if (tableList.getString("TABLE_NAME").equals(table)){
					  return true;
				  }
			  }
			  
			   //deallocate resources used
			   tableList.close();
			   
			  //if the table is not in the database
			  return false;      
	   }catch(SQLException se){
		   //not sure what to put here
		   return false;
	   }
	}
	
	private boolean check_for_column(String table, String column){
		//A simple function to check the column is in the table
		try {
			  DatabaseMetaData metaData = jdbc.get_DatabaseMetaData();
			  ResultSet tableList = metaData.getTables("adventureworks", null, null, new String[]{"TABLE"}); //first arg specifies database name, last arg indicates we want the entire list of tables
			  
			  String sql = "USE adventureworks;";
			  ResultSet rs = jdbc.query(sql);
		      sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + table + "';";
		      rs = jdbc.query(sql);
			   while(rs.next()){
				   if(rs.getString("COLUMN_NAME").equals(column)){
					   return true;
				   }
			   }
			  
			   //deallocate resources used
			   tableList.close();
			   rs.close();
			   
			  //if the column is not in the database
			  return false;      
	   }catch(SQLException se){
		   //not sure what to put here
		   return false;
	   }
	}
}
