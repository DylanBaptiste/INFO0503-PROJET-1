package clientjava;

import java.util.*;
import java.util.function.BiConsumer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sondage {
	private String administrateur;
	private String titre;
    Set<Question> sondageData = new HashSet<Question>();
    
    public Sondage(String administrateur,String titre) {
    	this.administrateur = administrateur;
        this.titre = titre;
    }
    
    public void ajouterQuestion(Question question) {
    	sondageData.add(question);
    }

    public int size() {
    	return sondageData.size();
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
            .put("login", this.administrateur)
            .put("titre", this.titre)
            .put("sondageData", tmpobj );
    }

    @Override
	public String toString() {
        int i = 0;
        String str = this.titre+":\n";
        for (Question question : this.sondageData) {
            str += "\n"+(++i)+"- "+question.toString();
        }
        return str;
	}
    
}