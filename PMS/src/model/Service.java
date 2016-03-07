package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Service {

	private String title;
	private int price;
	private int serviceCatalogueId;
	
	public Service(){}
	
	public Service(ResultSet rs) throws SQLException{
		this.title=rs.getString("title");
		this.price=Integer.parseInt(rs.getString("price"));
		this.serviceCatalogueId=Integer.parseInt(rs.getString("service_catalogue_id"));
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public int getServiceCatalogueId() {
		return serviceCatalogueId;
	}

	public void setServiceCatalogueId(int serviceCatalogueId) {
		this.serviceCatalogueId = serviceCatalogueId;
	}
	
	
	
	
}
