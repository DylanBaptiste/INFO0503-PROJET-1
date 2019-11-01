package clientjava;

import java.util.*;
import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sondage {
	private String IDClient;
	private String Titre;
    Set<Question> sondageData = new HashSet<Question>();

    
    public Sondage(String IDClient,String Titre) {
    	this.IDClient = IDClient;
        this.Titre = Titre;
    }
    
    public void ajouterQuestion(Question question) {
    	sondageData.add(question);
    }
    
    /*public Question getQuestion(int i) {
        Question resultat = null;
        
        if(question.containsKey(i)) {
            resultat = question.get(i);
        }
        
        return resultat;
    }*/


    public JSONObject toJSON(){

        JSONArray tmpobj = new JSONArray();
        for (Question question : sondageData) {
            tmpobj.put(question.toJSON());
        }


        return new JSONObject()
            .put("login", this.IDClient)
            .put("titre", this.Titre)
            .put("sondageData", tmpobj );
    }

    @Override
	public String toString() {
        String str = "";
        for (Question question : this.sondageData) {
            str += "\n"+question.toString();
        }
        return str;
	}
    
}