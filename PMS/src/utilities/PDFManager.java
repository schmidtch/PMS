package utilities;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Diagnose;
import model.Document;
import model.Patient;
import model.Service;
import model.Visit;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import controller.DiagnoseController;
import controller.DocumentController;
import controller.PatientController;
import controller.ServiceController;


public class PDFManager {
	private PDDocument pdDoc = null;
	private PDPageContentStream cos;
	private int fontHeight = 12;
	private int lineHeight = fontHeight+3;

	private String filePath = "/home/christopher/Dokumente/PMS/patients/";

	public PDFManager() {
		
	}

	public boolean createPDF(Document d, Patient p, String betrag) throws IOException, SQLException, IllegalArgumentException {
		boolean success = false;
        
        ServiceController sc = new ServiceController();
        ArrayList<Service> services = sc.getServicesForVisit(new Visit(d.getCaseno()));
        
        if(!services.isEmpty()){
	        success=true;
			pdDoc = new PDDocument();
			PDPage page1 = new PDPage();
			PDRectangle rect = page1.getMediaBox();
	        pdDoc.addPage(page1);
	        PDFont fontPlain = PDType1Font.HELVETICA;
	        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
	        DocumentController dc = new DocumentController();
	        PatientController pc = new PatientController(p);
	        DiagnoseController dco = new DiagnoseController();
	        p = pc.getPatientByPid();
	        ArrayList<Diagnose> diagnosis = dco.getDiagnosisForVisit(new Visit(d.getCaseno()));
	        d.setTitle("Honorarnote_"+String.valueOf(p.getPatId())+"_"+String.valueOf(d.getCaseno())+"_"+String.valueOf(p.getSVNR())+"-"+new SimpleDateFormat("MMddSSssyyyy").format(new Date())+".pdf");
	        d.setComment("Dokument fuer "+p.getGivenname()+" "+p.getName() + " am " + new SimpleDateFormat("yyyyMMdd").format(new Date()));
	        filePath += p.getName()+"_"+p.getGivenname()+"_"+p.getSVNR()+"/honorarnote/";
	        
	        int id = dc.findDocument(d);
	        
	        if(id==0){
	        	d = dc.insertDocument(d);
	        } else {
	        	d.setDocid(id);
	        	dc.updateDocument(d);
	        }
	        
	        // Start a new content stream which will "hold" the to be created content
	        cos = new PDPageContentStream(pdDoc, page1);
	        
	
	        // Head
	        PDImageXObject pdImage = PDImageXObject.createFromFile(this.getClass().getResource("../../../pictures/hr_head.png").getPath(), pdDoc);
	        cos.drawImage(pdImage, 20, rect.getHeight()-pdImage.getHeight()/5-20, pdImage.getWidth() / 5, pdImage.getHeight() / 5);
	        cos.beginText();
	        cos.setFont(fontPlain, fontHeight);
	        cos.newLineAtOffset(rect.getWidth()-180, rect.getHeight() - 45);
	        cos.showText("Heiligenstatt 2");
	        cos.newLineAtOffset(0, -lineHeight);
	        cos.showText("A-8850 Murau");
	        cos.newLineAtOffset(0, -lineHeight);
	        cos.showText("+43 664 266 63 52");
	        cos.newLineAtOffset(0, -lineHeight);
	        cos.showText("office@tcm-planegger.com");
	        cos.newLineAtOffset(0, -lineHeight);
	        cos.showText("www.tcm-planegger.com");
	        cos.endText();
	        
	        //Textbox für Versicherter:
	        cos.beginText();
	        cos.setFont(fontBold, fontHeight+2);
	        cos.newLineAtOffset(50, rect.getHeight() - 200);
	        cos.showText("Versicherte(r): ");
	        cos.setFont(fontPlain, fontHeight+2);
	        cos.newLineAtOffset(130, 0);
	        cos.showText(p.getGivenname() + " " + p.getName());
	        cos.newLineAtOffset(0, -lineHeight-2);
	        cos.showText(p.getAdress().getStreet()+ " "+p.getAdress().getStreetnumber());
	        cos.newLineAtOffset(0, -lineHeight-2);
	        cos.showText(p.getAdress().getZip() + " " + p.getAdress().getLocation());
	        cos.newLineAtOffset(0, -lineHeight-2);
	        cos.showText(p.getAdress().getCountry());
	        cos.endText();
	
	        //Textbox für Patienten:
	        cos.beginText();
	        cos.setFont(fontBold, fontHeight+2);
	        cos.newLineAtOffset(50, rect.getHeight() - 300);
	        cos.showText("Patient(in): ");
	        cos.setFont(fontPlain, fontHeight+2);
	        cos.newLineAtOffset(130, 0);
	        cos.showText(p.getGivenname() + " " + p.getName());
	        cos.newLineAtOffset(0, -lineHeight-2);
	        cos.showText(p.getAdress().getStreet()+ " "+p.getAdress().getStreetnumber());
	        cos.newLineAtOffset(0, -lineHeight-2);
	        cos.showText(p.getAdress().getZip() + " " + p.getAdress().getLocation());
	        cos.newLineAtOffset(0, -lineHeight-2);
	        cos.showText(p.getAdress().getCountry());
	        cos.endText();
	        
	        //Textbox für Honorar und Datum:
	        cos.beginText();
	        cos.setFont(fontPlain, fontHeight+2);
	        cos.newLineAtOffset(50, rect.getHeight() - 400);
	        cos.showText("Honorarnote Nr.: " + new SimpleDateFormat("yyyy").format(new Date())+"/"+String.valueOf(d.getDocid()));
	        cos.newLineAtOffset(390, 0);
	        cos.showText("Datum: "+new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
	        cos.endText();
	        
	        //Textbox für Leistungen:
	        cos.beginText();
	        cos.setFont(fontBold, fontHeight+2);
	        cos.newLineAtOffset(50, rect.getHeight() - 400-lineHeight*2);
	        cos.showText("Leistungen:");
			cos.setFont(fontPlain, fontHeight+2);
	        int x = 0;
	        int y = 115;
	        for(Service s : services){
	    		cos.newLineAtOffset(y, x);
	    	    cos.showText("- "+s.getTitle());
	    	    x=-lineHeight-3;
	    	    y=0;
	        }
	        cos.endText();
	        
	        //Textbox für Diagnosen:
	        cos.beginText();
	        cos.setFont(fontBold, fontHeight+2);
	        cos.newLineAtOffset(50, rect.getHeight() - 560);
	        if(!diagnosis.isEmpty()){
		        cos.showText("Diagnose: ");
				cos.setFont(fontPlain, fontHeight+2);
	        	for(Diagnose dg : diagnosis){
	        		if(dg.getType().equals("sm")){
	        	        cos.newLineAtOffset(115, 0);
	        	        cos.showText(dg.getText());
	        		}
	        	}
	        }
	        cos.endText();
	        
	        //Textbox für Abrechnung:
	        cos.beginText();
	        cos.setFont(fontBold, fontHeight+2);
	        cos.newLineAtOffset(rect.getWidth()-300, rect.getHeight() - 650);
	        cos.showText("Rechnungsbetrag in Euro: "+betrag+" .-");
	        cos.endText();
	        
	        //Textbox für Footer:
	        cos.beginText();
	        cos.setFont(fontPlain, fontHeight);
	        cos.newLineAtOffset(50, rect.getHeight() - 680);
	        cos.showText("Zahlbar prompt nach Erhalt der Honorarnote");
	        cos.newLineAtOffset(0, -lineHeight);
	        cos.showText("Steiermärkische Sparkasse");
	        cos.newLineAtOffset(0, -lineHeight);
	        cos.showText("Konto-Nr.: 00001611623, BLZ: 20815");
	        cos.newLineAtOffset(0, -lineHeight);
	        cos.showText("IBAN: AT742081500001611623, BIC: STSPAT2GXXX");
	        cos.endText();
	        
	        
	        cos.close();
	        exportFile(d.getTitle());
        }
        return success;
		
	}

	private void exportFile(String title) throws IOException{
		if(pdDoc!=null){
			pdDoc.save(filePath+title);
			pdDoc.close();
		}
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
