package testdetout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;




/**
 * Classe représentant une médiathèque.
 * @author Cyril Rabat
 * @version 05/10/2019
 */
public class Sondage {
	private String IDClient;
    private static int compteur = 0;
    private String Titre;
    private HashMap<Integer,Question> question;
    private HashMap<String, HashMap<String, Set<String>>> sondageData;
    /**
     * Crée une médiathèque.
     * @param sondageData 
     */
    
    public Sondage(String IDClient,String Titre, HashMap<String, HashMap<String, Set<String>>> sondageData) {
    	this.setIDClient(IDClient);
        this.Titre = Titre;
        this.setSondageData(sondageData);
    	question = new HashMap<Integer,Question>();
    }
    
    /**
     * Ajoute un livre à la médiathèque et retourne son identifiant.
     * @param l le livre à ajouter
     * @return l'identifiant du livre ajouté
     */
    public int ajouterQuestion(Question l) {
        question.put(compteur, l);
        return compteur++;
    }
    
    /** 
     * Retourne le ième livre.
     * @param i l'indice du livre
     * @return le livre (ou null)
     */
    public Question getQuestion(int i) {
        Question resultat = null;
        
        if(question.containsKey(i)) {
            resultat = question.get(i);
        }
        
        return resultat;
    }

    @Override
    public String toString() {
        return "\"Titre\":"+ "\""+getTitre()+"\","+ "\""+"sondageData"+ "\":" +  sondageData;
    }
        
    public JSONObject toJSON(){
        JSONObject objet = new JSONObject();
        objet.put("titre :",this.getTitre());
        objet.put("IDClient", this.IDClient);
        objet.put("question", this.question);
        objet.put("sondageDate",  sondageData.values());
        return objet;
    }
    
    public static Sondage deserializeMediatheque(JSONObject json,String Titre,String ID,HashMap<String, HashMap<String, Set<String>>> sondageData) {
        JSONObject array = json.getJSONObject("question");
        
        Sondage mediatheque = new Sondage(Titre, Titre, sondageData);
        
        for(int i = 0; i < array.length(); i++){
            mediatheque.ajouterQuestion(Question.deserializeQuestion(array.getJSONObject(Integer.toString(i)), null));
        }
            
        return mediatheque;
    }

	public String getTitre() {
		return Titre;
	}

	public void setTitre(String titre) {
		Titre = titre;
	}

	public String getIDClient() {
		return IDClient;
	}

	public void setIDClient(String iDClient) {
		IDClient = iDClient;
	}

	public HashMap<String, HashMap<String, Set<String>>> getSondageData() {
		return sondageData;
	}

	public void setSondageData(HashMap<String, HashMap<String, Set<String>>> sondageData) {
		this.sondageData = sondageData;
	}
}