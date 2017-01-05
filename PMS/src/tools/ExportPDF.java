package tools;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Document;
import model.Patient;

import org.json.JSONObject;

import utilities.PDFManager;


@Path("/exportPDF")
public class ExportPDF {

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String exportPDF(String input) {
		JSONObject in = new JSONObject(input);
		JSONObject response = new JSONObject();
		
		int caseno = Integer.parseInt(in.getString("caseno"));
		int pid = Integer.parseInt(in.getString("pid"));
		String betrag = in.getString("betrag");
		
		PDFManager pdfManager = new PDFManager();
		Document d = new Document(Document.HN, caseno);
		Patient p = new Patient(pid);
		String status="error", error="";
		try {
			try {
				if(pdfManager.createPDF(d, p, betrag)){
					status="success";
				} else {
					error="Patient sind keine Leistungen zugeordnet!";
				}
			} catch (IOException e) {
				error=e.getMessage();
			}
		} catch (SQLException | IllegalArgumentException e) {
			error=e.getMessage();
		}
		response.put("state", status);
		response.put("error", error);
		
		return response.toString();		
	}
}
