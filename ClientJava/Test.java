package clientjava;

import java.util.Scanner;

import javax.naming.ldap.ManageReferralControl;

import java.awt.Color;

import org.json.*;

//import java.util.Scanner;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.BufferedReader;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.URLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.net.MalformedURLException;



public class Test {

	private static String getKey(JSONObject json, String key){
		return json.has(key) ? json.optString(key) : "";
	}

	private static String manageResult(String resultRequest){
		try{
			JSONObject jsonRequest = new JSONObject(resultRequest);
			String success = getKey(jsonRequest, "success");
			String error   = getKey(jsonRequest, "error");
			
			if(success != ""){
				resultRequest = "\033[32m"+ success;
			}else{
				resultRequest = "\033[31m"+ error;
			}
		}catch(JSONException e){
			resultRequest += "\nimpossible de parser la reponse";
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
<<<<<<< HEAD:ClientJava/Test.java
						client.setId(getKey(jsonRequest, "id"));
=======
						String success = getKey(jsonRequest, "success");//"";//jsonRequest.getString("success");
						String error   = getKey(jsonRequest, "error");
						
						if(success != ""){
							resultRequest = "Requete reussi: "+ success;
							client.setId(getKey(jsonRequest, "id"));
						}else{
							resultRequest = "Une erreur est survenu: " + error;
						}
					}catch(JSONException e){
						resultRequest += "\nimpossible de parser la reponse";
>>>>>>> f89e2b4209a4bf86ed84181b4ef3fc19a1f06afe:ClientJAVA/src/com/test/Test.java
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
				{					System.out.println("Bienvenue sur l'interfaxe de Creation du Sondage ");
					System.out.println("Veuillez donner un titre à votre sondage :");
					//String RecupTitreSondage = saisieUtilisateur.nextLine();
					
					System.out.print("Combien avez-vous de question ?");
					String RecupNbQuestion = saisieUtilisateur.nextLine();
					int recupNbQuestion = Integer.parseInt(RecupNbQuestion);
					String RecupQuestion = ("");	
					String RecupReponse =("");
					for (int i =1 ; i <= recupNbQuestion; i++) 
					{
						System.out.println("Entrez la question "+i + " n'oubliez pas le ? : ");
						RecupQuestion =RecupQuestion + saisieUtilisateur.nextLine();
						System.out.println(" Veullez entrez les reponses pour cette question en les separants par un @");
						RecupReponse = RecupReponse +("|") + saisieUtilisateur.nextLine();
						// On fait une concatenation je sais plus trop le mot pour exploser les question en plusieurs apres grace au ?
					}
					System.out.println(RecupQuestion);
					System.out.println(RecupReponse);
					System.out.print("Bienvenue sur l'interfaxe pour voir Sondage");
					break;
				}
				case "4": 
				{
					System.out.print("Tu casses les couilles");
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
					resultRequest = "\033[32mVous vous êtes déconnecté.";
					break;
				}
				default: 
					resultRequest = "\033[31mCette action n'existe pas";
					break;
			}
		}while( (!recupOption.equals("8")) );

		saisieUtilisateur.close();

	}
}