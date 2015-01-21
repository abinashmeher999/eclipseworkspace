import java.io.Serializable;
import java.util.UUID;
import java.util.ArrayList;

public class Plib implements Serializable {
	/**
	 * 
	 */
	private double totalCostOfBooks;
	private static int bookIDCounter = 0;
	private static int friendIDCounter = 0;
	ArrayList<IssueBook> books = new ArrayList<IssueBook>();
	ArrayList<Friend> currentUsers = new ArrayList<Friend>();
	
	public static void main(String args[]){
		
	}
	
}
