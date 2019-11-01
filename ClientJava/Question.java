package clientjava;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

    //"{\"administrateur\":\"login\",\"titre\":\"titre\",\"sondageData\":[{\"question 2\":{\"reponse 4\":[\"1\"],\"reponse 3\":[]}},{\"question 1\":{\"reponse 2\":[\"2\",\"3\"],\"reponse 1\":[\"4\",\"5\"]}}]}"
    public Question(JSONObject json) {

        this.reponses = new HashSet<Reponse>();
        String questionString = json.names().getString(0);
        this.quest = questionString;

        String reponseStr = "";
        for(int i = 0; i < json.getJSONObject(questionString).length(); i++ ){
            reponseStr = json.getJSONObject(questionString).names().getString(i);
            this.ajouterReponse(new Reponse(reponseStr, json.getJSONObject(questionString).getJSONArray(reponseStr)));
        }
        
	
    }

    public  Set<Reponse> getReponses(){
        return this.reponses;
    }

    public void voter(int i, String login){
        Reponse[] r = this.reponses.toArray(new Reponse[this.reponses.size()]);
        r[i].voter(login);
        this.reponses = new HashSet<Reponse>(Arrays.asList(r));
    }

    public  String getQuestion(){
        return this.quest;
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