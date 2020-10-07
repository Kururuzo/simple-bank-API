package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.CreditCard;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {

    /**
     *
     * @return
     * @throws SQLException
     * find all clients
     */
    List<Client> getAll() throws SQLException;


    /**
     *
     * @param client
     * @throws SQLException
     * add new client
     */
    void addClient(Client client) throws SQLException;

    /**
     *
     * @param client
     * @throws SQLException
     * update client
     */
    void updateClient(Client client) throws SQLException;

    /**
     *
     * @param id
     * @return
     * @throws SQLException
     * find client by id
     */
    Client getClientById(Integer id) throws SQLException;

    /**
     *
     * @param client
     * @return
     * @throws SQLException
     * delete client
     */
    boolean deleteClient(Client client) throws SQLException;

//    Client getCardByClientId(int clientId, int accountId,int cardId) throws SQLException;




//    Client getById (int clientId) throws SQLException;
//    Client getByAccountId (int AccountId) throws SQLException;
//
//
//
//    boolean add(String name, String email) throws SQLException;
}
