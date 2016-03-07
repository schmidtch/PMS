package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utilities.DBConnectionHandler;
import model.Diagnose;
import model.Visit;

public class DiagnoseController {

	public void insertDiagnoseForCaseno(int caseno, Diagnose d) throws SQLException{
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		stmt.executeUpdate("Insert into Diagnosis (title, text, type, caseno) values ('"+d.getTitle()+"', '"+d.getText()+"', '"+d.getType()+"', "+String.valueOf(caseno)+")");
		stmt.close();
		DBConnectionHandler.closeConnection();
		
	}
	
	public ArrayList<Diagnose> getDiagnosisForVisit(Visit v) throws SQLException{
		ArrayList<Diagnose> diagnosis = new ArrayList<Diagnose>();
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from Diagnosis where caseno="+String.valueOf(v.getCaseno()));
		while(rs.next()){
			diagnosis.add(new Diagnose(rs));
		}
		
		return diagnosis;
	}
	
}
