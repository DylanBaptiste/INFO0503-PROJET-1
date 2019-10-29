package Serveurauthentification;
import org.json.JSONObject;

public class ClientAuthentification {
	
		private int NumeroClient;
		private String login;
		private String password;

		public ClientAuthentification (int NumeroClient ,String login ,String password ) {
			this.login = login;
			this.NumeroClient = NumeroClient;
			this.password = password;
		}
	
		public int getNumeroClient() {
			return NumeroClient;
		}

		public void setNumeroClient(int numeroClient) {
			NumeroClient = numeroClient;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		

		public JSONObject toJSON() {
			return new JSONObject(this);
		}
		
	    public static ClientAuthentification deserializeClientAuthentification(JSONObject json) {
	    	int NumeroClient = json.getInt("NumeroClient");
	        String password =  json.getString("password");
	        String login = json.getString("login");
	        return new ClientAuthentification(NumeroClient,login,password);
	    }
	}

