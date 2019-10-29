package com.test;
import java.io.IOException;
import com.sun.net.httpserver.HttpServer;

import com.sun.net.httpserver.HttpContext;
import java.net.InetSocketAddress;

public class ServeurHttp {

    public static void main(String[] args) {    
        HttpServer serveur = null;
        try {
            serveur = HttpServer.create(new InetSocketAddress(8081), 0);
        } catch(IOException e) {
            System.err.println("Erreur lors de la création du serveur " + e);
            System.exit(-1);
        }

        serveur.createContext("/login.html", new LoginHandler());
        //serveur.createContext("/create.html", new LoginHandler());
        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Serveur démarré. Pressez CRTL+C pour arrêter.");
    }

}