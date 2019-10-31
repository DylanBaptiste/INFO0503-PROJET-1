package packageClientJava;

import org.json.JSONObject;

public class Reponse {
	private String reponse;
	//tab de reponse plutot mais tql cazlme toi

	public Reponse(String reponse) {
		this.reponse = reponse;
	}
	
	@Override
	public String toString() {
		return "Reponse [reponse=" + reponse + "]";
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	public JSONObject toJSON() {
		return new JSONObject(this);
	}
    public static Reponse deserializeReponse(JSONObject json) {
        String reponse =  json.getString("dreponse");

        return new Reponse(reponse);
    }
	
}