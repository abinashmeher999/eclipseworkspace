import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;

public abstract class Book implements Serializable{
	/**
	 * 
	 */
	private int ID;
	private String title;
	private String yearOfPublication;
	private Date dateOfPurchase;
	private double price;
	private String publisher;
	protected Boolean issuable;
	protected Boolean isIssued;
	private long numberOfTimesIssued;
	protected ArrayList<Log> issuedHistory = new ArrayList<Log>();
	
	public Boolean isIssuable(){
		return issuable;
	}
	
	public boolean isIssued(){
		return isIssued;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(String yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public long getNumIssues() {
		return numberOfTimesIssued;
	}

	protected void setNumIssues(long numberOfTimesIssued) {
		this.numberOfTimesIssued = numberOfTimesIssued;
	}
	
	public ArrayList<Log> getIssueHistory(){
		return issuedHistory;
	}
	
	public void traceIssueHistory(){
		for (int i = 0 ; i < issuedHistory.size() ; i++){
			System.out.println("Issued to "+issuedHistory.get(i).getPerson());
			System.out.printf("%1$s %2$tB %2$td, %2$tY", 
                    "Issued on : ", issuedHistory.get(i).getIssueDate());
			System.out.println();
			if (!isIssued()==true || i != issuedHistory.size()-1){
			System.out.printf("%1$s %2$tB %2$td, %2$tY", 
                    "Issued on : ", issuedHistory.get(i).getReturnDate());
			}
			System.out.println();
		}
	}
}


