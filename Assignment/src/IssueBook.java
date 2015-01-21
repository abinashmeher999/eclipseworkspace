import java.io.Serializable;

public class IssueBook extends Book implements Serializable{
	/**
	 * 
	 */
	public IssueBook(){
		issuable = true;
		setNumIssues(0);
	}
	
	public Log issue(Friend friend){
		if (this.isIssued==true){
			Log nullLog = new Log(null,null);//fix this
			return nullLog;
		}else{
			Log entry = new Log(this,friend);
			this.isIssued = true;
			this.issuedHistory.add(entry);
			this.setNumIssues(getNumIssues()+1);
			friend.issueHistory.add(entry);
			friend.booksIssued.add(this);
			return entry;
		}
	}
	
	public Boolean returnBack(Friend friend){
		if (this.isIssued == false){
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
}