package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ServiceCatalogue {

	private int numberOfService=0;
	
	public ServiceCatalogue(){}
	
	public String getCatalogueAsHTML() throws SQLException{
		String catalogue = "";
		StringReplacer sr = new StringReplacer();
		Properties p = new Properties();
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from service_catalogue");
		setNumberOfService(rs.getFetchSize());
		while(rs.next()) {
			p.put("$id", rs.getString("service_catalogue_id"));
			p.put("$title", rs.getString("title"));
			sr.setReplacements(p);
			catalogue += sr.replaceInFile(this.getClass().getResource("../../../html/service_template.html").getPath());
		}
		return catalogue;
	}

	public int getNumberOfService() {
		return numberOfService;
	}

	public void setNumberOfService(int numberOfService) {
		this.numberOfService = numberOfService;
	}
	
}
