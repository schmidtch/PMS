package model;

import java.util.ArrayList;
import java.util.Properties;

import utilities.StringReplacer;

public class Visit {

	private int caseno;
	private String visitdate;
	private ArrayList<Diagnose> diagnosis = new ArrayList<Diagnose>();
	private ArrayList<Service> services = new ArrayList<Service>();
	private String serviceCatalogueHTML = "";
	private int numberService=0;
	
	public Visit(){}

	public Visit(int caseno){
		this.caseno=caseno;
	}

	public int getCaseno() {
		return caseno;
	}

	public void setCaseno(int caseno) {
		this.caseno = caseno;
	}

	public String getVisitdate() {
		return visitdate;
	}

	public void setVisitdate(String visitdate) {
		this.visitdate = visitdate;
	}
	
	public String toHTMLParagraph()	{
		String diagButton="", diagContent = "", printServices = "";
		StringReplacer sr = new StringReplacer();
		Properties p = new Properties();
		Properties pchecked = new Properties();
		Properties pvisit = new Properties();
		
		if(!this.diagnosis.isEmpty()){
			diagButton = "<span class=\"link showDiagnosis\">Diagnose Anzeigen</span>";
			for(Diagnose d : this.diagnosis) {
				diagContent += d.toHTMLBlock();
			}
		}
		
		if(!this.services.isEmpty()) {
			for (Service s : this.services) {
				pchecked.put("$leistung_"+s.getServiceCatalogueId()+"_checked", "checked");
			}
		}
		sr.setReplacements(pchecked);
		
		if(serviceCatalogueHTML.length()==0){
			p.put("$services", "Kein Leistungs-Katalog vorhanden!");
		} else {
			serviceCatalogueHTML = sr.replaceInString(serviceCatalogueHTML);
			p.put("$services", serviceCatalogueHTML);
		}
		sr.setReplacements(p);
		printServices = sr.replaceInFile(this.getClass().getResource("../../../html/service.html").getPath());
		
		String hasAll="inline";
		if(this.diagnosis.size()==2){
			hasAll="none";
		}
		
		pvisit.put("$caseno", String.valueOf(this.caseno));
		pvisit.put("$hasAll", hasAll);
		pvisit.put("$visitdate", visitdate);
		pvisit.put("$diagButton", diagButton);
		pvisit.put("$printServicesdiagContent", printServices+diagContent);
		sr.setReplacements(pvisit);
		
		return sr.replaceInFile(this.getClass().getResource("../../../html/visit.html").getPath()); 
	}
	
	public String toTableRow(){
		return "<tr><td>"+caseno+"</td><td>"+visitdate+"</td></tr>";
	}

	public ArrayList<Diagnose> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(ArrayList<Diagnose> diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	public void addDiagnose(Diagnose d){
		diagnosis.add(d);
	}

	public ArrayList<Service> getServices() {
		return services;
	}

	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

	public String getServiceCatalogueHTML() {
		return serviceCatalogueHTML;
	}

	public void setServiceCatalogueHTML(String serviceCatalogueHTML) {
		this.serviceCatalogueHTML = serviceCatalogueHTML;
	}

	public int getNumberService() {
		return numberService;
	}

	public void setNumberService(int numberService) {
		this.numberService = numberService;
	}
	
	
	
}
