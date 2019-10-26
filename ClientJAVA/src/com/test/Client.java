package com.test;


import java.util.Scanner;

import org.json.JSONObject;

import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;



public class Client {

	private String URL_LOGIN = "http://localhost/INFO0503-PROJET-1/ServeurPHP/login.php";
	private String login;
	private String password;

	public Client (String login ,String password ) {
		this.login = login;
		this.password = password;
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
	
    public static Client deserializeClient(JSONObject json) {
        String password =  json.getString("password");
        String login = json.getString("login");
        return new Client(login,password);
	}
	
	public String requestLogin(String l, String p){
		/*
	{"data": {"login": "toto", "mdp": "toto"}}
	-faire la requete vers http://localhost:80/INFO0503-PROJET-1/login.php en post avec le login et mdp
	-suivant la reponse le client est co ou pas
	-methode de co securisé ou non ?
	*/

	JSONObject data =  new JSONObject();
	data.put("login", l);
	data.put("password", p);
	//JSONObject data = new JSONObject().put("data", new JSONObject().put("login", RecupLogin).put("mdp", RecupPassword));

	// Mise en forme de l'URL
	URL url = null;
	try { 
		url = new URL(URL_LOGIN); 
	} catch(MalformedURLException e) { 
		System.err.println("URL incorrect : " + e);
		System.exit(-1);
	}

	// Etablissement de la connexion
	URLConnection connexion = null; 
	try { 
		connexion = url.openConnection(); 
		connexion.setDoOutput(true);
	} catch(IOException e) { 
		System.err.println("Connexion impossible : " + e);
		System.exit(-1);
	}

	// Envoi de la requête
	try {
		OutputStreamWriter writer = new OutputStreamWriter(connexion.getOutputStream());
		writer.write(data.toString());
		writer.flush();
		writer.close();
	} catch(IOException e) {
		System.err.println("Erreur lors de l'envoi de la requete : " + e);
		System.exit(-1);            
	}

	// Réception des données depuis le serveur
	String donnees = ""; 
	try { 
		BufferedReader reader = new BufferedReader(new InputStreamReader( connexion.getInputStream())); 
		String tmp; 
		while((tmp = reader.readLine()) != null) 
			donnees += tmp; 
		reader.close(); 
	} catch(Exception e) { 
		System.err.println("Erreur lors de la lecture de la réponse : " + e);
		System.exit(-1);
	}
	
	// Affichage des données reçues
	System.out.println("Réponse du serveur : ");
	System.out.println(donnees);
	return donnees;
	}
}
