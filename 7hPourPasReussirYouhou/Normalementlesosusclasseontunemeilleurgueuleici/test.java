package testdetout;


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



public class test {

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


