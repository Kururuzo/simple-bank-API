package com.bank.repository;

import com.bank.model.Client;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {

//    private static final String SQL_GET_CLIENT_BY_ID = "SELECT * FROM clients WHERE id = ?";
//    private static final String SQL_GET_ALL_CLIENTS = "SELECT * FROM clients";
//    private static final String SQL_ADD_CLIENT = "INSERT INTO clients (name, email) VALUES (?, ?)";
//
//    private static final String SQL_GET_CLIENT_BY_ACCOUNT_ID = "SELECT * FROM clients WHERE id = " +
//            "(SELECT clients_id FROM accounts WHERE id = ?)";

    private DataSource dataSource;
    private ResourceReader resourceReader;

    public ClientRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.resourceReader = new ResourceReader().get();
    }


//    @Override
//    public Client getById(int clientId) throws SQLException {
//        Connection connection = getConnection();
//        PreparedStatement ps = connection.prepareStatement(SQL_GET_CLIENT_BY_ID);
//        ps.setInt(1, clientId);
//        ResultSet resultSet = ps.executeQuery();
//        Client client = null;
//        if (resultSet.next()) {
//            client = new Client();
//            client.setId(resultSet.getInt("id"));
//            client.setName(resultSet.getString("name"));
//            client.setEmail(resultSet.getString("email"));
//            client.setRegistered(resultSet.getTimestamp("registered"));
//        }
//        resultSet.close();
//        ps.close();
//        Utils.closeQuietly(connection);
//
//        if (client == null) {
//            throw new SQLException("Not found client with id = " + clientId);
//        } else return client;
//
//    }
//
//    @Override
//    public Client getByAccountId(int accountId) throws SQLException {
//        Connection connection = getConnection();
//        PreparedStatement ps = connection.prepareStatement(SQL_GET_CLIENT_BY_ACCOUNT_ID);
//        ps.setInt(1, accountId);
//        ResultSet resultSet = ps.executeQuery();
//        Client client = null;
//        if (resultSet.next()) {
//            client = new Client();
//            client.setId(resultSet.getInt("id"));
//            client.setName(resultSet.getString("name"));
//            client.setEmail(resultSet.getString("email"));
//            client.setRegistered(resultSet.getTimestamp("registered"));
//        }
//        resultSet.close();
//        ps.close();
//        Utils.closeQuietly(connection);
//
//        if (client == null) {
//            throw new SQLException("Not found client with Account id = " + accountId);
//        } else return client;
//    }

    @Override
    public Client getClientById(Integer id) throws SQLException {
//        ResourceReader resourceReader = new ResourceReader().get();
        String sql = resourceReader.getSQL(SqlScripts.GET_CLIENT_BY_ID.getPath());

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = Client.builder()
                        .id(rs.getInt(1))
                        .name(rs.getString(2))
                        .email(rs.getString(3))
                        .registered(rs.getTimestamp(4))
                        .build();
                return client;
            }
        }
        return new Client();
    }

    @Override
    public List<Client> getAll() throws SQLException {
//        ResourceReader resourceReader = new ResourceReader().get();
        String sql = resourceReader.getSQL(SqlScripts.SELECT_ALL_CLIENTS.getPath());

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery();) {

//            ResultSet resultSet = ps.executeQuery();

            List<Client> clientList = new ArrayList<>();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setRegistered(resultSet.getTimestamp("registered"));
                clientList.add(client);
            }
            if (clientList.isEmpty()) {
                throw new SQLException("Not found any client!");
            } else {
                return clientList;
            }
        }
    }

    @Override
    public void addClient(Client client) throws SQLException {
//        ResourceReader resourceReader = new ResourceReader().get();
        String sql = resourceReader.getSQL(SqlScripts.ADD_CLIENT.getPath());

        try ( Connection connection = getConnection();
              PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getEmail());
            ps.execute();
        }

    }

    @Override
    public void updateClient(Client client) throws SQLException {
//        ResourceReader resourceReader = new ResourceReader().get();
        String sql = resourceReader.getSQL(SqlScripts.ADD_CLIENT.getPath());

        try ( Connection connection = getConnection();
              PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.execute();
        }
    }

    @Override
    public boolean deleteClient(Client client) throws SQLException {
//        ResourceReader resourceReader = new ResourceReader().get();
        String sql = resourceReader.getSQL(SqlScripts.DELETE_CLIENT.getPath());

        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, client.getId());
            int rows = stmt.executeUpdate();
            if (rows != 0) return true;
        }
        return false;
    }


//    @Override
//    public boolean add(String name, String email) throws SQLException {
//
//        Connection conn = getConnection();
//        PreparedStatement ps = conn.prepareStatement(SQL_ADD_CLIENT);
//        ps.setString(1, name);
//        ps.setString(2, email);
//
//        int res = ps.executeUpdate();
//        ps.close();
//        Utils.closeQuietly(conn);
//        return res != 0;
//    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
