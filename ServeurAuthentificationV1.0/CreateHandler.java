package Serveurauthentification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CreateHandler implements HttpHandler {

	
	// CREATION DU FICHIER JSON SI INEXISTANT ??
	// JE pense une fonction plus top 
	// DOnc je le fait pas ahahah 
	
	    public void handle(HttpExchange t) throws IOException {
	    	 int i  =1;
	    	//Creation d'un flux afin de lire les données recu en http
	        BufferedReader br = null;
	        try {
	            br = new BufferedReader(new InputStreamReader(t.getRequestBody(),"utf-8"));
	        } catch(UnsupportedEncodingException e) {
	            System.err.println("Erreur lors de la récupération du flux " + e);
	            System.exit(-1);
	        }
	 
	        // Récupération des données en POST
	        String query = null;
	        try {
	            query = br.readLine();
	        } catch(IOException e) {
	            System.err.println("Erreur lors de la lecture d'une ligne " + e);
	            System.exit(-1);
	        }
	        
	        
	        // Recuperation des variables recu par le post
	        String loginU = "";
	        String passwordU =  "";
	        JSONObject receptionUtilisateur = new JSONObject(query);
	        if(receptionUtilisateur.has("login")) {
	        	loginU = receptionUtilisateur.getString("login");
	        }
	        if(receptionUtilisateur.has("password")) {
	        	passwordU = receptionUtilisateur.getString("password");
	        }
	        
	        //if ((receptionUtilisateur.has("login"))||(receptionUtilisateur.has("password"))){
	        //Creation d'un objet Utilisateur avec en ID = 0
	        //ClientAuthentification client = new ClientAuthentification(9999,loginU,passwordU);
	    	//System.out.println(client);
	    	//JSONObject jsonClient = client.toJSON(); 
	    	
	    	//Ouverture Du fichier JSON
	    	
	    	File f;
	    	BufferedReader lecteurAvecBuffer = null;
	        String ligne;
	        int IDclient =0;
	        try{lecteurAvecBuffer = new BufferedReader(new FileReader("Utilisateur.son")); }
	        catch(FileNotFoundException exc){System.out.println("Erreur d'ouverture");}
	    	
	        while ((ligne = lecteurAvecBuffer.readLine()) != null) {
	        	
	        	//TODO La comparaison jamais elel marche gniahhhhgniaahhh
	        	JSONObject tmp = new JSONObject (ligne);
	        	 if ((loginU != null)||(passwordU != null)||(loginU.equals(tmp.get("login"))) || (passwordU.equals(tmp.get("password")))) {
	        		 IDclient = tmp.getInt("IDClient");
	        		 
	        	 } 
	        } 
	        
	        //Renvoie de la requete
	        ClientAuthentification client = new ClientAuthentification(IDclient,loginU,passwordU);
	        JSONObject jsonClient = client.toJSON();
	    	String data = ("json=" + new JSONObject().put("data", jsonClient).toString());
	        // Envoi de l'en-tête Http
	        try {
	            Headers h = t.getResponseHeaders();
	            h.set("Content-Type", "text/html; charset=utf-8");
	            t.sendResponseHeaders(200, data.getBytes().length);
	        } catch(IOException e) {
	            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
	            System.exit(-1);
	        }

	        // Envoi du corps (données HTML)
	        try {
	            OutputStream os = t.getResponseBody();
	            os.write(data.getBytes());
	            os.close();
	        } catch(IOException e) {
	            System.err.println("Erreur lors de l'envoi du corps : " + e);
	        }

	 
	        }
	    }