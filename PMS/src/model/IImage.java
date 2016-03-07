package model;

import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;


public class IImage {

	private int id;
	private String name;
	private String description;
	private String img;
	private File imgFile;
	private byte[] imgBytes;
	private InputStream is;
	
	public IImage(){}
	
	public IImage(String name, String description, String img){
		this.name=name;
		this.description=description;
		this.img=img;
		this.setImgBytes(img.getBytes());
	}
	
	public IImage(InputStream is){
		this.setIs(is);
	}
	
	public IImage(String image) {
		this.setImg(image);
	}
	
	public IImage(ResultSet rs) throws NumberFormatException, SQLException{
		this.setId(Integer.parseInt(rs.getString("imageid")));
		this.setName(rs.getString("name"));
		this.setDescription(rs.getString("description"));
		Blob b = rs.getBlob("image");
		this.setImg(new String(b.getBytes(1l, (int) b.length())));
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public byte[] getImgBytes() {
		return imgBytes;
	}

	public void setImgBytes(byte[] imgBytes) {
		this.imgBytes = imgBytes;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}
	
	
	
	
	
}
