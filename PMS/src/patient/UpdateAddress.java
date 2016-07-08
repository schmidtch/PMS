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
import model.Adress;
import model.Patient;

@Path("/updateAddress")
public class UpdateAddress {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public String updatePatientAdress(String input){
		JSONObject resp = new JSONObject();
		try {
			JSONObject jo = new JSONObject(input);
			Patient p = new Patient();
			Adress a = new Adress();
			a.setCountry(jo.getString("staat"));
			a.setLocation(jo.getString("ort"));
			a.setStreet(jo.getString("straße").split(" ")[0]);
			a.setStreetnumber(Integer.parseInt(jo.getString("straße").split(" ")[1]));
			a.setZip(Integer.parseInt(jo.getString("plz")));
			p.setAdress(a);
			p.setBirthdate(jo.getString("gebdat"));
			p.setSVNR(Integer.parseInt(jo.getString("svnr")));
			PatientController pc = new PatientController(p);
			System.out.println("Patient: "+p.getSVNR()+" with Address: "+p.getAdress().getStreet());
			resp.put("state", "success");
		} catch (JSONException e) {
			resp.put("state", "error");
			resp.put("error", e.getMessage());
			e.printStackTrace();
		}
		
		return resp.toString();
	}
}
