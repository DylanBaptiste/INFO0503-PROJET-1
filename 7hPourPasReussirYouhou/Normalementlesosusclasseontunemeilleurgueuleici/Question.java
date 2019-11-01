package testdetout;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONObject;


public class Question {
	private static int compteur = 0;
    private String question;
    private HashMap<Integer,Reponse> reponse;
    

    public Question(String question) {
        this.question = question;
        reponse = new HashMap<Integer,Reponse>();
    }
    
    public int ajouterReponse(Reponse reponses) {
        reponse.put(compteur, reponses);
        return compteur++;
    }
    
    /** 
     * Retourne le ième livre.
     * @param i l'indice du livre
     * @return le livre (ou null)
     */
    public Reponse getReponse(int i) {
        Reponse resultat = null;
        
        if(reponse.containsKey(i)) {
            resultat = reponse.get(i);
        }
        
        return resultat;
    }
    
    @Override
    public String toString() {
        return "Question :"+question+"Reponse"+reponse +"}";
    }
        
    public JSONObject toJSON(){
        JSONObject objet = new JSONObject();
        objet.put("question :",this.question);
        objet.put("reponse", this.reponse);
        return objet;
    }
    
    public static Question deserializeQuestion(JSONObject json,String Quest) {
        JSONObject array = json.getJSONObject("reponse");
        
        Question question = new Question(Quest);
        
        for(int i = 0; i < array.length(); i++){
            question.ajouterReponse(Reponse.deserializeReponse(array.getJSONObject(Integer.toString(i))));
        }
            
        return question;
    }

}