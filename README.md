# csce315_Project2
https://people.engr.tamu.edu/choe/choe/courses/20fall/315/proj2.html

# File information
* All files in the Connector J directory are part of the MySQL Connector. This is an external library which our program depends on.
* src\SQL_CommandLine contains the user facing commandline interface, it is very simple and is primarily a while loop to scan for user input
* src\SQL_CommandLineInterpreter contains all input processing functions for command line input. It also contains all functions which support custom commands
* src\SQL_JDBC manages the interface between the database and the Java application. To connect to your own DB, update the DB_URL, USER, and PASS variables for your DB
* src\TableNode is a simple node class used in SQL_CommandLineInterpret in both search_path and search_and_join
* src\ other files are not implemented yet.

# Sources:
* https://www.w3schools.com/java/java_user_input.asp
* https://coderwall.com/p/609ppa/printing-the-result-of-resultset
* https://www.w3docs.com/snippets/java/how-to-split-a-string-in-java.html
