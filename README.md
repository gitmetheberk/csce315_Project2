# csce315_Project2
Project requirements: https://people.engr.tamu.edu/choe/choe/courses/20fall/315/proj2.html

# Setup
1. Two .jar files are included which need to be in the classpath to compile the program. rs2xml.jar is in the root directory and mysql-connector-java-8.0.21.jar is in "Connector J 8.0" 
2. If you are running Windows 10, skip this step. If you are not running Windows 10, the provided mysql-connector .jar file may not work and you will have to download the .jar file for your operating system from https://dev.mysql.com/downloads/connector/j/. This new file needs to be on the classpath in place of the original mysql-connector .jar file
3. Open src\SQL_JDBC.java and find the String variable DB_URL near the top of the class, this variable needs to be updated to point to your database installation
4. Open src\SQL_JDBC.java and find the two String variables USER and PASS near the top of the class, these need to be updated to the username and password of an account on your database which should have read-only permissions and the ability to create views.
5. After the above steps, the program should compile and run successfully

# File information
* All files in the Connector J directory are part of the MySQL Connector. This is an external library which this program depends on.
* rs2xml.jar is an external library which this program depends on. 
* src\SQL_JDBC.java manages the interface between the database and the Java application. To connect to your own DB, update the DB_URL, USER, and PASS variables for your DB
* src\SQL_CommandLine.java contains the user facing commandline interface, it is very simple and is primarily a while loop to scan for user input
* src\SQL_CommandLineInterpreter.java contains all input processing functions for command line input. It also contains all functions which support custom commands
* src\SQL_GUI.java contains the main class for the graphical user interface, initializes all sub panels.
* All other .java files support the primary SQL_* classes by performing a function defined by their name.

# Libraries
* https://hacksmile.com/rs2xml-jar-free-download/
* https://dev.mysql.com/downloads/connector/j/

# Sources:
* https://www.w3schools.com/java/java_user_input.asp
* https://coderwall.com/p/609ppa/printing-the-result-of-resultset
* https://www.w3docs.com/snippets/java/how-to-split-a-string-in-java.html
* https://github.com/BranislavLazic/SwingTutorials/blob/master/src/main/java/CardLayoutTutorial.java
* https://stackoverflow.com/questions/4577792/how-to-clear-jtable
* https://stackoverflow.com/questions/192078/how-do-i-get-the-size-of-a-java-sql-resultset
