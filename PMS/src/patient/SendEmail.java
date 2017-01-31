package patient;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

@Path("/sendEmail")
public class SendEmail {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String sendEmail(String in) {
		JSONObject jo = new JSONObject(in);
		JSONObject out = new JSONObject();
		
		String to = jo.getString("to");
		String file = jo.getString("file");
		String from = "christopher.schmidt@broken-glass.at";
	    
		String host = "mail.infotek.at";
	    Properties p = new Properties();
	    p.put("mail.smtp.auth", "true");
	    p.put("mail.smtp.host", host);
	    p.put("mail.smtp.port", "25");

	    Session session = Session.getDefaultInstance(p, new Authenticator() {
	      						protected PasswordAuthentication getPasswordAuthentication() {
	      							return new PasswordAuthentication("christopher.schmidt@broken-glass.at", "brokenglass");
	      						}
	      					});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));  // hopefully you're putting a real value here
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));  // and here
			message.setSubject("TCM-Planegger Honorarnote");
			
			MimeBodyPart attachment = new MimeBodyPart();
			Multipart mp1 = new MimeMultipart();
			attachment.attachFile("/Volumes/Daten/Webserver/apache-tomcat-8.0.32/webapps/PMS_Content/"+file);
			mp1.addBodyPart(attachment);
			message.setContent(mp1);
			Transport.send(message);
			out.put("state", "success");
		} catch (MessagingException e){
			out.put("state", "error");
			out.put("msg", e.getMessage());
			throw new RuntimeException(e);
		} catch (IOException e) {
			out.put("state", "error");
			out.put("msg", e.getMessage());
			e.printStackTrace();
		}
	
		return out.toString();
	}
	
	
}
