package clientjava;

import java.util.*;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.net.URL;
import java.net.MalformedURLException;



public class Client {

	private String URL_API;
	private final int LOGIN_ACTION			= 1;
	private final int CREATE_ACTION			= 2;
	private final int CREATESONDAGE_ACTION	= 3;

	private String id = "";

	public Client() {
		String jsonStr = "";
		try{
			File fichier = new File("config.json");
			
			if(!fichier.exists()){
				fichier.createNewFile();
			}
			
			BufferedReader br = new BufferedReader(new FileReader(fichier.getAbsoluteFile()));
			String line;
			while ((line = br.readLine()) != null) {
				jsonStr += line;
			}
			br.close();
		}catch(Exception e){
			System.out.println("Impossible de charger le fichier de config");
			System.exit(-1);
		}

		JSONObject json = null;
		try{
			json = new JSONObject(jsonStr);
			if(json.has("URL_API")){
				this.URL_API = json.getString("URL_API");
			}
			
		}catch(Exception e){
			System.out.println("Fichier de config non valide");
			System.exit(-1);
		}

		
	}

	public String getId() { return id; }

	public void setId(String id) { this.id = id; }

	public String toString() { return "Client: "+this.id; }

	public String requestVoter(Scanner saisieUtilisateur){

		if(this.id.equals("")){
			return "Vous n'etes pas connecté";
		}

		System.out.print("Administrateur du sondage: ");
		String admin = saisieUtilisateur.nextLine();

		System.out.print("titre du sondage: ");
		String titre = saisieUtilisateur.nextLine();

		String sondageString = requestGetSondage(admin, titre);
		
		JSONObject json = null;
        try{
            json = new JSONObject(sondageString);
        }
        catch(Exception e){
            return "ce sondage à un probleme";
		}

		if(json.has("error")){
			return json.toString();
		}

		int i = 0;
		int j = 0;
		int input = -1;
		Sondage sondage = new Sondage(json);

		System.out.println(sondage.size());

		for (Question q : sondage.getQuestions() ) {
			//while(input < 0 || input > q.size() ){
				System.out.println("("+(i++)+") - "+q.getQuestion());
				for (Reponse r : q.getReponses() ) {
					System.out.println("\t("+(j++)+") - "+r.getrep());
				}
				j = 0;
				System.out.println("Vote: ");
				input = Integer.parseInt(saisieUtilisateur.nextLine());
				if(input >= 0 && input <= q.size() ){
					q.voter(input, this.id);
				}
			//}
		}
		
		JSONObject data = sondage.toJSON().put("action", 5);
		return makeRequest(data);
	}

	private String requestGetSondage(String admin, String titre){
		

		JSONObject data = new JSONObject()
			.put("action", 10)
			.put("admin", admin)
			.put("titre", titre)
			;
		return makeRequest(data);
	}

	public String requestVoirVote(Scanner saisieUtilisateur){

		if(this.id.equals("")){
			return "Vous n'etes pas connecté";
		}

		System.out.print("Nom du sondage: ");
		String titre = saisieUtilisateur.nextLine();

		String sondageString = requestGetSondage(this.id, titre);
		
		JSONObject json = null;
        try{
            json = new JSONObject(sondageString);
        }
        catch(Exception e){
            return "ce sondage à un probleme";
		}

		if(json.has("error")){
			return json.toString();
		}
		Sondage sondage = new Sondage(json);

		return sondage.toStringAndVote();


	}
	
	/**
	 * Genre une requete pour créé un sondage
	 * @param saisieUtilisateur
	 * @return la réponse du serveur PHP
	 */
	public String requestCreateSondage(Scanner saisieUtilisateur){

		int MAX_Q = 10;
		int MAX_R = 10;
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

		while(isValid == -1 && !isAbort){
			System.out.print("\nValider ce sondage (y/n) ? ");
			switch(saisieUtilisateur.nextLine()){
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

		System.out.print( this.id != "" ?  "\n| (3) Créer un sondage" : "");
		System.out.print("\n| (4) Voir les sondages d'un admin");
		System.out.print( this.id != "" ?  "\n| (5) Voter" : "");
		System.out.print( this.id != "" ?  "\n| (6) Voir le nombre de vote(s) sur un de ses sondage" : "");
		System.out.print( this.id != "" ?  "\n| (9) Se déconnecter" : "");
		System.out.print("\n| (8) quitter");  
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
