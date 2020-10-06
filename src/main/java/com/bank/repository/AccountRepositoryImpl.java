package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Client;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private DataSource dataSource;
    private ResourceReader resourceReader = new ResourceReader();

    public AccountRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addAccount(Client client, Account account) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.ADD_ACCOUNT.getPath());

        try(Connection connection = getConnection();PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1, client.getId());
            ps.setString(2, account.getNumber());
            ps.setBigDecimal(3, account.getAmount());
            ps.setString(4, account.getCurrency());
            ps.execute();
        }
    }

    @Override
    public Account getAccountByClientId(Client client) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.GET_ACCOUNT_BY_CLIENT_ID.getPath());

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, client.getId());
            ResultSet rs = stmt.executeQuery();
            Account account = null;
            while (rs.next()) {
                 account = Account.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .amount(rs.getBigDecimal("amount"))
                        .currency(rs.getString("currency"))
                        .build();
            }
            if(account != null){
                return account;
            }else {
                throw new SQLException("Not found any account!");
            }
        }
    }

    @Override
    public Account getAccountById(Account account) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.GET_ACCOUNT_BY_CLIENT_ID.getPath());

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, account.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                account = Account.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .amount(rs.getBigDecimal("amount"))
                        .currency(rs.getString("currency"))
                        .build();
            }
            if(account != null){
                return account;
            }else {
                throw new SQLException("Not found any account!");
            }
        }
    }

    @Override
    public List<Account> getAllClientAccounts(Client client) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.GET_ALL_CLIENT_ACCOUNTS.getPath());
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, client.getId());
            ResultSet rs = stmt.executeQuery();
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = Account.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .amount(rs.getBigDecimal("amount"))
                        .currency(rs.getString("currency"))
                        .build();
                accounts.add(account);
            }
            if (accounts.isEmpty()) {
                throw new SQLException("Not found any account!");
            } else {
                return accounts;
            }
        }
    }

    @Override
    public void updateAccount(Client client, Account account) {
        String sql = resourceReader.getSQL(SqlScripts.UPDATE_ACCOUNT.getPath());
        try(Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, client.getId());
            stmt.setBigDecimal(2, account.getAmount());
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean deletAccount(Account account) {
        String sql = resourceReader.getSQL(SqlScripts.DELETE_ACCOUNT.getPath());
        try(Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, account.getId());
            int rows = stmt.executeUpdate();
            if (rows != 0)return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
