package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.CreditCard;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CreditCadrRepositoruImol implements CreditCardRepository {

    private DataSource dataSource;
    private ResourceReader resourceReader = new ResourceReader();

    public CreditCadrRepositoruImol(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addCard(Account account, CreditCard card) {

    }

    @Override
    public List<CreditCard> getAllCards(Account account) {
        return null;
    }

    @Override
    public CreditCard getCardById(CreditCard card) {
        return null;
    }

    @Override
    public void updateCard(CreditCard card) {

    }

    @Override
    public boolean deleteCard(CreditCard card) {
        return false;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
