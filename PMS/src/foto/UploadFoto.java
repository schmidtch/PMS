package foto;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.IImage;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;

import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

import controller.IImageController;


@Path("/upload")
public class UploadFoto {

	private final static double P_HEIGHT = 300.0;
	private final static double A_HEIGHT = 768.0;
	
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadFotot(FormDataMultiPart form){
		
		JSONObject response = new JSONObject();

		boolean saveFile=false;
		FormDataBodyPart filePart = null,  descPart=null;
		ContentDisposition headerOfFilePart = null;
		int caseno = Integer.parseInt(form.getField("caseno").getValue()),anzFiles = Integer.parseInt(form.getField("anzPix").getValue());
		InputStream inStream = null;
		String base64Pic = "", msg = "", state = "";
		IImageController ic = new IImageController();
		
		for(int i=0; i<anzFiles; i++){
			filePart = form.getField("file_"+String.valueOf(i));
			descPart = form.getField("description_"+String.valueOf(i));
			headerOfFilePart = filePart.getContentDisposition();
			BufferedImage bi = filePart.getValueAs(BufferedImage.class);
			if(descPart.getValue().equals("portrait")){
				bi = resizeImage(bi, bi.getType(), P_HEIGHT);
			} else {
				bi = resizeImage(bi, bi.getType(), A_HEIGHT);
				//inStream = filePart.getValueAs(InputStream.class);
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				ImageIO.write(bi, "jpg", os);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			inStream = new ByteArrayInputStream(os.toByteArray());
			try {
	
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = inStream.read(data, 0, data.length)) != -1) {
				  buffer.write(data, 0, nRead);
				}
				buffer.flush();
				byte[] bytes =  buffer.toByteArray();
				
		        base64Pic = new String(Base64.encodeBase64(bytes));
		        
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(base64Pic.length()>0){				
				IImage ii = new IImage(base64Pic);
				ii.setName(headerOfFilePart.getFileName());
				ii.setDescription(descPart.getValue());
				try {
					ic.insertImageForVisit(ii, caseno);
					state="success";
					msg="Bild erforlgreich hinzugeügt!";
				} catch (SQLException e) {
			    	state="error";
			    	msg=e.getMessage();
					e.printStackTrace();
				} 
				
				if(saveFile){
					try {
						String filePath = "/Volumes/Daten/PMS/data/patients/Pat_ient_2510/fotos/" + headerOfFilePart.getFileName();
						InputStream saveFileStream = filePart.getValueAs(InputStream.class);
				        OutputStream outpuStream = new FileOutputStream(new File(filePath));
				        int read = 0;
				        byte[] bytes2 = new byte[1024];
				        while ((read = saveFileStream.read(bytes2)) != -1) {
				            outpuStream.write(bytes2, 0, read);
				        }
				        outpuStream.flush();
				        outpuStream.close();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
				}
			} else {
				state="error";
				msg="Error with Base64 Encoding!";
			}
		}
		response.put("msg", msg);
		response.put("state", state);
		
		return response.toString();
		
		
	}
	private static BufferedImage resizeImage(BufferedImage originalImage, int type, double newHeight){
		double ratio = (double)originalImage.getHeight()/newHeight;
		int width = (int)(originalImage.getWidth()/ratio);
		BufferedImage resizedImage = new BufferedImage(width, (int)newHeight, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, (int)newHeight, null);
		g.dispose();

		return resizedImage;
	}

	
}
