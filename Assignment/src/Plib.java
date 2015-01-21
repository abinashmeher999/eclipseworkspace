import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Plib implements Serializable {
	/**
	 * 
	 */
	private static int bookIDCounter = 0;
	private static int friendIDCounter = 0;
	private static double totalCostOfBooks;
	static ArrayList<Book> books = new ArrayList<Book>();;
	static ArrayList<Friend> currentUsers = new ArrayList<Friend>();
	static String fileName = "Data.dat";
	
	public static void readData(String fileName){
		try (FileInputStream fileIn = new FileInputStream(fileName); ObjectInputStream objectInput = new ObjectInputStream(fileIn)) {
			bookIDCounter = objectInput.readInt();
			friendIDCounter = objectInput.readInt();
			totalCostOfBooks = objectInput.readDouble();
			books = (ArrayList<Book>) objectInput.readObject();
			currentUsers = (ArrayList<Friend>) objectInput.readObject();
			
		} catch (IOException | ClassNotFoundException ex) {
			
		}
	}
	
	public static void writeAndExit(String fileName){
		try (FileOutputStream fileOut = new FileOutputStream(fileName); ObjectOutputStream objectOutput = new ObjectOutputStream(fileOut);) {
			objectOutput.writeInt(bookIDCounter);
			objectOutput.writeInt(friendIDCounter);
			objectOutput.writeDouble(totalCostOfBooks);
			objectOutput.writeObject(books);
			objectOutput.writeObject(currentUsers);
			System.out.println("Succesfully exited!");
		} catch (IOException ex){
			System.out.println("Couldn't be written!");
		}
	}
	
	public static void createBook(){
		IssueBook newBook = new IssueBook();
		Scanner input = new Scanner(System.in);
		
		newBook.setID(++bookIDCounter);
		System.out.println("Please note, the ID is "+newBook.getID());
		
		System.out.println("Enter the Title :");
		newBook.setTitle(input.nextLine());
		
		System.out.print("Enter the Year of Publication\t");
		newBook.setYearOfPublication(input.nextLine());
		
		String expectedPattern = "dd/MM/yyyy";
		System.out.println("Enter date of purchase in "+expectedPattern+" format :");
	    SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
	    try {
	    	Date date = formatter.parse(input.nextLine());
	    	newBook.setDateOfPurchase(date);
	    } catch (ParseException e) {
	        System.out.println("Error in date format :");
	        return;
	    }
	    
	    System.out.println("Enter the price :");
	    newBook.setPrice(input.nextDouble());
	    input.nextLine();
	    
	    System.out.println("Enter the Publisher :");
	    newBook.setPublisher(input.nextLine());
	    
		books.add(newBook);
		totalCostOfBooks += newBook.getPrice();
		return;
	}
	
	public static boolean deleteBook(int bookID){
		int i;
		for (i = 0 ; i < books.size() ; i++){
			if (books.get(i).getID()==bookID){
				Book tempBook = books.remove(i);
				totalCostOfBooks -= tempBook.getPrice();
				System.out.println("Book ID "+bookID+" has been deleted!");
				return true;
			}
		}
		System.out.println("Book not found!");
		return false;
	}
	
	public static Boolean makeUnissuable(int bookID){
		int i;
		for (i = 0 ; i < books.size() ; i++){
			if (books.get(i).getID()==bookID){
				IssueBook tempIssueBook = new IssueBook();
				tempIssueBook = (IssueBook) books.remove(i);
				books.add(tempIssueBook.makeRefBook());
				System.out.println("Book ID "+bookID+" is now unissuable.");
				return true;
			} 
		}
		if (i == books.size()){
			System.out.println("Book not found!");
		}
		
		return false;
	}
	
	public static boolean search(String keyword){
		int i = 0;
		int count = 0;
		boolean found = false;
		for (i = 0 ; i < books.size() ; i++){
			if (books.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())){
				System.out.println("\t"+(++count)+". "+books.get(i).getTitle()+" ID "+books.get(i).getID());
				found = true;
			}
		}
		
		return found;
	}
	
	public static void displayBooks(){
		int n = books.size();
		int count = 0;
			
		System.out.println("Issuable Books :");
		for (int i = 0; i < n ; i++){
			if (books.get(i).isIssuable()){
				System.out.println("\t"+(++count)+". "+books.get(i).getTitle()+" ID "+books.get(i).getID());
			}
		}
		if (count == 0){
			System.out.println("\tNone");
		}
		count = 0;
		
		System.out.println("Reference Books :");
		for (int j = 0; j < n; j++){
			if (!books.get(j).isIssuable()){
				System.out.println("\t"+(++count)+". "+books.get(j).getTitle()+" ID "+books.get(j).getID());
			} 
		}
		if (count == 0){
			System.out.println("\tNone");
		}
		count = 0;
	}
	
	public static double displayTotalCost(){
		System.out.println("The total cost of books in the library is Rs."+totalCostOfBooks+".");
		return totalCostOfBooks;
	}
	
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		readData(fileName);
		int option = 0;
		while (option!=-1){
			System.out.println("Create Book: 1");
			System.out.println("Delete Book: 2");
			System.out.println("Display Books: 3");
			System.out.println("Make Unissuable: 4");
			System.out.println("Search for a Book: 5");
			System.out.println("View total cost: 6");
			System.out.println("Exit library: 7");
			option = input.nextInt();
			input.nextLine();
			switch (option) {
			case 1:
				createBook();
				option = 0;
				break;
			case 2:
				System.out.println("Enter the ID (-1 to cancel)");
				int k = input.nextInt();
				input.nextLine();
				if (k==-1){
					System.out.println("Delete cancelled!");
				} else {
					deleteBook(k);
				}
				option = 0;
				break;
			case 3:
				displayBooks();
				option = 0;
				break;
			case 4:
				System.out.println("Enter the ID (-1 to cancel)");
				int j = input.nextInt();
				input.nextLine();
				if (j==-1){
					System.out.println("Making Referrence cancelled!");
				} else {
					makeUnissuable(j);
				}
				option = 0;
				break;
			case 5:
				System.out.println("Enter the keyword :");
				String key = input.nextLine();
				search(key);
				option = 0;
				break;
			case 6:
				displayTotalCost();
				option = 0;
				break;
			case 7:
				option = -1;
				break;

			default:
				System.out.println("Please Enter a VALID INPUT!");
				option = 0;
				break;
			}
		}
		
		writeAndExit(fileName);
		
	}
	
}
