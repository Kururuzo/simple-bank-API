package com.bank.service;

import com.bank.model.Client;
import com.bank.repository.ClientRepository;
import com.bank.repository.ClientRepositoryImpl;
import com.bank.repository.Utils;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private ClientRepository repository;

    public ClientService() {
        this.repository = new ClientRepositoryImpl(Utils.getDataSource());
    }

    public Client getById(Integer id) {
        Client client = null;
        try {
            client = repository.getClientById(id);
            if (client == null) {
                throw new SQLException("Client with Id = " + id + ", not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public List<Client> getAll() {
        List<Client> clientList = null;
        try {
            clientList = repository.getAll();
            if (clientList.isEmpty()) {
                throw new SQLException("No one Client found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }



}
