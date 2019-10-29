package com.test;

import java.util.Scanner;

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

	public static String getKey(JSONObject json, String key){
		return json.has(key) ? json.optString(key) : "";
	}

	


	public static void main(String[] args) {	

		Client client = new Client();
		String resultRequest = "";
		int recupOption = 0;

		Scanner saisieUtilisateur = new Scanner(System.in);

		do{

			client.displayMenu(resultRequest);
			System.out.println();
			System.out.println("Votre choix: ");

			recupOption = Integer.parseInt( saisieUtilisateur.nextLine() );

			
			switch(recupOption){

			
				case 1:
				{
					resultRequest = client.requestLogin(saisieUtilisateur);
					/*
					serealiser resultRequest
					effectuer les action en conséquence de la reponse
					*/
					/*
					JSONO
					*/
					System.out.println(resultRequest);
					try{
						JSONObject jsonRequest = new JSONObject(resultRequest);
						String success = getKey(jsonRequest, "success");//"";//jsonRequest.getString("success");
						String error   = getKey(jsonRequest, "error");
						
						if(success != ""){
							resultRequest = "Requete reussi: "+ success;
							//client.setLogin(login); <- dans la fonction requestLogin ?
						}else{
							resultRequest = "Une erreur est survenu: " + error;
						}
					}catch(JSONException e){
						resultRequest += "\nimpossible de parser la reponse";
					}
					
					

					break;
				}
				case 2: 
				{
					System.out.println("Bienvenue sur l'interfaxe de Creation du Sondage ");
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
					break;
					
				}
				case 3:
				{
					System.out.print("Bienvenue sur l'interfaxe pour voir Sondage");
					break;
				}
				case 4: 
				{
					System.out.print("Tu casses les couilles");
					break;
				}
				case 6: 
				{
					System.out.print("Salut mon pote");
					break;
				}
				default: 
					System.out.print("Cette action n'existe pas");
					break;
			}
		}while(recupOption != 6);

		saisieUtilisateur.close();

	}
}