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

public class ClientRepositoryJDBCTest {
    public static final String DB_URL = "jdbc:h2:mem:bank;"
            + "DB_CLOSE_DELAY=-1;"
            + "DATABASE_TO_UPPER=false;";

    private static ClientRepository clientRepository;
    private static JdbcDataSource dataSource;

    @BeforeClass
    public static void setup() {
        dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        clientRepository = new ClientRepositoryJDBC(dataSource);
    }

    @Before
    public void setUp() {
        try (Connection connection = dataSource.getConnection()) {
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getById() {
        try {
            Client client = clientRepository.getById(CLIENT_1_ID);
            CLIENTS_MATCHER.assertMatch(client, CLIENT_1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getByAccountId() {
        try {
            Client client = clientRepository.getByAccountId(CLIENT_1.getAccounts().get(0).getId());
            CLIENTS_MATCHER.assertMatch(client, CLIENT_1);
        } catch (SQLException e) {
            e.printStackTrace();
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

    @Test
    public void addClient() {
        try {
            boolean success = clientRepository.add(CLIENT_3.getName(), CLIENT_3.getEmail());
            Assert.assertTrue(success);
            List<Client> allClients = clientRepository.getAll();
            CLIENTS_MATCHER.assertMatch(allClients, CLIENT_1, CLIENT_2, CLIENT_3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}