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



public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	Scanner saisieUtilisateur = new Scanner(System.in);


	System.out.println("Veuillez saisir un login :");
	String RecupLogin = saisieUtilisateur.nextLine();
	System.out.println("Veuillez saisir un mot de passe :");
	String RecupPassword = saisieUtilisateur.nextLine();

	

	

	Client client = new Client(RecupLogin,RecupPassword);
	client.requestLogin(RecupLogin, RecupPassword);
	//System.out.println(client);
    
	//JSONObject jsonClient = client.toJSON();
    //System.out.println(jsonClient);
	
	//TODO Si pas bon login mdp ????
	
	//TODO Connection accepter
	String RecupOption = "0";
	do{

	
		System.out.println("");
		System.out.println("Bravo vous etes connecter ");
		System.out.println("Que voulez vous faire ? ");
		System.out.println(" ---------OPTIONS----------");
		System.out.println("| (1)  Repondre Sondage   |");
		System.out.println("| (2)  Creer Sondage      |");
		System.out.println("| (3)  Voir le nom de     |");
		System.out.println("|      ses Sondages       |");
		System.out.println("| (4) Voir Reponse Sondage|");
		System.out.println("| (5) login				  |");
		System.out.println("| (6) quitter				  |");
		System.out.println(" -------------------------");
		
		RecupOption = saisieUtilisateur.nextLine();
		int recupOption = Integer.parseInt(RecupOption);
		
		if (recupOption == 1) {
			System.out.println("Bienvenue sur l'interface de Reponse aux Sondages");
			System.out.println("Vous ne pouvez repondre qu'une fois par sondage ! ");
			//TODO Demande des sondages dispo && reponse des questions ? ? On utilise le scanner ??
		}
		if (recupOption == 2) 
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
			
		}
		if (recupOption == 3) {
			System.out.print("Bienvenue sur l'interfaxe pour voir Sondage");
		}
		if (recupOption == 4) {
			System.out.print("Tu casses les couilles");
		}
		if (recupOption == 5) {
			System.out.println("Veuillez saisir un login :");
			RecupLogin = saisieUtilisateur.nextLine();
			System.out.println("Veuillez saisir un mot de passe :");
			RecupPassword = saisieUtilisateur.nextLine();
			client.requestLogin(RecupLogin, RecupPassword);
		}
	}while(RecupOption != "6");

	saisieUtilisateur.close();

}
}