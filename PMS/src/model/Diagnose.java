package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import utilities.StringReplacer;

public class Diagnose {

	private int diag_id;
	private String title;
	private String text;
	private String type;
	
	public Diagnose(){}
	
	public Diagnose(String title, String text, String type){
		this.title=title;
		this.text=text;
		this.type=type;
	}
	
	public Diagnose(ResultSet rs) throws SQLException{
		this.title=rs.getString("title");
		this.text=rs.getString("text");
		this.type=rs.getString("type");
		this.diag_id=Integer.parseInt(rs.getString("diag_id"));
	}
	
	public int getDiag_id() {
		return diag_id;
	}
	
	public void setDiag_id(int diag_id) {
		this.diag_id = diag_id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getLongTextType(){
		String ltt = "";
		if(type.equals("sm")){
			ltt = "Schulmedizinisch";
		} else if (type.equals("ch")) {
			ltt = "Chinesisch";
		}
		return ltt;
	}
	
	public String toHTMLBlock(){
		
		StringReplacer sr = new StringReplacer();
		Properties p = new Properties();
		p.put("$title", title);
		p.put("$type", getLongTextType());
		p.put("$text", text.replaceAll("\n", "<br />"));
		sr.setReplacements(p);
		return sr.replaceInFile(this.getClass().getResource("../../../html/diagnose.html").getPath());
		
	}
	
}
