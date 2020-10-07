package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.CreditCard;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditCadrRepositoruImpl implements CreditCardRepository {

    private DataSource dataSource;
    private ResourceReader resourceReader = new ResourceReader();

    public CreditCadrRepositoruImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addCard(Account account, CreditCard card) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.ADD_CARD.getPath());
        try(Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, account.getId());
            stmt.setString(2, card.getNumber());
            stmt.execute();
        }
    }

    @Override
    public List<CreditCard> getAllCards(Account account) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.FIND_ALL_CARDS_BY_ACCOUNT_ID.getPath());
        try(Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, account.getId());
            ResultSet rs = stmt.executeQuery();
            List<CreditCard> creditCards = new ArrayList<>();
            while (rs.next()){
                CreditCard card = CreditCard.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .registered(rs.getDate("registered"))
                        .build();
                creditCards.add(card);
            }
            if ((creditCards.isEmpty())){
                throw new SQLException("Not found any client!");
            }else return creditCards;
        }
    }

    @Override
    public CreditCard getCardById(int id) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.FIND_CARD_BY_ID.getPath());
        try(Connection connection = getConnection(); PreparedStatement stmp = connection.prepareStatement(sql)) {
            stmp.setInt(1, id);
            ResultSet rs = stmp.executeQuery();
            if (rs.next()){
                CreditCard card = CreditCard.builder()
                        .id(rs.getInt("id"))
                        .number(rs.getString("number"))
                        .registered(rs.getDate("registered"))
                        .build();
                return card;
            }
        }
        return CreditCard.builder().id(-1).build();
    }

    @Override
    public void updateCard(CreditCard card) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.UPDATE_CARD.getPath());
        try(Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, card.getNumber());
            stmt.setInt(2, card.getId());
            stmt.execute();
        }
    }

    @Override
    public boolean deleteCard(CreditCard card) throws SQLException {
        String sql = resourceReader.getSQL(SqlScripts.DELETE_CARD.getPath());
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, card.getId());
            int rows = stmt.executeUpdate();
            if (rows != 0)return true;
        }
        return false;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
