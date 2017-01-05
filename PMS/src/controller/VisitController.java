package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import utilities.DBConnectionHandler;
import model.Patient;
import model.Visit;

public class VisitController {

	public VisitController(){
		
	}
	
	public int insertVisit(Patient p){
		int id = 0;
		String today = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
		try {
			PreparedStatement stmt = DBConnectionHandler.getConnection().prepareStatement("insert into Visit (PATID, visitdate) values ('"+p.getPatId()+"', STR_TO_DATE('"+today+"', '%d.%m.%Y'))", Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			while(rs.next()){
				id = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			if(e.getMessage().indexOf("Duplicate entry")>-1){
				id = -1;
				System.out.println("INFO: "+e.getMessage() + " " +p.getPatId());
			} else {
				e.printStackTrace();
			}
		} finally {
			try {
				DBConnectionHandler.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	
	public ArrayList<Visit> getVisitsForPatient(Patient p){
		ArrayList<Visit> visits = new ArrayList<Visit>();
		DiagnoseController dc = new DiagnoseController();
		ServiceController sc = new ServiceController();
		Visit v = null;
		try {
			Statement stmt = DBConnectionHandler.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("select CASENO, visitdate from Visit where PATID="+String.valueOf(p.getPatId())+" order by visitdate desc;");
			if(rs.isBeforeFirst()){
				while (rs.next()) {
					v = new Visit();
					v.setCaseno(Integer.parseInt(rs.getString("CASENO")));
					v.setVisitdate(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("visitdate")));
					v.setDiagnosis(dc.getDiagnosisForVisit(v));
					v.setServices(sc.getServicesForVisit(v));
					visits.add(v);
				}
			} 
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				DBConnectionHandler.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return visits;
	}
	
}
