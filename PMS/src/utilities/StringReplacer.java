package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
/**
 * Klasse StringReplacer wird zum ersetzen von gewissen Platzhalten in einem HTML-File mit html Code bzw. Ergebnislisten und dgl. verwendet.
 * @author schmidtc
 *
 */
public class StringReplacer {
	
	private static String NL = System.getProperty("line.separator");
	
	private Properties replacements = null;
		
	public void setReplacements(Properties r) {
		this.replacements = r;
	}
	
	
	/** Replaces in the given string s all keywords by their substitutes.	 */
	private String replaceKeywords(String s) {
		if (replacements != null) {
		   Enumeration<?> e = replacements.propertyNames();
		   String      key;
		   while (e.hasMoreElements()) {
			   key = (String)e.nextElement();
				s = s.replace(key, replacements.getProperty(key));		   
		   }
		}
		return s;
	}
	
	
	/** Replaces all replacements in the given file. */
	public String replaceInFile(String filename) {
		String s = "";
		String line;
		BufferedReader r;
		
		try {
			r = new BufferedReader(new FileReader(filename));
			while ((line = r.readLine()) != null) {
				s += replaceKeywords(line) +NL;
			}
			r.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			s = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			s = e.getMessage();
		}
	    return s;
	}
	
	public String replaceInString(String s){
		String ret = "";
		ret += replaceKeywords(s);
		return ret;
	}
}
