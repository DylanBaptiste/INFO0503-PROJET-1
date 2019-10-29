package com.test;


import java.util.Scanner;

import org.json.JSONObject;

//import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URL;
//import java.net.URLEncoder;
import java.net.MalformedURLException;



public class Client {

	private String URL_LOGIN = "http://localhost/INFO0503-PROJET-1/ServeurPHP/login.php";
	private static int LOGIN_ACTION = 1;

	private String login = "";
	private String password = "";

	public Client () {
		this.login = "";
		this.password = "";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    public String toString() {
        return " login : " + login + " mdp :" + password ;
    }
	
	public JSONObject toJSON() {
		return new JSONObject(this);
	}
	
   	/*public static Client deserializeClient(JSONObject json) {
        String password =  json.getString("password");
        String login = json.getString("login");
        return new Client(login,password);
	}*/

	/**
	 * Genere une requqte de login
	 * @return une String de la réponse du serveur
	 */
	public String requestLogin(Scanner saisieUtilisateur){

		//System.out.print("\033[H\033[2J");  
		System.out.flush();
		System.out.flush();

		System.out.print("login: ");
		String RecupLogin = saisieUtilisateur.nextLine();
		System.out.print("mot de passe: ");
		String RecupPassword = saisieUtilisateur.nextLine();


		JSONObject data = new JSONObject()
			.put("action", LOGIN_ACTION)
			.put("login", RecupLogin)
			.put("password", RecupPassword);

		return makeRequest(data);
		
	}

	public String requestSeePoll(String userID){

		JSONObject data = new JSONObject()
			.put("action", 4)
			.put("userID", userID);

		return makeRequest(data);
		
	}

	/**
	 * Procedure qui affiche le resultat de la derniere requete et le menu en fonction de l'etat du client
	 * @param lastResultRequest
	 */
	public void displayMenu(String lastResultRequest){
			System.out.flush();
			//System.out.println("\033[H\033[2J");  
			System.out.flush();

			System.out.println(lastResultRequest);
			System.out.println("\n---------OPTIONS----------");
			System.out.println( this.login == "" ?  "| ("+LOGIN_ACTION+") login	 |" : "| Vous etes connecté	|");
			System.out.println("| (1)  Repondre Sondage   |");
			System.out.println("| (2)  Creer Sondage      |");
			System.out.println("| (3)  Voir le nom de ses Sondages       |");
			System.out.println("| (4) Voir Reponse Sondage|");
			System.out.println("| (6) quitter			  |");
			System.out.println(" -------------------------");

	}

	/**
	 * effectu une requete vers le serveur php
	 * @return une String de la reponse recus
	 */
	private String makeRequest(JSONObject data){
		
		URL url = null;
		try { 
			url = new URL(URL_LOGIN); 
		} catch(MalformedURLException e) { 
			return ("URL incorrect : " + e);
		}

		URLConnection connexion = null; 
		try { 
			connexion = url.openConnection(); 
			connexion.setDoOutput(true);
		} catch(IOException e) { 
			return("Connexion impossible : " + e);
		}

		try {
			OutputStreamWriter writer = new OutputStreamWriter(connexion.getOutputStream());
			writer.write(data.toString());
			writer.flush();
			writer.close();
		} catch(IOException e) {
			return ("Erreur lors de l'envoi de la requete : " + e);        
		}

		String response = ""; 
		try { 
			BufferedReader reader = new BufferedReader(new InputStreamReader( connexion.getInputStream())); 
			String tmp; 
			while((tmp = reader.readLine()) != null) 
				response += tmp; 
			reader.close(); 
		} catch(Exception e) { 
			return ("Erreur lors de la lecture de la réponse : " + e);
		}
		
		return response;
	}
	
	
}
