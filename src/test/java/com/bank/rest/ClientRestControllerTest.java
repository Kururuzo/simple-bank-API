package com.bank.rest;

import com.bank.model.to.ClientTo;
import com.bank.repository.Utils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.bank.ClientTestData.*;
import static org.junit.Assert.*;

public class ClientRestControllerTest {

    private static HttpServer server;
    private static WebTarget target;

    @BeforeClass
    public static void setUp() throws Exception {
        // start the server
//        server = Main.startServer();
        server = Utils.startServer();
        // create the client
        javax.ws.rs.client.Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        //c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Utils.BASE_URI);
    }

    @Before
    public void setup() {
        try (Connection connection = Utils.getDataSource().getConnection()) {
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
    public void getById() {
        ClientTo clientTo = target.path("/client/" + CLIENT_1_ID).request().get(ClientTo.class);
        CLIENTS_TO_MATCHER.assertMatch(new ClientTo(CLIENT_1), clientTo);
    }

    @Test
    public void getAll() {
        Response response = target.path("/client/all").request().get();
        assertEquals("should return status 200", 200, response.getStatus());

        GenericType<List<ClientTo>> generic = new GenericType<List<ClientTo>>() {
        };

        List<ClientTo> actualTos  = response.readEntity(generic);
        List<ClientTo> expectedTos = Arrays.asList(CLIENT_1, CLIENT_2).stream()
                .map(ClientTo::new).collect(Collectors.toList());

        CLIENTS_TO_MATCHER.assertMatch(actualTos, expectedTos);
    }



//    @Test
//    public void getIt() {
//        String responseMsg = target.path("client").request().get(String.class);
//        assertEquals("Client got it!", responseMsg);
//    }
//
//    @Test
//    public void get() {
//        String responseMsg = target.path("/client/100000").request().get(String.class);
//        System.out.println(responseMsg);
//
//    }


//    @Test
//    public void postJson() {
//        String responseMsg = target.path("/client/post").request().get(String.class);
//        System.out.println(responseMsg);
//
//    }
}