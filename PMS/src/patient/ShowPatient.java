package patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import model.IImage;
import model.Patient;
import model.Visit;
import controller.IImageController;
import controller.PatientController;
import controller.VisitController;
import utilities.ServiceCatalogue;
import utilities.StringReplacer;

@Path("/show")
public class ShowPatient {

	@GET
	@Produces("text/html")
	public String showPatients(){
		StringReplacer sr = new StringReplacer();
		Properties pr = new Properties();
		String content = "";
		
		PatientController pc = new PatientController();
		try {
			ArrayList<Patient> pList = pc.getpList();
			
			if(!pList.isEmpty()){
				for(Patient p : pList){
					content += p.toTableRow();
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pr.put("$patients", content);
		
		
		sr.setReplacements(pr);
		
		return sr.replaceInFile(this.getClass().getResource("../../../html/showPatients.html").getPath());
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public String showPatient(String input) {
		
		JSONObject jo = new JSONObject(input);
		JSONObject response = new JSONObject();
		StringReplacer sr = new StringReplacer();
		Properties pr = new Properties();
		Patient p = new Patient();
		p.setBirthdate(jo.getString("birthdate"));
		p.setSVNR(Integer.parseInt(jo.getString("svnr")));
		try {
			response.put("msg", "success");
			p = new PatientController(p).getPatientBySVNRBirthdate();
			if(p!=null){
				pr.put("$pid", String.valueOf(p.getPatId()));
				pr.put("$patientsName", "<span class=\"pData\" id=\"givenname\">"+p.getGivenname() +"</span> <span class=\"pData\" id=\"givenname2\">" + p.getGivenname2() + "</span> <span class=\"pData\" id=\"name\">" + p.getName()+ "</span>");
				pr.put("$svnr", String.valueOf(p.getSVNR()));
				pr.put("$gebdat", p.getBirthdate());
				pr.put("$street", p.getAdress().getStreet() + " " + String.valueOf(p.getAdress().getStreetnumber()));
				pr.put("$zip", String.valueOf(p.getAdress().getZip()));
				pr.put("$location", p.getAdress().getLocation());
				pr.put("$country", p.getAdress().getCountry());
				pr.put("$telefon", p.getContact().getTelefon());
				pr.put("$email", p.getContact().getEmail());
				ArrayList<Visit> visits = new VisitController().getVisitsForPatient(p);
				IImageController iic = new IImageController();
				String visitsData = "";
				if(visits.isEmpty()){
					visitsData = "Keine Besuche vorhanden!";
				} else {
					ServiceCatalogue sca = new ServiceCatalogue();
					String serviceCatalogue = sca.getCatalogueAsHTML();
					if(serviceCatalogue.length()==0){
						serviceCatalogue = "Kein Leistungs-Katalog vorhanden!";
					}
					for (Visit v : visits) {
						if(iic.getAllImagesForVisit(v.getCaseno()).isEmpty()) v.fotosAktiv="link_inactiv";
						v.setServiceCatalogueHTML(serviceCatalogue);
						v.setNumberService(sca.getNumberOfService());
						visitsData += v.toHTMLParagraph();
					}
				}
				pr.put("$visits", visitsData);
				String pic="", sex="";
				IImage ii = iic.getPatientPortrait(p.getPatId());
				
				if(ii!=null){
					pic = "<img id=\"portrait\" src=\"data:image/jpg;base64,"+ ii.getImg() +"\" style=\"height:250px;\" />";
				} else {
					if(p.getSex().equals("M")){
						sex="male";
					} else if (p.getSex().equals("F")){
						sex="female";
					}	
					pic = "<img id=\"portrait\" src=\"pictures/"+sex+".png\" name=\"PatientIcon\" />";
				}
				pr.put("$portrait", pic);
				
				
				sr.setReplacements(pr);
				response.put("data",sr.replaceInFile(this.getClass().getResource("../../../html/patient.html").getPath()));
			}
		} catch (SQLException e) {
			//todo: Error handling
			response.put("msg", "error");
			e.printStackTrace();
		}
		
		return response.toString();
	}
	
	
}
