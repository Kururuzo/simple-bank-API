package com.bank.rest;

import com.bank.repository.Utils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.h2.tools.RunScript;
import org.junit.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class AccountRestControllerTest {

    private static HttpServer server;
    private static WebTarget target;

    @BeforeClass
    public static void setUp() throws Exception {
        // start the server
//        server = Main.startServer();
        server = Utils.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
         //c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Utils.BASE_URI);
    }

    @Before
    public void setup() {
        try (Connection connection = Utils.getDataSource().getConnection()){
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getIt() {
        String responseMsg = target.path("account").request().get(String.class);
            assertEquals("Account got it!", responseMsg);
    }

    @Test
    public void get_account() {
//        Response response = target.path("/account/1").request().get();

//        assertEquals("should return status 200", 200, response.getStatus());
//        assertNotNull("Should return user list", response.getEntity().toString());
    }

    @Test
    public void getBalance() {

//        assertNotNull(account);
//        assertEquals("100.0000", account.getBalance().toString());
    }
}