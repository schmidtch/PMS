package controller;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import utilities.DBConnectionHandler;
import model.IImage;

public class IImageController {

	public int insertImageForVisit(IImage i, int caseno) throws SQLException {
		
		PreparedStatement stmt = DBConnectionHandler.getConnection().prepareStatement("insert into Images (name, image, description, caseno) values ('"+i.getName()+"', ?, '"+i.getDescription()+"', "+String.valueOf(caseno)+")");
		stmt.setBinaryStream(1, new ByteArrayInputStream(i.getImg().getBytes(StandardCharsets.UTF_8)));
		stmt.executeUpdate();
		stmt.close();
		DBConnectionHandler.closeConnection();
		
		return i.getId();
	}
	
	public ArrayList<IImage> getAllImagesForVisit(int caseno) throws SQLException {
		ArrayList<IImage> images = new ArrayList<IImage>();
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select * from Images where caseno="+String.valueOf(caseno)+" and description!='portrait'");
		while (rs.next()) {
			images.add(new IImage(rs));
		}
		rs.close();
		stmt.close();
		DBConnectionHandler.closeConnection();
		
		return images;
	}
	
	public IImage getPatientPortrait(int pid) throws SQLException {
		
		IImage ii = null;
		
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select i.* from Images i, Visit v where i.description='portrait' and v.patid="+String.valueOf(pid)+" and v.caseno=i.caseno order by i.imageid desc;");
		if (rs.next()) {
			ii = new IImage(rs);
		}
		rs.close();
		stmt.close();
		DBConnectionHandler.closeConnection();
		
		return ii;
		
	}
	
	public ArrayList<IImage> getTungImages(int pid) throws SQLException{
		ArrayList<IImage> images = new ArrayList<IImage>();
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select i.*, v.visitdate from Images i, Visit v where i.description='zunge' and v.patid="+String.valueOf(pid)+" and v.caseno=i.caseno order by v.caseno desc;");
		IImage img = null;
		while (rs.next()){
			img = new IImage(rs);
			img.setVisitdate(new SimpleDateFormat("dd.MM.yyyy").format(rs.getDate("visitdate")));
			img.setCaseno(Integer.parseInt(rs.getString("caseno")));
			images.add(img);
		}
		rs.close();
		stmt.close();
		DBConnectionHandler.closeConnection();
		return images;
	}
	
}
