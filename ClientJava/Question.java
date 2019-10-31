package clientjava;

import java.util.HashMap;

import org.json.JSONObject;

public class Question {
		 
	    private static int compteur = 0;// ??
	    private String quest;
	    private HashMap<Integer,Reponse> reponse;
	    
	public Question(String quest) {
		 reponse = new HashMap<Integer,Reponse>();
		 this.quest = quest;
	}
    public int ajouterReponse(Reponse r) {
        reponse.put(compteur, r);
        return compteur++;
    }
   public Reponse getLivre(int i) {
        Reponse resultat = null;
        
        if(reponse.containsKey(i)) {
            resultat = reponse.get(i);
        }
        
        return resultat;
    }
   
   // gneugneu c faux la 
   public JSONObject toJSON(){
       JSONObject objet = new JSONObject();
       objet.put("quest", quest);
       objet.put("reponse", this.reponse);
       return objet;
   }
   
   public static Question deserializeQuestion(JSONObject json) {
       JSONObject array = json.getJSONObject("reponse");
       String quest =  json.getString("question");

       Question question = new Question(quest);
       
       for(int i = 0; i < array.length(); i++){
           question.ajouterReponse(Reponse.deserializeReponse(array.getJSONObject(Integer.toString(i))));
       }
           
       return question;
   }

   
   
   public static int getCompteur() {
	return compteur;
}
   public static void setCompteur(int compteur) {
	Question.compteur = compteur;
}
   public String getQuestion() {
	return quest;
}
   public void setQuestion(String quest) {
	this.quest = quest;
}
   public HashMap<Integer, Reponse> getReponse() {
	return reponse;
}
   public void setReponse(HashMap<Integer, Reponse> reponse) {
	this.reponse = reponse;
}


}