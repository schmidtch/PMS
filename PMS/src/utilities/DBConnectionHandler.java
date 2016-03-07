package utilities;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.*;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionHandler {

	private static DBConnectionHandler instance = null;
	private static Connection con = null;
	private static InitialContext context = null;
	private static DataSource ds = null;
	//private static String debug = "";
	
	private DBConnectionHandler(){
		try {
			if (con == null) { con = ds.getConnection(); /*debug = "NEW CONNECTION MADE";*/ }
		} catch (SQLException e) {
			System.err.println("ERROR WITH SQL OR CONNECTION TO DB " + e.getMessage());
		}
	}
	
	public static Connection getConnection() throws SQLException{
		//debug = "OLD CONNECTION USED";
		if (instance == null) {
			try {
				context = new InitialContext();
				Context envContext = (Context)context.lookup("java:/comp/env");
				ds = (DataSource) envContext.lookup("jdbc/pms");
			} catch (NamingException e) {
				System.err.println("ERROR WITH PATH, CANNOT LOCATE THE DATASOURCE " + e.getMessage());
			} 
			
			instance = new DBConnectionHandler();
		} 
		/*System.out.println("\n----con 0---->"+debug + " " +con.toString());
		System.out.println("----con 1---->"+con.getMetaData().getIdentifierQuoteString());
		System.out.println("----con 2---->"+con.getMetaData().getUserName());
		System.out.println("----con 3---->"+con.getMetaData().getURL());
		System.out.println("----con 4---->"+con.getMetaData().getDriverName());*/
		
		return con;
	}
	
	public static void closeConnection() throws SQLException{
		if(con!=null) { con.close(); /*System.out.println("I WILL CLOSE THE CONNECTION\n\n\n");*/ con=null; instance=null; }
	}
	
}
