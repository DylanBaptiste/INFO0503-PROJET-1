package clientjava;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/*
"question 1" =>
    [ 
        "rep 1 q 1" => ["jean-louis", "jean-jean"],
        "rep 2 q 1" => ["jean-louis", "jean-jean"]
    ]
*/
public class Question {
   public Set<Reponse> reponses = new HashSet<Reponse>();
   public String quest;
	    
	public Question(String quest) {
        this.quest = quest;
    }
    
    public void ajouterReponse(Reponse value) {
        this.reponses.add(value);
    }

    public int size(){
        return this.reponses.size();
    }

    public JSONObject toJSON(){
        JSONObject tmpobj = new JSONObject();
        for (Reponse reponse : reponses) {
            tmpobj.put(reponse.rep, new JSONArray(reponse.SetUser));
        }
        return new JSONObject().put(quest, tmpobj);
    }
   
    /*public static Question deserializeQuestion(JSONObject json) {
        JSONObject array = json.getJSONObject("reponse");
        String quest =  json.getString("question");

        Question question = new Question(quest);
        
        for(int i = 0; i < array.length(); i++){
            question.ajouterReponse(Reponse.deserializeReponse(array.getJSONObject(Integer.toString(i))));
        }
            
        return question;
    }*/

    @Override
	public String toString() {
        int i = 0;
        String str = this.quest;
        for (Reponse reponse : this.reponses) {
            str += "\n\t"+(++i)+"- "+reponse.toString();
        }
        return str;
	}


}