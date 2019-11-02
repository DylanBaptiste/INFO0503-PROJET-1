package clientjava;

import java.util.Scanner;

import org.json.*;



public class App {

	

	private static String getKey(JSONObject json, String key){
		return json.has(key) ? json.optString(key) : "";
	}

	private static String manageResult(String resultRequest){
		try{
			JSONObject jsonRequest = new JSONObject(resultRequest);
			String success = getKey(jsonRequest, "success");
			String error   = getKey(jsonRequest, "error");
			
			if(success != ""){
				resultRequest = "Succes: "+ success;
			}else{
				resultRequest = "Erreur: "+ error;
			}
		}catch(JSONException e){
			resultRequest += "";
		}
		return resultRequest;
	}
	public static void main(String[] args) {	

		Client client = new Client();
		String resultRequest = "";
		String recupOption = "0";

		Scanner saisieUtilisateur = new Scanner(System.in);

		do{

			client.displayMenu(resultRequest);
			resultRequest = "";
			
			System.out.print("Votre choix: ");

			recupOption =  saisieUtilisateur.nextLine();

			
			switch(recupOption){

			
				case "1":
				{
					resultRequest = client.requestLogin(saisieUtilisateur);
					
					try{
						JSONObject jsonRequest = new JSONObject(resultRequest);
						client.setId(getKey(jsonRequest, "id"));
					}
					catch(Exception e){}

					resultRequest = manageResult(resultRequest);
					
					break;
				}
				case "2": {

					resultRequest = client.requestCreate(saisieUtilisateur);

					try{
						JSONObject jsonRequest = new JSONObject(resultRequest);
						client.setId(getKey(jsonRequest, "id"));
					}
					catch(Exception e){}

					resultRequest = manageResult(resultRequest);
					
					break;
				}
		
				case "3":
				{	
					resultRequest = client.requestCreateSondage(saisieUtilisateur);
					resultRequest = manageResult(resultRequest);
					break;
				}
				case "4": 
				{
					String tmpresultRequest = client.requestGetSondageByAdmin(saisieUtilisateur);
					resultRequest = manageResult(tmpresultRequest);

					try{
						JSONObject jsonRequest = new JSONObject(tmpresultRequest);
						if(jsonRequest.has("sondages")){
							for(int i = 0; i < jsonRequest.getJSONArray("sondages").length(); i++){
								resultRequest += "\n\t- "+jsonRequest.getJSONArray("sondages").get(i);
							}
						}
					}catch(JSONException e){}
					break;
				}
				case "5": 
				{
					//voter à un sondage
					String tmpresultRequest = client.requestVoter(saisieUtilisateur);
					resultRequest = manageResult(tmpresultRequest);
					
					break;
				}
				case "6": 
				{
					
					String tmpresultRequest = client.requestVoirVote(saisieUtilisateur);
					resultRequest = manageResult(tmpresultRequest);
					
					break;
				}
				case "8": 
				{
					System.out.print("Salut mon pote");
					break;
				}
				case "9": 
				{
					client.setId("");
					resultRequest = "Vous vous êtes déconnecté.";
					break;
				}
				default: 
					resultRequest = "Cette action n'existe pas";
					break;
			}
		}while( (!recupOption.equals("8")) );

		saisieUtilisateur.close();

	}
}