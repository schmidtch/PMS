package controller;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utilities.DBConnectionHandler;
import model.Adress;
import model.Contact;
import model.Patient;

public class PatientController {

	private Patient p;
	private Adress a;
	private Contact c;
	private ArrayList<Patient> pList;
	
	public PatientController(){
		setpList(new ArrayList<Patient>());
	}
	
	public PatientController(Patient p){
		this.p=p;
		this.a=p.getAdress();
		this.c=p.getContact();
	}
	
	public void insertNewPatient() throws SQLException {
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		stmt.execute("Insert into Person values ("+p.getSVNR()+", STR_TO_DATE('"+p.getBirthdate()+"', '%d.%m.%Y'), '"+p.getName()+"', '"+p.getGivenname()+"', '"+p.getTitle()+"', '"+p.getGivenname2()+"', '"+p.getSex()+"', '"+p.getBirthname()+"')");
		stmt.execute("Insert into Patient (SVNR, BIRTHDATE) values ("+p.getSVNR()+", STR_TO_DATE('"+p.getBirthdate()+"', '%d.%m.%Y'))");
		stmt.execute("Insert into Address (STREET, STREETNR, LOCATION, ZIP, SVNR, BIRTHDATE, COUNTRY) values ('"+a.getStreet()+"', "+a.getStreetnumber()+", '"+a.getLocation()+"', "+a.getZip()+", "+p.getSVNR()+", STR_TO_DATE('"+p.getBirthdate()+"', '%d.%m.%Y'), '"+a.getCountry()+"');");
		stmt.execute("Insert into Contact (TELEFON, EMAIL, SVNR, BIRTHDATE) values ('"+c.getTelefon()+"', '"+c.getEmail()+"', "+p.getSVNR()+", STR_TO_DATE('"+p.getBirthdate()+"', '%d.%m.%Y'))");
		stmt.close();
		String folder = p.getName()+"_"+p.getGivenname()+"_"+p.getSVNR();
		new File("/home/christopher/Dokumente/PMS/patients/"+folder+"/scans").mkdirs();//"/Volumes/Daten/PMS/data/patients/"+folder+"/scans").mkdirs();
		new File("/home/christopher/Dokumente/PMS/patients/"+folder+"/honorarnote").mkdirs();
		DBConnectionHandler.closeConnection();
		
	}
	
	public void setPatient(Patient p){
		this.p=p;
		this.a=p.getAdress();
		this.c=p.getContact();
	}
	
	public Patient getPatient(){
		return p;
	}
	
	public Patient getPatientBySVNRBirthdate() throws SQLException{
		Patient p = null;
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from patientdata where svnr="+String.valueOf(this.p.getSVNR())+" and birthdate=STR_TO_DATE('"+this.p.getBirthdate()+"', '%d.%m.%Y')");
		if(rs.next()) p=this.p.fullFillObject(rs);
		rs.close();
		stmt.close();
		DBConnectionHandler.closeConnection();
		return p;
	}
	
	public Patient getPatientByPid() throws SQLException{
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from patientdata where patid="+String.valueOf(p.getPatId())+"");
		if(rs.next()) return p.fullFillObject(rs);
		else return null;
		
	}

	public ArrayList<Patient> getpList() throws SQLException {
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from patientdata;");
		
		while (rs.next()){
			pList.add(new Patient(rs));
		}
		
		rs.close();
		stmt.close();
		DBConnectionHandler.closeConnection();
		
		return pList;
	}
	
	public void updatePatientAddress() throws SQLException{
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		stmt.executeQuery("update Address set street='"+a.getStreet()+", ' where svnr="+String.valueOf(this.p.getSVNR())+" and birthdate=STR_TO_DATE('"+this.p.getBirthdate()+"', '%d.%m.%Y')");
		stmt.close();
		DBConnectionHandler.closeConnection();
		
	}

	public void setpList(ArrayList<Patient> pList) {
		this.pList = pList;
	}	
	
}
