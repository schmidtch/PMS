package controller;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utilities.DBConnectionHandler;
import model.IImage;

public class IImageController {

	public int insertImageForVisit(IImage i, int caseno) throws SQLException {
		
		PreparedStatement stmt = DBConnectionHandler.getConnection().prepareStatement("insert into images (name, image, description, caseno) values ('"+i.getName()+"', ?, '"+i.getDescription()+"', "+String.valueOf(caseno)+")");
		stmt.setBinaryStream(1, new ByteArrayInputStream(i.getImg().getBytes(StandardCharsets.UTF_8)));
		stmt.executeUpdate();
		stmt.close();
		DBConnectionHandler.closeConnection();
		
		return i.getId();
	}
	
	public ArrayList<IImage> getAllImagesForVisit(int caseno) throws SQLException {
		ArrayList<IImage> images = new ArrayList<IImage>();
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from images where caseno="+String.valueOf(caseno));
		while (rs.next()) {
			images.add(new IImage(rs));
		}
		rs.close();
		stmt.close();
		DBConnectionHandler.closeConnection();
		
		return images;
	}
	
}
