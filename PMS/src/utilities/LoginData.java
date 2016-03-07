package utilities;

public class LoginData {
	
	private static String PASSWORD = "b9dd67f30cb5161295a3367a0058cb33";
	private static String USERNAME = "admin";
	private static LoginData instance = null;
	
	private LoginData(){}
	
	public static LoginData getInstance(){
		if(instance==null){
			instance = new LoginData();
		}
		return instance;
	}
	
	public boolean checkLogin(String username, String password){
		if(username.equals(USERNAME) && password.equals(PASSWORD)){
			return true;
		}
		return false;
	}
	
}
