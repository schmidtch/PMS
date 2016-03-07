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


@Path("showF")
public class ShowFoto {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadFotot(String input){
		
		JSONObject in = new JSONObject(input);
		JSONObject response = new JSONObject();
		IImageController ic = new IImageController();
		String msg = "", state="", pic="";
		int caseno = Integer.parseInt(in.getString("caseno"));
		
		try {
			ArrayList<IImage> images = ic.getAllImagesForVisit(caseno);
			if(images.isEmpty()){
				state="error";
				msg="Keine Fotos vorhanden!";
			} else {
				state="success";
				for(IImage i : images){
					pic=i.getImg();
				}
			}
	        	
			
		} catch (SQLException e) {
			msg=e.getMessage();
			state="error";
			e.printStackTrace();
		}	
		
		response.put("state", state);
		response.put("msg", msg);
		response.put("pic", pic);
		
		return response.toString();
	}
		
}
