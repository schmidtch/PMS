package model;

public class Document {

	private String title;
	private String comment;
	private String type;
	private int caseno;
	private int docid;
	public static String HN = "Honorarnote";
	
	public Document(){}
	
	public Document(String type, int caseno){
		this.caseno=caseno;
		this.type=type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCaseno() {
		return caseno;
	}

	public void setCaseno(int caseno) {
		this.caseno = caseno;
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}
	
}
