package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

public class Adress {

	private String street;
	private int streetnumber;
	private String location;
	private int zip;
	private String country;
	
	public Adress(){}
	
	public Adress(JSONObject jo){
		street = jo.getString("street");
		streetnumber = Integer.parseInt(jo.getString("streetnumber"));
		location = jo.getString("location");
		zip = Integer.parseInt(jo.getString("zip"));
		country = jo.getString("country");
	}
	
	public Adress(ResultSet rs){
		try {
			street = rs.getString("street");
			streetnumber = rs.getInt("streetnr");
			location = rs.getString("location");
			zip = rs.getInt("zip");
			country = rs.getString("country");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getStreetnumber() {
		return streetnumber;
	}

	public void setStreetnumber(int streetnumber) {
		this.streetnumber = streetnumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
