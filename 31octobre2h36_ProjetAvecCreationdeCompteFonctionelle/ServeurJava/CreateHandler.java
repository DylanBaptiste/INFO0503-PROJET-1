package com.test;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileWriter;
import java.io.InputStreamReader;
import org.json.JSONObject;

class CreateHandler implements HttpHandler {

	private HttpExchange Exchange =  null;

	public void handle(HttpExchange t) throws IOException {

		this.Exchange = t;
		
		String query = "";
		try{
			query = readRequest();
		}
		catch(Exception e){
			System.out.println(e);
			sendError(e.toString());
		}

		
		// Recuperation des variables recu par le post
		String loginU	 = "";
		String passwordU = "";

		JSONObject jsonQuerry  = null;
		try{
			jsonQuerry = new JSONObject(query);
		}catch(Exception e){
			sendError("Les données envoyées ne sont pas au format json");
			return;
		}
		
			// LoginU prend la valeur du login dans le json
		if( jsonQuerry.has("login") ){
			loginU = jsonQuerry.getString("login");
			loginU.replaceAll("[%~/. ]", "");
			}
		else{
			sendError("Aucun login envoyé");
			return;
			}
			// PasswordU prend la valeur du PasswordU dans le json
		if( jsonQuerry.has("password") ){
			passwordU = jsonQuerry.getString("password");
			}
		else{
			sendError("Aucun password envoyé");
			return;
			}
		
		File f = new File(Paths.get(loginU+".json").toString());
		//On verifie si le fichier existe si oui message d'erreur
		if(f.exists()) {
			sendError("Login deja existant");

			}
	     // Creation du fichier et ecriture   
		else {
			JSONObject json = new JSONObject();
			UUID uid = UUID.randomUUID();
			json.put("password", passwordU);
	        json.put("id", uid.toString());
	        
	        // Pour moi les fichiers .json sont cree directement dans le repertoire eclipse ou se situe le code regarde toi dylan
	        File fichier = new File(f.getAbsoluteFile().toString());
	        // Exception si le fichier existe deja ? jamais le cas 
	        try { fichier.createNewFile();
			} catch (IOException e1) { e1.printStackTrace(); }
	        try (FileWriter ecritureFichier = new FileWriter(fichier)){
	        	ecritureFichier.write(json.toString());
	        } catch ( IOException e ) {
	        	 System.err.println("Erreur lors de la creation du fichier : " + e);
		    }

	        sendSuccessCreate("Creation de compte reussi", json.getString("id"));
		}

		}
	


	private String readRequest() throws Exception{

		//Creation d'un flux afin de lire les données recu en http
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(this.Exchange.getRequestBody(),"utf-8"));
		} catch(UnsupportedEncodingException e) {
			throw new Exception("Votre requete est mal formé: "+e);
		}
	
		// Récupération des données en POST
		String query = null;
		try {
			query = br.readLine();
		} catch(IOException e) {
			throw  new Exception("Erreur lors de la lecture d'une ligne: " + e);
		}
		
		return query;
	}
		
	private void sendSuccessCreate(String successMessage, String id){
		sendPostResponse(new JSONObject().put("success", successMessage).put("id", id));
	}

		// Message en cas d'erreur
		private void sendError(String errorMessage){
			sendPostResponse(new JSONObject().put("error", errorMessage));
		}
		

		private void sendPostResponse(JSONObject response){
			
			// Envoi de l'en-tête Http
			try{
				Headers headers = this.Exchange.getResponseHeaders();
				headers.set("Content-Type", "application/json; charset=utf-8");
				this.Exchange.sendResponseHeaders(200, response.toString().getBytes().length);
			}catch(IOException e) {
				System.err.println("Erreur lors de l'envoi de l'en-tête: " + e);
				System.exit(-1);
			}

			// Envoi du corps (données HTML)
			try{
				OutputStream os = this.Exchange.getResponseBody();
				os.write(response.toString().getBytes());
				os.close();
			}catch(IOException e) {
				System.err.println("Erreur lors de l'envoi du corps: " + e);
			}

		}


	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
