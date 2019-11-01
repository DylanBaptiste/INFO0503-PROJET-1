package ezfvnoezfVNCSDQLN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

public class TEST {

	public static void main(String[] args) {
		HashMap<String, String> reponses = new HashMap<String, String>();
		HashMap<String, Object> responseObj = new HashMap<String, Object>();
		List<User> userList = new ArrayList<User>();
		//List<pute> puteList = new ArrayList<pute>();

		JSONObject obj = new JSONObject(responseObj);
	

		String question = "";
		Scanner saisieUtilisateur = new Scanner(System.in);
		String currentReponse = "";
		int MAX_Q = 10;
		int MAX_R = 5;
		String quitCode = "q";
		String abortCode = "a";
		boolean isAbort = false;
		
		System.out.print("Titre du sondage: ");
		String RecupTitle = saisieUtilisateur.nextLine();
		while(true){

			System.out.print("QUESTION n"+quitCode+": quitter, "+abortCode+": avorter): ");
			
			
			question = saisieUtilisateur.nextLine();
			if(!question.equals(quitCode) && !question.equals(abortCode) && responseObj.size() < MAX_Q){
				while(true){

					System.out.print("("+question+") REPONSE n"+reponses.size()+" ("+quitCode+": quitter, "+abortCode+": avorter): ");

					currentReponse = saisieUtilisateur.nextLine();
					 if (!currentReponse.equals(quitCode)) {userList.add(new User(question,currentReponse));}
					 
					 /*if (!currentReponse.equals(quitCode)) {
						 TODO Il faudrait reussir a vider le putelist afin que les reponse des question d'apres ne serajoute pas au premier ou de l'add dans lobjet direct je sais pas tropp
						 userList.add(new User(currentReponse));
						 puteList.add(new pute(question,userList));}
					*/
					if( !currentReponse.equals(quitCode) && reponses.size() <= MAX_R){
						//verification que currentReponse est unique ?
						reponses.put(currentReponse, null );
					}else{
						
						if(currentReponse.equals(quitCode)){
							System.out.println("T'as quitté");
							break;
						}
						if(currentReponse.equals(abortCode)){
							System.out.println("Vous avez annulé");
							isAbort = true;
							break;
						}
						if(reponses.size() < 2){
							System.out.println("Vous devez ajouter au moins 2 reponses par question => "+question+" est supprimé de ce sondage");
							
							responseObj.remove(question);
						}
						if(reponses.size() >= MAX_R){
							System.out.println("MAX REPONSES");
							break;
						}
					}
				}
				/// TEST ///


				responseObj.put(question, reponses);
				
				System.out.println(reponses);
				reponses = new HashMap<String, String>();
				
				
				//////////
			}
			else{
				if(responseObj.size() == 0){
					System.out.println("Aucun sondage créé");
					isAbort = true;
				}
				if(currentReponse.equals(abortCode)){
					System.out.println("Vous avez annulé");
					isAbort = true;
				}
				break;
			}
		}

		System.out.println("Sondage.sondageData: "+responseObj);
		
		
		int isValid = -1;
		String valid = "";

		while(isValid == -1 && !isAbort){
			System.out.print("\nValider ce sondage ? (y/n) ");
			valid = saisieUtilisateur.nextLine();
			switch(valid){
				case "y": case "yes": case "oui": isValid = 1;
					break;
				case "n": case "no": case "non": isValid = 0;
					break;
				default:
					break;
			}
		}
		HashMap<String, Object> responseObj2 = new HashMap<String, Object>();
		System.out.println(userList);
		responseObj2.put("Titre", RecupTitle); //1
		responseObj2.put("Question", userList);
		//responseObj2.put("Question", puteList);
		JSONObject obj3 = new JSONObject(responseObj2);
		System.out.println(obj3);
		;
		
	}
}

