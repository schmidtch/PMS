package patient;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Patient;

import org.json.JSONObject;

import controller.PatientController;

@Path("/getPatientList")
public class GetPatientList {

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPatientList(){
		
		JSONObject response = new JSONObject();
		JSONObject pObj = null;
		ArrayList<JSONObject> patients = new ArrayList<JSONObject>();
		PatientController pc = new PatientController();
		
		try {
			ArrayList<Patient> pList = pc.getpList();
			if(pList.isEmpty()){
				response.put("patient", "error");
			} else {
				for(Patient p : pList){
					pObj = new JSONObject();
					pObj.put("name", p.getName());
					pObj.put("givenname", p.getGivenname());
					pObj.put("svnr", p.getSVNR());
					patients.add(pObj);
				}
				response.put("patients", patients);
			}
		} catch (SQLException e) {
			response.put("patient", "error");
			e.printStackTrace();
		}
		
		return response.toString();
		
	}
	
}
