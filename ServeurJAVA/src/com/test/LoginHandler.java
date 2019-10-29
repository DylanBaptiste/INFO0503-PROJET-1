package com.test;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

class LoginHandler implements HttpHandler {

	private HttpExchange Exchange =  null;

	public void handle(HttpExchange t) throws IOException {

		this.Exchange = t;
		
		System.out.println("test");
		/*try{
			JSONObject test = new JSONObject().put("success", "qsd").put("id", 1);
			System.out.println("test: " + test.toString());
		}
		catch(Execption e){
			e.printStackTrace();
		}*/
		
		//sendSuccess("c'est ok ma couille", 1);
		//sendError("test");
		/*this.Exchange = t;
		
		try{
			String query = readRequest();
		}
		catch(Exception e){
			System.out.println(e);
			sendError(e.toString());
		}
		
		// Recuperation des variables recu par le post
		String loginU	 = "";
		String passwordU = "";

		try{
			JSONObject jsonQuerry = new JSONObject(query);
		}catch(Exception e){
			sendError("Les données envoyées ne sont pas au format json");
			return;
		}
		
		if( jsonQuerry.has("login") ){
			loginU = jsonQuerry.getString("login");
			loginU.replaceAll("[%~/. ]", ""); // <- faille si pas ca je pense
		}
		else{
			sendError("Aucun login envoyé");
			return;
		}

		if( jsonQuerry.has("password") ){
			passwordU = jsonQuerry.getString("password");
		}
		else{
			sendError("Aucun password envoyé");
			return;
		}
		
		try{
			JSONObject localJsonUser = readJson(loginU+".json");
		}
		catch(Exception e){
			sendError(e.toString());
			return;
		}

		System.out.println("Contenu JSON : " );
		System.out.println( localJsonUser.toString() ) ;
		
		if(localJsonUser.has("password") && localJsonUser.getString("password") == passwordU){
			sendSuccess("login reussi", localJsonUser.getInt("id"));
		}else{
			sendError("Mauvais mot de passe");
			return;
		}
		*/

		/*
		String reponse = "test";
		// Envoi de l'en-tête Http
        try {
            Headers h = t.getResponseHeaders();
            h.set("Content-Type", "text/html; charset=utf-8");
            t.sendResponseHeaders(200, reponse.getBytes().length);
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi de l'en-tête : " + e);
            System.exit(-1);
        }

        // Envoi du corps (données HTML)
        try {
            OutputStream os = t.getResponseBody();
            os.write(reponse.getBytes());
            os.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de l'envoi du corps : " + e);
		}
		*/

	
	}

	

	private String readRequest() throws Exception{

		//Creation d'un flux afin de lire les données recu en http
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(this.Exchange.getRequestBody(),"utf-8"));
		} catch(UnsupportedEncodingException e) {
			throw "Votre requete est mal formé: "+e;
		}
	
		// Récupération des données en POST
		String query = null;
		try {
			query = br.readLine();
		} catch(IOException e) {
			throw "Erreur lors de la lecture d'une ligne: " + e;
		}
		
		return query;

	}

	private JSONObject readJson(String loginU) throws Exception{
		fs = null;
		String json = "";

		try  {
			fs = new FileInputStream ( loginU+".json" );
			Scanner scanner = new Scanner ( fs );

			while ( scanner.hasNext() )
				json += scanner.nextLine();

			scanner.close();
			json = json.replaceAll("[\t\r\n ]", "");
			fs.close();
		}
		catch (FileNotFoundException e){
			throw "L'utilisateur "+loginU+" n'existe pas.";
		}

		JSONObject objet = new JSONObject( json );
	}

	private void sendSuccess(String successMessage, int id){
		sendPostResponse(new JSONObject().put("success", successMessage).put("id", id));
	}

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
	        

