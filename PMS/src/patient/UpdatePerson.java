package patient;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import controller.PatientController;
import model.Patient;

@Path("/updatePersondata")
public class UpdatePerson {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public String updatePatientAdress(String input){
		JSONObject resp = new JSONObject();
		try {
			JSONObject jo = new JSONObject(input);
			Patient p = new Patient();
			String newPatient = jo.getString("newPatient");
			p.setBirthdate(jo.getString("gebdat"));
			p.setSVNR(Integer.parseInt(jo.getString("svnr")));
			p.setName(jo.getString("name"));
			p.setGivenname(jo.getString("givenname"));
			p.setGivenname2(jo.getString("givenname2"));
			PatientController pc = new PatientController(p);
			pc.setPatient(p);
			if(newPatient.equals("yes")){
				pc.updatePatient(jo.getString("oldsvnr"), jo.getString("oldgebdat"));
			} else {
				pc.updatePatientPersonData();	
			}
			resp.put("state", "success");
		} catch (JSONException e) {
			resp.put("state", "error");
			resp.put("error", e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			resp.put("state", "error");
			resp.put("error", e.getMessage());
			e.printStackTrace();
		}
		
		return resp.toString();
	}
}
