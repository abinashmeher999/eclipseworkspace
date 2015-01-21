import java.io.Serializable;

public class IssueBook extends Book implements Serializable{
	/**
	 * 
	 */
	//public boolean isIssued;
	public IssueBook(){
		issuable = true;
		isIssued = false;
		setNumIssues(0);
	}
	
	public boolean issue(Friend friend){
		if (this.isIssued()==true){
			System.out.println("This book has been issued!");
			return false;
		}else{
			Log entry = new Log(this,friend);
			this.isIssued = true;
			this.issuedHistory.add(entry);
			this.setNumIssues(getNumIssues()+1);
			friend.issueHistory.add(entry);
			friend.booksIssued.add(this);
			System.out.println("Issue successful.\n");
			return true;
		}
	}
	
	public Boolean returnBack(Friend friend){
		if (this.isIssued == false){
			System.out.println("Unissued book cannot be returned.\n");
			return false;
		}else{
			this.issuedHistory.get(this.issuedHistory.size()-1).setReturnDate();
			friend.booksIssued.remove(this);
			int n = friend.issueHistory.size();
			for (int i = 0 ; i < n ; i++ ){
				if (friend.issueHistory.get(i).getBookID()==this.getID()){
					friend.issueHistory.get(i).setReturnDate();
					break;
				}
			}
			this.isIssued = false;
			System.out.println("Successfully returned.\n");
			return true;
		}
	}
	
	public RefBook makeRefBook(){
		RefBook newRef = new RefBook();
		newRef.setID(this.getID());
		newRef.setTitle(this.getTitle());
		newRef.setYearOfPublication(this.getYearOfPublication());
		newRef.setDateOfPurchase(this.getDateOfPurchase());
		newRef.setPrice(this.getPrice());
		newRef.setPublisher(this.getPublisher());
		newRef.setNumIssues(this.getNumIssues());
		return newRef;
	}
	
	public void issueQuery(){
		if (this.isIssued()){
			System.out.println("Book : "+this.getTitle()+" ID "+this.getID());
			System.out.println("Issued to :"+this.getIssueHistory().get(this.getIssueHistory().size()-1).getPerson());
			System.out.printf("%1$s %2$tB %2$td, %2$tY", 
                    "Issued on : ", this.getIssueHistory().get(this.getIssueHistory().size()-1).getIssueDate());
			System.out.println("");
		} else {
			System.out.println("Not issued to anyone");
		}
	}
}