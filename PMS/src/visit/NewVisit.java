package visit;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Patient;

import org.json.JSONObject;

import controller.VisitController;

@Path("/newVisit")
public class NewVisit {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public String newVisit(String input){
		
		JSONObject in = new JSONObject(input); 
		JSONObject response = new JSONObject();
		Patient p = new Patient();
		p.setPatId(Integer.parseInt(in.getString("patid")));
		p.setSVNR(Integer.parseInt(in.getString("svnr")));
		p.setBirthdate(in.getString("birthdate"));
		VisitController vc = new VisitController();
		int caseno = vc.insertVisit(p);
		if(caseno!=0){
			response.put("msg", "success");
			response.put("caseno", String.valueOf(caseno));
		} else {
			response.put("msg", "error");
		}
		
		return response.toString();
	}
	
	
}
