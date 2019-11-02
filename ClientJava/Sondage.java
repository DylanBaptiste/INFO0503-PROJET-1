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

    //{"administrateur":"login","titre":"titre","sondageData":[{"question 2":{"reponse 4":["toto"],"reponse 3":[]}},{"question 1":{"reponse 2":["toto","titi"],"reponse 1":["titi","tata"]}}]}
    public Sondage(JSONObject json) {
        
        if( json.has("administrateur") &&  json.has("titre") &&  json.has("sondageData")){
            this.administrateur = json.getString("administrateur");
            this.titre          = json.getString("titre");
            
            
            this.sondageData = new HashSet<Question>();
            JSONArray jsonArray = json.getJSONArray("sondageData");
            if (jsonArray != null) { 
                int len = jsonArray.length();
                for (int i = 0; i < len ; i++){ 
                    this.ajouterQuestion(new Question(new JSONObject(jsonArray.get(i).toString())));
                } 
            }
        }
    	
    }

    
    public void ajouterQuestion(Question question) {
    	sondageData.add(question);
    }

    public int size() {
    	return sondageData.size();
    }

    

    public Set<Question> getQuestions() {
    	return this.sondageData;
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


    public String toStringAndVote(){
        int i = 0;
        String str = this.titre+"("+this.sondageData.size()+" questions):\n";
        for (Question question : this.sondageData) {
            str += "\n"+(++i)+"- "+question.toStringAndVote();
        }
        return str;
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