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

    private static final String SQL_GET_CLIENT_BY_ID = "SELECT * FROM clients WHERE id = ?";
    private static final String SQL_GET_ALL_CLIENTS = "SELECT * FROM clients";
    private static final String SQL_ADD_CLIENT = "INSERT INTO clients (name, email) VALUES (?, ?)";


    private static final String SQL_GET_CLIENT_BY_ACCOUNT_ID = "SELECT * FROM clients WHERE id = " +
            "(SELECT clients_id FROM accounts WHERE id = ?)";

    private DataSource dataSource;
    private ResourceReader resourceReader = new ResourceReader();


    public ClientRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Client> getAll() throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.SELECT_ALL_CLIENTS.getPath());
        try(Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
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
        String sql = resourceReader.getSQL(SqlScripts.ADD_CLIENT.getPath());
        try(Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.execute();
        }
    }

    @Override
    public void updateClient(Client client) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.UPDATE_CLIENT.getPath());
        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setInt(3, client.getId());
            stmt.execute();
        }
    }

    @Override
    public Client getClientById(Integer id) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.GET_CLIENT_BY_ID.getPath());
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = Client.builder()
                        .id(rs.getInt(1))
                        .name(rs.getString(2))
                        .email(rs.getString(3))
                        .registered(rs.getDate(4))
                        .build();
                return client;
            }
        }
        return Client.builder().id(-1).build();
    }

    @Override
    public boolean deleteClient(Client client) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.DELETE_CLIENT.getPath());
        try(Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,client.getId());
            int rows = stmt.executeUpdate();
            if (rows != 0)return true;
        }
        return false;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
