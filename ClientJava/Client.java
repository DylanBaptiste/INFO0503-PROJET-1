package clientjava;

import java.util.*;

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

	private final String URL_API			= "http://localhost/INFO0503-PROJET-1/ServeurPHP/api.php";
	private final int LOGIN_ACTION			= 1;
	private final int CREATE_ACTION			= 2;
	private final int CREATESONDAGE_ACTION	= 3;

	private String id = "";

	public Client () { }

	public String getId() { return id; }

	public void setId(String id) { this.id = id; }

	public String toString() { return "Client: "+this.id; }
	
	/**
	 * Genre une requete pour créé un sondage
	 * @param saisieUtilisateur
	 * @return la réponse du serveur PHP
	 */
	public String requestCreateSondage(Scanner saisieUtilisateur){

		/*
		Question q1 = new Question("question 1");
		Question q2 = new Question("question 2");
		
		Reponse r1 = new Reponse("reponse 1");
		Reponse r2 = new Reponse("reponse 2");
		Reponse r3 = new Reponse("reponse 3");
		Reponse r4 = new Reponse("reponse 4");

		q1.ajouterReponse(r1);
		q1.ajouterReponse(r2);

		q2.ajouterReponse(r3);
		q2.ajouterReponse(r4);

		r4.voter("toto");
		
		r2.voter("toto");
		r2.voter("titi");

		r1.voter("titi");
		r1.voter("tata");

		Sondage s = new Sondage("login", "titre");
		s.ajouterQuestion(q1);
		s.ajouterQuestion(q2);
		
		System.out.println(r1.toJSON());
		System.out.println(q1.toJSON());

		System.out.println(s.toString());
		System.out.println(s.toJSON());
		
		
		JSONObject data = s.toJSON().put("action", 3); 
		System.out.println(data.toString());
		return makeRequest(data);
		*/

		int MAX_Q = 3;
		int MAX_R = 2;
		String quitCode = "q";
		String abortCode = "a";
		boolean isAbort = false;
		String input = "";

		System.out.print("Titre du sondage: ");
		Sondage sondage = new Sondage(this.id, saisieUtilisateur.nextLine());
		
		Question currentQuestion = null;
		Reponse currentReponse = null;

		while(true){

			System.out.print("- QUESTION n"+sondage.size()+" ("+quitCode+": quitter, "+abortCode+": avorter): ");

			input = saisieUtilisateur.nextLine();
			
			currentQuestion = new Question(input);
			
			if(!input.equals(quitCode) && !input.equals(abortCode) && sondage.size() < MAX_Q){
			
				sondage.ajouterQuestion(currentQuestion);
				
				while(true){

					System.out.print("\t- REPONSE n"+currentQuestion.size()+" ("+quitCode+": quitter, "+abortCode+": avorter): ");

					input = saisieUtilisateur.nextLine();
					
					if( !input.equals(quitCode) && !input.equals(abortCode) && currentQuestion.size() < MAX_R){
						currentReponse = new Reponse(input);
						currentQuestion.ajouterReponse(currentReponse);
					}else{
						
						if(input.equals(quitCode)){
							System.out.println("T'as quitté");
							break;
						}
						if(input.equals(abortCode)){
							System.out.println("Vous avez annulé");
							isAbort = true;
							break;
						}
						//if(currentQuestion.size() < 2){
						//	System.out.println("Vous devez ajouter au moins 2 reponses par question => "+question+" est supprimé de ce sondage");
						//	sondageData.remove(question);
						//}
						if(currentQuestion.size() >= MAX_R){
							System.out.println("MAX REPONSES");
							break;
						}
					}
				}
				sondage.ajouterQuestion(currentQuestion);

			}
			else{
				if(sondage.size() == 0){
					System.out.println("Aucun sondage créé");
					isAbort = true;
				}
				if(input.equals(abortCode)){
					System.out.println("Vous avez annulé");
					isAbort = true;
				}
				break;
			}
		}

		System.out.println("Voici votre sondage: "+sondage.toString());
		
		
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

		if(isValid == 1){
			JSONObject data = sondage.toJSON().put("action", CREATESONDAGE_ACTION);
			return makeRequest(data);
		}else{
			return "Création annulé";
		}
	}

	public String requestGetSondageByAdmin(Scanner saisieUtilisateur){

		System.out.print("Nom de l'administrateur: ");
		String RecupAdmin = saisieUtilisateur.nextLine();

		JSONObject data = new JSONObject()
			.put("action", 4)
			.put("admin", RecupAdmin);

		return makeRequest(data);
	}


	/**
	 * Genere une requqte de login
	 * @return la réponse du serveur d'authentification
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
	
	/**
	 * Genere une requqte de creation de compte
	 * @return la réponse du serveur d'authentification
	 */
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
	
		return makeRequest(data);	
	}

	/**
	 * Procedure qui affiche le resultat de la derniere requete et le menu en fonction de l'etat du client
	 * @param lastResultRequest
	 */
	public void displayMenu(String lastResultRequest){
		
		System.out.println('\n'+lastResultRequest);

		System.out.print("\n----======= MENU =======-----");
		System.out.print( this.id == "" ?  "\n| ("+LOGIN_ACTION+") login" : "\n| Vous êtes connecté en tant que " +this.id);
		System.out.print( this.id == "" ?  "\n| (2) Créer un compte" : "");
		System.out.print("\n| (3) Creer un sondage");
		System.out.print("\n| (4) Voir les sondages d'un admin");
		System.out.print("\n| (X)");
		System.out.print("\n| (8) quitter");
		System.out.print( this.id != "" ?  "\n| (9) Se déconnecter" : "");
		System.out.print("\n----======= **** =======-----\n\n");  

	}
		
	/**
	 * effectu une requete vers le serveur php
	 * @return une String de la reponse recus
	 */
	private String makeRequest(JSONObject data){
		
		URL url = null;
		try { 
			url = new URL(URL_API); 
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
