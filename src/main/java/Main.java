/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nethw
 */

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {
    // This is the URL where your server will live
    public static final String BASE_URI = "http://localhost:8080/api/v1/";

    public static void main(String[] args) {
        try {
            // This grabs all the API endpoints and errors you registered
            final ResourceConfig rc = ResourceConfig.forApplication(new com.coursework.config.ApplicationConfig());

            // This starts the embedded server!
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
            
            System.out.println("=====================================================");
            System.out.println("SMART CAMPUS SERVER IS RUNNING!");
            System.out.println("Test your Discovery API here: " + BASE_URI);
            System.out.println("Keep this window open. Press ENTER in this console to stop the server.");
            System.out.println("=====================================================");
            
            System.in.read();
            server.shutdownNow();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
