package clientjava;

import java.util.HashMap;

import org.json.JSONObject;

public class Sondage {
	private String IDClient;
	private String Titre;
    private static int compteur = 0;
    private HashMap<Integer,Question> question;

    
    public Sondage(String IDClient,String Titre) {
    	this.IDClient = IDClient;
    	this.Titre = Titre;
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
        objet.put("question", this.question);
        return objet;
    }
    
}