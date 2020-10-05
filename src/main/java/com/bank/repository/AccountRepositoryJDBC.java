package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.CreditCard;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryJDBC implements AccountRepository {
    public static final String SQL_GET_AMOUNT_BY_ACCOUNT_NUMBER = "SELECT amount FROM accounts WHERE number = ?";
    public static final String SQL_GET_AMOUNT_BY_ACCOUNT_ID = "SELECT amount FROM accounts WHERE id = ?";
    private static final String SQL_UPDATE_AMOUNT = "UPDATE accounts SET amount = ? WHERE number = ?";
    //    private static final String SQL_GET_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE id = ?";
    private static final String SQL_GET_ACCOUNT_LIST_BY_CLIENT_ID = "SELECT * FROM accounts WHERE clients_id = ?";
    private static final String SQL_GET_CREDIT_CARD_LIST_BY_ACCOUNT_ID = "SELECT * FROM credit_cards WHERE account_id = ?";
    private static final String SQL_ADD_CREDIT_CARD = "INSERT INTO credit_cards (account_id, number) VALUES (?, ?)";
    private static final String SQL_IS_CARD_NUMBER_EXISTS = "SELECT * FROM credit_cards WHERE number = ?";

    DataSource dataSource;

    public AccountRepositoryJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Double checkBalanceByAccountNumber(String accountNumber) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL_GET_AMOUNT_BY_ACCOUNT_NUMBER);
        ps.setString(1, accountNumber);
        ResultSet resultSet = ps.executeQuery();
        Double amount = null;
        if (resultSet.next()) {
            amount = resultSet.getDouble("amount");
        }
        resultSet.close();
        ps.close();
        Utils.closeQuietly(conn);
        if (amount == null) {
            throw new SQLException("Account with number = " + accountNumber + ", not found");
        } else {
            return amount;
        }
    }


    @Override
    public Double checkBalanceByAccountId(Integer accountId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL_GET_AMOUNT_BY_ACCOUNT_ID);
        ps.setInt(1, accountId);
        ResultSet resultSet = ps.executeQuery();
        Double amount = null;
        if (resultSet.next()) {
            amount = resultSet.getDouble("amount");
        }
        resultSet.close();
        ps.close();
        Utils.closeQuietly(conn);
        if (amount == null) {
            throw new SQLException("Account with Account Id = " + accountId + ", not found");
        } else {
            return amount;
        }
    }

    @Override
    public boolean depositFunds(String accountNumber, Double amount) throws SQLException {
        Double balance = checkBalanceByAccountNumber(accountNumber);
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_AMOUNT);
        ps.setDouble(1, balance + amount);
        ps.setString(2, accountNumber);
        int res = ps.executeUpdate();
        ps.close();
        Utils.closeQuietly(conn);
        return res != 0;
    }

    @Override
    public List<Account> getAccountListByClientId(Integer clientId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL_GET_ACCOUNT_LIST_BY_CLIENT_ID);
        ps.setInt(1, clientId);
        ResultSet resultSet = ps.executeQuery();

        List<Account> accountList = new ArrayList<>();
        while (resultSet.next()) {
            Account account = new Account();
            account = new Account();
            account.setId(resultSet.getInt("id"));
            account.setNumber(resultSet.getString("number"));
            account.setAmount(resultSet.getDouble("amount"));
            account.setCurrency(resultSet.getString("currency"));
            accountList.add(account);
        }

        resultSet.close();
        ps.close();
        Utils.closeQuietly(conn);

        if (accountList.isEmpty()) {
            throw new SQLException("For the Client with Id = " + clientId + ", not found any account");
        } else {
            return accountList;
        }
    }

    @Override
    public List<CreditCard> getCreditCardListByAccountId(Integer accountId) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL_GET_CREDIT_CARD_LIST_BY_ACCOUNT_ID);
        ps.setInt(1, accountId);
        ResultSet resultSet = ps.executeQuery();

        List<CreditCard> cardList = new ArrayList<>();
        while (resultSet.next()) {
            CreditCard card = new CreditCard();
            card.setId(resultSet.getInt("id"));
            card.setNumber(resultSet.getString("number"));
            card.setRegistered(resultSet.getTimestamp("registered"));
            cardList.add(card);
        }

        resultSet.close();
        ps.close();
        Utils.closeQuietly(conn);

        if (cardList.isEmpty()) {
            throw new SQLException("For the Account with Id = " + accountId + ", no cards found");
        } else {
            return cardList;
        }
    }

    @Override
    public boolean addCreditCard(Integer accountId, String cardNumber) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL_ADD_CREDIT_CARD);
        ps.setInt(1, accountId);
        ps.setString(2, cardNumber);

        if (ps.executeUpdate() == 0) {
            throw new SQLException("For the Account with Id = " + accountId + ". Credit card was now issued!");
        }
        return true;
    }

    @Override
    public boolean isCardNumberExists(String cardNumber) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(SQL_IS_CARD_NUMBER_EXISTS);
        ps.setString(1, cardNumber);
        ResultSet resultSet = ps.executeQuery();
        boolean isExists = resultSet.next();

        resultSet.close();
        ps.close();
        Utils.closeQuietly(conn);
        return isExists;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
