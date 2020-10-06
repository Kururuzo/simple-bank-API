package com.bank.repository;

import com.bank.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {

    /**
     *
     * @return
     * @throws SQLException
     * Метод возвращает список всех клиентов в БД
     */
    List<Client> getAll() throws SQLException;


    /**
     *
     * @param client
     * @throws SQLException
     * Добавление клиента
     */
    void addClient(Client client) throws SQLException;

    void updateClient(Client client) throws SQLException;

    Client getClientById(Integer id) throws SQLException;

    boolean deleteClient(Client client) throws SQLException;



//    Client getById (int clientId) throws SQLException;
//    Client getByAccountId (int AccountId) throws SQLException;
//
//
//
//    boolean add(String name, String email) throws SQLException;
}
