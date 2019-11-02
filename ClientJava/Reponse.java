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

	public Reponse(String r, JSONArray jsonArray) {

		this.SetUser = new HashSet<String>();
		this.rep = r;

		for(int i = 0; i< jsonArray.length(); i++){
			this.voter(jsonArray.getString(i));
		}
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
	
	
	public String toStringAndVote() {
		return this.rep+" => "+"Nombre de vote(s) : "+this.SetUser.size()+" : "+this.SetUser.toString();
	}

	@Override
	public String toString() {
		return this.rep;
	}
	
}