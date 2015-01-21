import java.util.Date;
import java.util.UUID;


public class Log {
	private String personName;
	private int personID;
	private String book;
	private int bookID;
	private Date issueDate;
	private Date returnDate;
	
	public Log(IssueBook book,Friend friend){
		this.book = book.getTitle();
		this.bookID = book.getID();
		this.personName = friend.getName();
		this.personID = friend.getID();
		
		long now = System.currentTimeMillis();
		this.issueDate = new Date(now);
	}
	
	public void setReturnDate(){
		long now = System.currentTimeMillis();
		this.returnDate = new Date(now);
	}
	
	public int getBookID(){
		return bookID;
	}
}
