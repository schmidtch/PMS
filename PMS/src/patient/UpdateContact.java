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
import model.Contact;
import model.Patient;

@Path("/updateContact")
public class UpdateContact {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public String updatePatientAdress(String input){
		JSONObject resp = new JSONObject();
		try {
			JSONObject jo = new JSONObject(input);
			Patient p = new Patient();
			Contact c = new Contact();
			c.setEmail(jo.getString("email"));
			c.setTelefon(jo.getString("telefon"));
			p.setContact(c);
			p.setBirthdate(jo.getString("gebdat"));
			p.setSVNR(Integer.parseInt(jo.getString("svnr")));
			PatientController pc = new PatientController(p);
			pc.setPatient(p);
			pc.updatePatientContact();
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
