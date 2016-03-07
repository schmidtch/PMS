package diagnose;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Diagnose;

import org.json.JSONException;
import org.json.JSONObject;

import controller.DiagnoseController;
import utilities.StringReplacer;

@Path("/newDiagnose")
public class NewDiagnose {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getNewDiagnoseForm(){
		return new StringReplacer().replaceInFile(this.getClass().getResource("../../../html/newDiagnoseTemplate.html").getPath());
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createNewDiagnose(String input){
		JSONObject in = new JSONObject(input);
		JSONObject response = new JSONObject();
		
		DiagnoseController dc = new DiagnoseController();
		String state="error", error=""; 
		try {
			dc.insertDiagnoseForCaseno(Integer.parseInt(in.getString("caseno")), new Diagnose(in.getString("title"), in.getString("text"), in.getString("type")));
			state="success";
		} catch (NumberFormatException e) {
			error=e.getMessage();
			e.printStackTrace();
		} catch (JSONException e) {
			error=e.getMessage();
			e.printStackTrace();
		} catch (SQLException e) {
			error=e.getMessage();
			e.printStackTrace();
		}
		response.put("state", state);
		response.put("error", error);
		
		return response.toString();
	}
	
}
