package com.bank.repository;

import com.bank.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {
    Client getById (int clientId) throws SQLException;
    Client getByAccountId (int AccountId) throws SQLException;

    List<Client> getAll() throws SQLException;

    boolean add(String name, String email) throws SQLException;
}
