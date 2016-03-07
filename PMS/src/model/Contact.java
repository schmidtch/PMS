package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

public class Contact {

	private String telefon;
	private String email;
	
	public Contact(){}
	
	public Contact(JSONObject jo){
		telefon = jo.getString("telefon");
		email = jo.getString("email");
	}
	
	public Contact(ResultSet rs){
		try {
			telefon = rs.getString("telefon");
			email = rs.getString("email");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
