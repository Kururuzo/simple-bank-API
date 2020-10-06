package com.bank;

import com.bank.repository.Utils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 */
public class Main {

    /**
     * Main method.
     *
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
}

