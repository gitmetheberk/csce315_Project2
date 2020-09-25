import java.util.ArrayList;

// Utility class used in search_path()and search_and_join() in SQL_CommandLineInterface
public class TableNode {
	private String name;
	private ArrayList<String> columns;
	private ArrayList<String> path_to;
	
	// visited may not be used, if a Node exists, it's been visited
	// private boolean visited;
	
	// Constructor, name and columns required
	public TableNode(String name, ArrayList<String> columns, ArrayList<String> path_to) {
		this.name = name;
		this.path_to = path_to;
		this.columns = columns;
	}
	
	public TableNode(String name, ArrayList<String> path_to) {
		this.name = name;
		this.path_to = path_to;
		this.columns = new ArrayList<String>();
	}
	
	

//	public boolean isVisited() {
//		return visited;
//	}
//
//	public void setVisited(boolean visited) {
//		this.visited = visited;
//	}

	public ArrayList<String> getPath_to() {
		return path_to;
	}

	public void setPath_to(ArrayList<String> path_to) {
		this.path_to = path_to;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<String> columns) {
		this.columns = columns;
	}
	
	public boolean hasColumns() {
		return !columns.isEmpty();
	}
	
	
}
