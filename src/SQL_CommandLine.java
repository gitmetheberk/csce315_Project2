import java.util.Scanner;
// Command line, provides a command line interface for the command line interpreter
public class SQL_CommandLine {

	public static void main(String[] args) {
		// Construct the interpreter object
		SQL_CommandLineInterpreter CLI = new SQL_CommandLineInterpreter();
		
		// Primary command loop
		Scanner scanner = new Scanner(System.in);
		String userIn;
		
		// User-facing text
		System.out.println("Note: jdb> is the prompt. \r\n" + 
						   "Note: all custom commands are prefixed by \"jdb-\".");
		System.out.println("Note: To exit, enter \"EXIT CLI\"");
		System.out.println("");
		
		while (true) {
			// Grab user input and make sure it's not empty
			System.out.print("jdb> ");
			try {
				userIn = scanner.nextLine();
			} catch (Exception e) {
				continue;
			}
			
			if (userIn.equalsIgnoreCase("EXIT CLI")) {
				break;
			}
	
			// Provide warning for comments in SQL query, usually resulting from a copy-paste of a query
			if (userIn.contains("-- ")) {
				System.out.println("WARNING: Comments in SQL queries can have unexpected consequences");
			}
			
			// At this point user input has been scanned, send to the processor
			String result = CLI.processInput(userIn);
			
			// Print the returned string
			System.out.println(result);
			
			
		}
		
		scanner.close();
		System.out.println("\n\nThank you for using our DB client. Have a nice day!");
	}
}
