package application;

public class Report {
	
	private String initials;
	private String comments;
	
	public Report(String initials, String comments){
		this.initials = initials;
		this.comments = comments;
	}
	
	public Report(){
		this.initials = "";
		this.comments = "";
	}
	
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
