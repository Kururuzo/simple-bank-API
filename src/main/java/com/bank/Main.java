package com.bank;

import com.bank.repository.Utils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    /**
     * Main method.
     * @param args - scripts
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 0) {
            for (String arg : args) {
                RunScript.execute(Utils.getConnection(), new FileReader(arg));
            }
        } else {
            System.out.println("Starting server without argument (scripts)");
        }
        Utils.startServer();


    }

//    // Base URI the Grizzly HTTP server will listen on
//    public static final String BASE_URI = "http://localhost:8080/myapp/";
//
//    /**
//     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
//     * @return Grizzly HTTP server.
//     */
//    public static HttpServer startServer() {
//
//        // create a resource config that scans for JAX-RS resources and providers
//        // in com.novikov package
//        final ResourceConfig rc = new ResourceConfig().packages("com.bank");
//
//        // create and start a new instance of grizzly http server
//        // exposing the Jersey application at BASE_URI
//        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
//    }
//
//    /**
//     * Main method.
//     * @param args
//     * @throws IOException
//     */
//    public static void main(String[] args) throws IOException {
//        final HttpServer server = startServer();
//        System.out.println(String.format("Jersey app started with WADL available at "
//                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
//        System.in.read();
//        server.stop();
//    }
}

