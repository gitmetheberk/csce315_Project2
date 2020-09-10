// From https://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm

import java.sql.*;

public class test1_JDBC {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://192.168.1.77:3306";
	
	// Not a fan of just having the password in the java file, just saying
	static final String USER = "user2";
	static final String PASS = "c8kPA8eHaXsBNEPE";
	
	public static void main(String[] args) {
		   Connection connection = null;
		   Statement statement = null;
		   try{
		      //STEP 2: Register JDBC driver
			  // When the method getConnection is called, the DriverManager will attempt to locate a suitable driver 
			  // from amongst those loaded at initialization and those loaded explicitly using the same classloader 
			  // as the current applet or application. 
		      //Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      connection = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      statement = connection.createStatement();
		      String sql;
		      sql = "SELECT * FROM adventureworks.employee";
		      ResultSet rs = statement.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){
		         //Retrieve by column name
		         int employeeID  = rs.getInt("EmployeeID");
		         
		         // Grab contact ID to get employee's contact
		         int contactID = rs.getInt("ContactID");
		         
		         // Get user's contact information
		         Statement contactStatement = connection.createStatement();
		         ResultSet contacts = contactStatement.executeQuery("SELECT * FROM adventureworks.contact WHERE ContactID = " + contactID);
		         if (contacts.next()) {
		         
		         System.out.print("EmployeeID:" + employeeID + " ");
		         System.out.print("contactID:" + contactID + " ");
		         System.out.println("Name:" + contacts.getString("FirstName") + " " + contacts.getString("LastName"));
		      
		         } else {
		        	 System.out.println("No contact found");
		         }
		         contacts.close();
		         contactStatement.close();
		      }
		      
		      //STEP 6: Clean-up environment
		      rs.close();
		      statement.close();
		      connection.close();
		      
		      
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(statement!=null)
		            statement.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");
		}//end main

}