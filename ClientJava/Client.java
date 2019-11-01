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
		

		/*
		sondageData =
		[
			"question 1" =>
			[ 
				"rep 1 q 1" => ["jean-louis", "jean-jean"],
				"rep 2 q 1" => ["jean-louis", "jean-jean"]
			],
			"question 2" =>
			[
				"rep 1 q 2" => ["jean-louis", "jean-jean"],
				"rep 2 q 2" => ["jean-louis", "jean-jean", "toto"],
				"rep 3 q 2" => ["jean-louis", "jean-jean", "toto"]
			]
		]
		*/

		//On peux utiliser HashMap à la place de Pair pour forcer le fait d'avoir des questions et reponses unique
		//Pair<String, Pair<String, String[]>> sondageData = new Pair<String, Pair<String, String[]>>();
		
		//Pair marche pas....
		/*HashMap<String, HashMap<String, Set<String>>> sondageData = new HashMap<String, HashMap<String, Set<String>>>();
		String question = "";
		HashMap<String, Set<String>> reponses = new HashMap<String, Set<String>>();
		String currentReponse = "";
		int MAX_Q = 10;
		int MAX_R = 5;
		String quitCode = "q";
		String abortCode = "a";
		boolean isAbort = false;

		System.out.print("Titre du sondage: ");
		String RecupTitle = saisieUtilisateur.nextLine();

		while(true){

			System.out.print("QUESTION n"+sondageData.size()+" ("+quitCode+": quitter, "+abortCode+": avorter): ");

			question = saisieUtilisateur.nextLine();
			if(!question.equals(quitCode) && !question.equals(abortCode) && sondageData.size() < MAX_Q){
				while(true){

					System.out.print("("+question+") REPONSE n"+reponses.size()+" ("+quitCode+": quitter, "+abortCode+": avorter): ");

					currentReponse = saisieUtilisateur.nextLine();
					
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
							sondageData.remove(question);
						}
						if(reponses.size() >= MAX_R){
							System.out.println("MAX REPONSES");
							break;
						}
					}
				}
				sondageData.put(question, reponses);
				reponses = new HashMap<String, Set<String>>();

			}
			else{
				if(sondageData.size() == 0){
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

		System.out.println("Sondage.sondageData: "+sondageData.toString());
		
		
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

		//créé l'objet Sondage
		Sondage s = new Sondage(this.id, RecupTitle, null );
		System.out.println(s.toJSON());

		if(isValid == 1){
			JSONObject data = new JSONObject()
			.put("action", CREATESONDAGE_ACTION)
			.put("id", this.id)
			//.put("sondage", s.toJSON)
			;
			
			return makeRequest(data);
		}else{
			return "Création annulé";
		}*/
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
		
		System.out.println('\n'+lastResultRequest+"\033[0m");
		
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
