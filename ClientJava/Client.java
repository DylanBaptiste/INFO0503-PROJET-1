package clientjava;


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
	private String URL_CREATE = "http://localhost/INFO0503-PROJET-1/ServeurPHP/create.php";
	private static int LOGIN_ACTION = 1;
	private static int CREATE_ACTION =2;

	private String id = "";

	public Client () { }

	public String getId() { return id; }

	public void setId(String id) { this.id = id; }

    public String toString() { return "Client: "+this.id; }

	/**
	 * Genere une requqte de login
	 * @return une String de la réponse du serveur
	 */
	public String requestLogin(Scanner saisieUtilisateur){

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
	
	public String requestCreate(Scanner saisieUtilisateur){

		System.out.print("login: ");
		String RecupLogin = saisieUtilisateur.nextLine();
		System.out.print("mot de passe: ");
		String RecupPassword = saisieUtilisateur.nextLine();
		System.out.print("mot de passe de confirmation: ");
		String confirmPassword = saisieUtilisateur.nextLine();

		JSONObject data = new JSONObject()
			.put("action", CREATE_ACTION)
			.put("login", RecupLogin)
			.put("password", RecupPassword)
			.put("confirmPassword", confirmPassword);
		System.out.println(data);
	
		return makeRequestCreate(data);	
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
			
			
			
			System.out.println('\n'+lastResultRequest+"\033[0m");
			System.out.print("\n----======= MENU =======-----");
			System.out.print( this.id == "" ?  "\n| ("+LOGIN_ACTION+") login" : "\n| Vous êtes connecté en tant que " +this.id);
			System.out.print( this.id == "" ?  "\n| (2) Créer un compte" : "");
			System.out.print("\n| (X) Creer Sondage");
			System.out.print("\n| (X) Voir le nom de ses Sondages");
			System.out.print("\n| (X) Voir Reponse Sondage");
			System.out.print("\n| (8) quitter");
			System.out.print( this.id != "" ?  "\n| (9) Se déconnecter" : "");
			System.out.print("\n----======= **** =======-----\n\n");  

	}

	private String makeRequestCreate(JSONObject data){
			
			URL url = null;
			try { 
				url = new URL(URL_CREATE); 
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
			
			//TODO trouver pq ça marche pas
			String response = ""; 
			try { 
				BufferedReader reader = new BufferedReader(new InputStreamReader( connexion.getInputStream())); 
				String tmp; 

				while((tmp = reader.readLine()) != null) 
					response += tmp; 
				System.out.print(response);
				reader.close(); 
			} catch(Exception e) { 
				return ("Erreur lors de la lecture (makerequestCreate dans client) : " + e);
			}
			
			return response;
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
