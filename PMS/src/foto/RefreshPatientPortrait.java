package foto;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import controller.IImageController;
import model.IImage;

@Path("/refreshPortrait")
public class RefreshPatientPortrait {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String refreshPatientPortrait(String input){
		JSONObject in = new JSONObject(input);
		JSONObject response = new JSONObject();
		IImageController iic = new IImageController();
		int pid = Integer.parseInt(in.getString("pid"));
		String state = "", msg="", pic="";
		
		
		try {
			IImage ii = iic.getPatientPortrait(pid);
			state="success";
			if(ii!=null){
				pic=ii.getImg();
			}
		} catch (SQLException e) {
			state="error";
			msg=e.getMessage();
			e.printStackTrace();
		}
		
		response.put("state", state);
		response.put("msg", msg);
		response.put("pic", pic);
		
		
		
		return response.toString();
	}
	
}
