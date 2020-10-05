package com.bank.repository;

import com.bank.model.Client;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryJDBC implements ClientRepository {

    private static final String SQL_GET_CLIENT_BY_ID = "SELECT * FROM clients WHERE id = ?";
    private static final String SQL_GET_ALL_CLIENTS = "SELECT * FROM clients";
    private static final String SQL_ADD_CLIENT = "INSERT INTO clients (name, email) VALUES (?, ?)";


    private static final String SQL_GET_CLIENT_BY_ACCOUNT_ID = "SELECT * FROM clients WHERE id = " +
            "(SELECT clients_id FROM accounts WHERE id = ?)";

    DataSource dataSource;

    public ClientRepositoryJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Client getById(int clientId) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_GET_CLIENT_BY_ID);
        ps.setInt(1, clientId);
        ResultSet resultSet = ps.executeQuery();
        Client client = null;
        if (resultSet.next()) {
            client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setName(resultSet.getString("name"));
            client.setEmail(resultSet.getString("email"));
            client.setRegistered(resultSet.getTimestamp("registered"));
        }
        resultSet.close();
        ps.close();
        Utils.closeQuietly(connection);

        if (client == null) {
            throw new SQLException("Not found client with id = " + clientId);
        } else return client;

    }

    @Override
    public Client getByAccountId(int accountId) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_GET_CLIENT_BY_ACCOUNT_ID);
        ps.setInt(1, accountId);
        ResultSet resultSet = ps.executeQuery();
        Client client = null;
        if (resultSet.next()) {
            client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setName(resultSet.getString("name"));
            client.setEmail(resultSet.getString("email"));
            client.setRegistered(resultSet.getTimestamp("registered"));
        }
        resultSet.close();
        ps.close();
        Utils.closeQuietly(connection);

        if (client == null) {
            throw new SQLException("Not found client with Account id = " + accountId);
        } else return client;
    }

    @Override
    public List<Client> getAll() throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_GET_ALL_CLIENTS);
        ResultSet resultSet = ps.executeQuery();

        List<Client> clientList = new ArrayList<>();
        while (resultSet.next()) {
            Client client = new Client();
            client.setId(resultSet.getInt("id"));
            client.setName(resultSet.getString("name"));
            client.setEmail(resultSet.getString("email"));
            client.setRegistered(resultSet.getTimestamp("registered"));
            clientList.add(client);
        }

        resultSet.close();
        ps.close();
        Utils.closeQuietly(connection);

        if (clientList.isEmpty()) {
            throw new SQLException("Not found any client!");
        } else {
            return clientList;
        }
    }

    @Override
    public boolean add(String name, String email) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL_ADD_CLIENT);
        ps.setString(1, name);
        ps.setString(2, email);

        int res = ps.executeUpdate();
        ps.close();
        Utils.closeQuietly(conn);
        return res != 0;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
