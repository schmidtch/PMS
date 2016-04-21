package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utilities.DBConnectionHandler;
import model.Service;
import model.Visit;

public class ServiceController {

	public ServiceController(){}
	
	public void insertServiceForVisit(int caseno, Service s) throws SQLException{
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		stmt.executeUpdate("Insert into Service (caseno, Service_Catalogue_id) values ("+String.valueOf(caseno)+", "+s.getServiceCatalogueId()+");");
		stmt.close();
		DBConnectionHandler.closeConnection();
	}
	

	public void clearServicesForVisit(int caseno) throws SQLException{
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		stmt.executeUpdate("Delete from Service where caseno="+String.valueOf(caseno));
		stmt.close();
		DBConnectionHandler.closeConnection();
	}
	
	public ArrayList<Service> getServicesForVisit(Visit v) throws SQLException{
		ArrayList<Service> services = new ArrayList<Service>();
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select s.serviceid, sc.* from Service s, Service_Catalogue sc where caseno="+String.valueOf(v.getCaseno()+" and s.service_catalogue_id=sc.service_catalogue_id"));
		while(rs.next()){
			services.add(new Service(rs));
		}
		
		return services;
	}
	
	
}
