package foto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import controller.IImageController;
import model.IImage;
import utilities.StringReplacer;

@Path("/gettungs")
public class GetTungFotos {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public String getTungFotos(String input){

		JSONObject in = new JSONObject(input);
		JSONObject response = new JSONObject();
		
		Properties p = new Properties();
		StringReplacer sr = new StringReplacer();
		IImageController iic = new IImageController();
		ArrayList<IImage> tungs;
		try {
			tungs = iic.getTungImages(Integer.parseInt(in.getString("patid")));

			String fotoChoose =  "", fotos="";
			
			if(!tungs.isEmpty()){
				response.put("fotos", "yes");
				for (IImage img : tungs){
					fotoChoose += "<option value=\""+String.valueOf(img.getId())+"\">"+img.getVisitdate()+"</option>";
					fotos += "<div class=\"comparePic\" style=\"width:100px; margin:auto;margin-right:5px; float:left;\" id=\"comparePic"+String.valueOf(img.getId())+"\"><img style=\"max-width:100%; margin:auto; max-height:80%; display:block;\" src=\"data:image/jpg;base64,"+ img.getImg() +"\" /><span class=\"bildBeschriftung\">"+img.getVisitdate()+"</span></div>";
				}
			} else {
				response.put("fotos", "no");
			}
			
			p.put("$fotos", fotos);
			p.put("$foto_choose", fotoChoose);
			
			sr.setReplacements(p);
			
			response.put("state", "success");
			response.put("data", sr.replaceInFile(this.getClass().getResource("../../../html/tungfotos.htm").getPath()));
			
		} catch (NumberFormatException|SQLException|JSONException e) {
			response.put("state", "error");
			response.put("data", e.getMessage());
			e.printStackTrace();
		}
		
		return response.toString();
		
		
	}
	
	
}
