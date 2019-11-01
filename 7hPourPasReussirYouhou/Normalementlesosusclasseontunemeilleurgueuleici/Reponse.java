package testdetout;


import org.json.JSONObject;


public class Reponse {

    private String reponse;


    public Reponse(String reponse) {
        this.reponse = reponse;
        
    }
    public String getReponse() {
        return reponse;
    }

    public String toString() {
        return reponse;
    }

    public JSONObject toJSON(){
        return new JSONObject(this);
    }
    
    public static Reponse deserializeReponse(JSONObject json) {
        String reponse = json.getString("reponse");
        return new Reponse(reponse);
    }
}