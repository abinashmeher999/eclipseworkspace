import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;


public class Friend implements Serializable{
	private int ID;
	private String name;
	private String address;
	private String lnumber;
	private String mnumber;
	public ArrayList<Log> issueHistory;
	public ArrayList<IssueBook> booksIssued;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLnumber() {
		return lnumber;
	}
	public void setLnumber(String lnumber) {
		this.lnumber = lnumber;
	}
	
	public String getMnumber() {
		return mnumber;
	}
	public void setMnumber(String mnumber) {
		this.mnumber = mnumber;
	}
	
	public Boolean issueBook(IssueBook ibook){
		
		return null;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
}
