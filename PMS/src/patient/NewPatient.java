package patient;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Patient;

import org.json.JSONException;
import org.json.JSONObject;

import controller.PatientController;
import utilities.StringReplacer;

@Path("/new")
public class NewPatient {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getTemplate(){
		return new StringReplacer().replaceInFile(this.getClass().getResource("../../../html/newPatientTemplate.html").getPath());
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public String insertNewPatient(String input){
		JSONObject resp = new JSONObject();
		try {
			new PatientController(new Patient(new JSONObject(input))).insertNewPatient();
			resp.put("state", "success");
		} catch (JSONException e) {
			resp.put("state", "success");
			resp.put("error", e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			resp.put("state", "success");
			resp.put("error", e.getMessage());
			e.printStackTrace();
		}
		
		return resp.toString();
	}
	
}
