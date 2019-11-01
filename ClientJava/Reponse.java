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

	//{"administrateur":"login","titre":"titre","sondageData":[{"question 2":{"reponse 4":["toto"],"reponse 3":[]}},{"question 1":{"reponse 2":["toto","titi"],"reponse 1":["titi","tata"]}}]}
	public Reponse(String r, JSONArray jsonArray) {

		this.SetUser = new HashSet<String>();
		this.rep = r;

		for(int i = 0; i< jsonArray.length(); i++){
			this.voter(jsonArray.getString(i));
		}

        /*this.reponses = new HashSet<Reponse>();
        String questionString = json.names().getString(0);
        this.rp = questionString;

        this.ajouterReponse(new Reponse(json.getJSONObject(questionString));
 
        }*/

    	
    }

	public Set<String> getSetUser() {
		return this.SetUser;
	}

	public String getrep() {
		return this.rep;
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
		return this.rep+" => "+this.SetUser.toString();
	}
	
}