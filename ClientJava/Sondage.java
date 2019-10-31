package clientjava;

import java.util.*;
import java.util.function.BiConsumer;

import org.json.JSONException;
import org.json.JSONObject;

public class Sondage {
	private String IDClient;
	private String Titre;
    private static int compteur = 0;
    private HashMap<Integer,Question> question;
    HashMap<String, HashMap<String, Set<String>>> sondageData;

    
    public Sondage(String IDClient,String Titre, HashMap<String, HashMap<String, Set<String>>> sondageData) {
    	this.IDClient = IDClient;
        this.Titre = Titre;
        this.sondageData = sondageData;
    	question = new HashMap<Integer,Question>();
    }
    
    public int ajouterQuestion(Question q) {
    	question.put(compteur,q);
    	return compteur++;
    }
    
    public Question getQuestion(int i) {
        Question resultat = null;
        
        if(question.containsKey(i)) {
            resultat = question.get(i);
        }
        
        return resultat;
    }
    public JSONObject toJSON(){
        JSONObject objet = new JSONObject();
        objet.put("IDClient",this.IDClient);
        objet.put("Titre",this.Titre);
        objet.put("sondageData", getJsonFromMap( this.sondageData ));
        return objet;
    }

    private JSONObject getJsonFromMap(HashMap<String, HashMap<String, Set<String>>> sondage) throws JSONException {
        
        JSONObject jsonData = new JSONObject();
        
        for(String key : sondage.keySet()) {
            
            HashMap<String, Set<String>> value = sondage.get(key);

            for(String questionKey : sondage.keySet()) {
                JSONObject SetReponses = new JSONObject(question);
                for(String reponses : SetReponses) {

                }
                System.out.println(SetReponses.toString());
                jsonData.put(questionKey, SetReponses);
            }
            jsonData.put(key, value);
        }
        return jsonData;
    }
    
}