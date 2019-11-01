package clientjava;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 "rep 1 q 1" => ["jean-louis", "jean-jean"]
*/
public class Reponse {
	public String rep;
	public Set<String> SetUser = new HashSet<String>();

	public Reponse(String str) {
		this.rep = str;
	}

	public Set<String> getReponse() {
		return this.SetUser;
	}

	public void voter(String login) {
		this.SetUser.add(login);
	}

	public JSONObject toJSON() {
		return new JSONObject().put(this.rep, new JSONArray(this.SetUser));
	}
	
	/*public static Reponse deserializeReponse(JSONObject json) {
        String reponse =  json.getString("dreponse");

        return new Reponse(reponse);
	}*/

	@Override
	public String toString() {
		return this.rep;//+" => "+this.SetUser.toString();
	}
	
}