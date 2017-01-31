package foto;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.IImage;

import org.json.JSONObject;

import controller.IImageController;


@Path("/showF")
public class ShowFoto {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadFotot(String input){
		
		JSONObject in = new JSONObject(input);
		JSONObject response = new JSONObject();
		IImageController ic = new IImageController();
		String msg = "", state="";
		int caseno = Integer.parseInt(in.getString("caseno"));
		int count = 0;
		
		try {
			ArrayList<IImage> images = ic.getAllImagesForVisit(caseno);
			if(images.isEmpty()){
				state="error";
				msg="Keine Fotos vorhanden!";
			} else {
				
				state="success";
				for(IImage i : images){
					response.put("pic_"+String.valueOf(count),i.getImg());
					response.put("picDesc_"+String.valueOf(count), i.getDescription());
					count++;
				}
			}
	        	
			
		} catch (SQLException e) {
			msg=e.getMessage();
			state="error";
			e.printStackTrace();
		}	
		response.put("anz", String.valueOf(count));
		response.put("state", state);
		response.put("msg", msg);
		
		return response.toString();
	}
		
}
