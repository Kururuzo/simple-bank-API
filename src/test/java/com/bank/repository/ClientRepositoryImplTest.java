package com.bank.repository;

import com.bank.model.Client;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static com.bank.ClientTestData.*;

public class ClientRepositoryImplTest {
    public static final String DB_URL = "jdbc:h2:mem:bank;"
            + "DB_CLOSE_DELAY=-1;"
            + "DATABASE_TO_UPPER=false;";

    private static ClientRepository clientRepository;
//    private static JdbcDataSource dataSource;

    @BeforeClass
    public static void setup() {
//        dataSource = new JdbcDataSource();
//        dataSource.setURL(DB_URL);
//        clientRepository = new ClientRepositoryImpl(dataSource);
        clientRepository = new ClientRepositoryImpl(Utils.getDataSource());
    }

    @Before
    public void setUp() {
//        try (Connection connection = dataSource.getConnection()) {
        try (Connection connection = Utils.getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addClient() {
        try {
            Client client = Client.builder()
                    .id(100006)
                    .name("newName")
                    .email("new@mail.ru")
                    .build();
            clientRepository.addClient(client);
            List<Client> allClients = clientRepository.getAll();
            CLIENTS_MATCHER.assertMatch(allClients, CLIENT_1, CLIENT_2, client);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void getClientById() {
        Client client;
        try {
            client = clientRepository.getClientById(CLIENT_1_ID);
            CLIENTS_MATCHER.assertMatch(client, CLIENT_1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void updateClient() {
        try {
            Client client = Client.builder()
                    .id(CLIENT_3.getId())
                    .name("update name")
                    .email("update@mail.ru")
                    .build();
            clientRepository.updateClient(client);
            Client client1 = clientRepository.getClientById(CLIENT_3.getId());
            CLIENTS_MATCHER.assertMatch(client, client1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void deleteClient() {
        try {
            Client client = Client.builder()
                    .name("update name")
                    .email("update@mail.ru")
                    .build();
            clientRepository.addClient(client);
            Client client1 = clientRepository.getClientById(100006);
            clientRepository.deleteClient(client1);
            List<Client> clients = clientRepository.getAll();
            CLIENTS_MATCHER.assertMatch(clients, CLIENT_1, CLIENT_2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



    @Test
    public void getAllClients() {
        try {
            List<Client> allClients = clientRepository.getAll();
            Collections.sort(allClients, clientComparator);
            Assert.assertEquals(allClients.size(), 2);
            CLIENTS_MATCHER.assertMatch(allClients, CLIENTS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//
//    @Test
//    public void getById() {
//        try {
//            Client client = clientRepository.getById(CLIENT_1_ID);
//            CLIENTS_MATCHER.assertMatch(client, CLIENT_1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getByAccountId() {
//        try {
//            Client client = clientRepository.getByAccountId(CLIENT_1.getAccounts().get(0).getId());
//            CLIENTS_MATCHER.assertMatch(client, CLIENT_1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


//    @Test
//    public void addClient() {
//        try {
//            boolean success = clientRepository.add(CLIENT_3.getName(), CLIENT_3.getEmail());
//            Assert.assertTrue(success);
//            List<Client> allClients = clientRepository.getAll();
//            CLIENTS_MATCHER.assertMatch(allClients, CLIENT_1, CLIENT_2, CLIENT_3);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}