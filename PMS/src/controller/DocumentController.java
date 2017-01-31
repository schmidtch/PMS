package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utilities.DBConnectionHandler;
import model.Document;

public class DocumentController {

	public Document insertDocument(Document d) throws SQLException{
		PreparedStatement stmt = DBConnectionHandler.getConnection().prepareStatement("insert into Documents (title, comment, type, caseno) values ('"+d.getTitle()+"', '"+d.getComment()+"', '"+d.getType()+"', "+String.valueOf(d.getCaseno())+")", Statement.RETURN_GENERATED_KEYS);
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		while(rs.next()){
			d.setDocid(rs.getInt(1));
		}
		rs.close();
		stmt.close();
		DBConnectionHandler.closeConnection();
		return d;
	}
	
	public void updateDocument(Document d) throws SQLException{
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		stmt.executeUpdate("update Documents set comment='"+d.getComment()+"' where docid="+String.valueOf(d.getDocid()));
	}
	
	public int findDocument(Document d) throws SQLException {
		int found = 0;
		String title = d.getTitle().substring(0, d.getTitle().indexOf("-"));
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select docid from Documents where title like '"+title+"%'");
		if(rs.next()){
			found = Integer.parseInt(rs.getString("docid"));
		}
		rs.close();
		stmt.close();
		DBConnectionHandler.closeConnection();
		return found;
	}
	
	public Document getDocumentForCase(int caseno) throws SQLException {
		Document d = new Document("noDocument", caseno);
		Statement stmt = DBConnectionHandler.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("select title, type from Documents where caseno="+String.valueOf(caseno) + " and type='Honorarnote'");
		while (rs.next()){
			d.setTitle(rs.getString("title"));
			d.setType(rs.getString("type"));
		}
		rs.close();
		stmt.close();
		DBConnectionHandler.closeConnection();
		return d;
	}
	
	
}
