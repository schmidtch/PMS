package service;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Service;

import org.json.JSONObject;

import controller.ServiceController;

@Path("newService")
public class NewService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public String insertNewService(String input){
		JSONObject in = new JSONObject(input);
		JSONObject response = new JSONObject();
		ServiceController sc = new ServiceController();
		Service s = new Service();
		int caseno = Integer.parseInt(in.getString("caseno"));
		String status="success", msg="";
		String inServices = in.getString("selectedServices");
		if("0".equals(inServices) || inServices.length()==0) {
			status="error";
			msg="Keine Leistungen gewählt!";
		} else {
			if(inServices.indexOf(";")>-1){
				String[] services = inServices.split(";",-1);
				for(int i=0; i<services.length-1; i++){
					s.setServiceCatalogueId(Integer.parseInt(services[i]));
					try {
						sc.insertServiceForVisit(caseno, s);
					} catch (SQLException e) {
						status="error";
						msg=e.getMessage();
						break;
					}
				}
			}
		}
		
		response.put("status", status);
		response.put("msg", msg);
		
		return response.toString();
		
	}
	
}
