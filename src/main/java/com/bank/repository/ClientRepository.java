package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientRepository {

    /**
     *
     * Метод возвращает список всех клиентов в БД
     */
    List<Client> getAll() throws SQLException;


    /**
     *
     * Добавление клиента
     */
    void addClient(Client client) throws SQLException;

    /**
     *
     * изменение клиента
     */
    void updateClient(Client client) throws SQLException;

    /**
     *
     * поиск клиента по ID
     */
    Client getClientById(Integer id) throws SQLException;

    /**
     *
     * удаление клиента
     */
    boolean deleteClient(Client client) throws SQLException;

    void addClientAccount(Client client, Account account) throws SQLException;



//    Client getById (int clientId) throws SQLException;
//    Client getByAccountId (int AccountId) throws SQLException;
//
//
//
//    boolean add(String name, String email) throws SQLException;
}
