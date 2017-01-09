package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Patient {

	private String name;
	private String givenname;
	private String title;
	private String givenname2;
	private String sex;
	private String birthdate;
	private Adress adress;
	private Contact contact;
	private int svnr;
	private int patid;
	private String birthname;
	private ArrayList<Visit> visits = new ArrayList<Visit>();
	
	public Patient(){}
	
	public Patient(int patid){
		this.patid=patid;
	}
	
	public Patient(JSONObject jo){
		try {
			name=jo.getString("name");
			givenname=jo.getString("givenname");
			title=jo.getString("title");
			givenname2=jo.getString("givenname2");
			sex=jo.getString("sex");
			birthdate=jo.getString("birthdate");
			svnr = Integer.parseInt(jo.getString("svnr"));
			birthname = jo.getString("birthname"); 
			adress = new Adress(new JSONObject(jo.getString("adress")));
			contact = new Contact(new JSONObject(jo.getString("contact")));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public Patient(ResultSet rs){
		fullFillObject(rs);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGivenname() {
		return givenname;
	}

	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGivenname2() {
		return givenname2;
	}

	public void setGivenname2(String givenname2) {
		this.givenname2 = givenname2;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	public int getPatId(){
		return patid;
	}
	
	public void setPatId(int patid){
		this.patid=patid;
	}
	
	public int getSVNR(){
		return this.svnr;
	}
	
	public void setSVNR(int svnr){
		this.svnr=svnr;
	}

	public String getBirthname() {
		return birthname;
	}

	public void setBirthname(String birthname) {
		this.birthname = birthname;
	}
	
	public ArrayList<Visit> getVisits(){
		return visits;
	}
	
	public void setVisit(ArrayList<Visit> visits){
		this.visits=visits;
	}
	
	public void addVisit(Visit v){
		visits.add(v);
	}
	
	public String toTableRow(){
		return "<tr onclick=\"patientListClick('"+svnr+"','"+birthdate+"')\"><td class=\"name\">"+name+"</td><td class=\"givenname\">"+givenname+"</td><td class=\"svnr\">"+svnr+"</td><td class=\"birthdate\">"+birthdate+"</td></tr>";
	}
	
	public Patient fullFillObject(ResultSet rs){
		try {
			patid=Integer.parseInt(rs.getString("patid"));
			name=rs.getString("name");
			givenname=rs.getString("givenname");
			givenname2=rs.getString("givenname2");
			title=rs.getString("title");
			birthname=rs.getString("birthname");
			svnr=rs.getInt("svnr");
			birthdate= new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("birthdate"));
			adress = new Adress(rs);
			contact = new Contact(rs);
			sex = rs.getString("sex");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
}
