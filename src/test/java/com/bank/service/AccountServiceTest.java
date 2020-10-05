package com.bank.service;

import com.bank.model.CreditCard;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.bank.AccountTestData.*;
import static com.bank.ClientTestData.*;
import static com.bank.CreditCardTestData.*;

public class AccountServiceTest {
    public static final String DB_URL = "jdbc:h2:mem:bank;"
            + "DB_CLOSE_DELAY=-1;"
            + "DATABASE_TO_UPPER=false;";


    private static JdbcDataSource dataSource;
    private static AccountService service;

    @BeforeClass
    public static void setup() {
        dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        service = new AccountService(dataSource);
    }

    @Before
    public void setUp() throws Exception {
        try (Connection connection = dataSource.getConnection()){
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkBalance() {
        Double balance = service.checkBalance(ACCOUNT_1);
        Assert.assertEquals(ACCOUNT_1.getAmount(), balance);
    }

    @Test
    public void depositeFunds() {
        Double deposite = 3000d;
        Double balance = service.checkBalance(ACCOUNT_1) + deposite;
        service.depositeFunds(ACCOUNT_1, deposite);
        Double newBalance = service.checkBalance(ACCOUNT_1);
        Assert.assertEquals(balance, newBalance);
    }

    @Test
    public void getListOfCreditCards() {
        List<CreditCard> cards = Arrays.asList(CARD_1);

        List<CreditCard> clientOneCards = service.getListOfCreditCards(CLIENT_1);
        Assert.assertEquals(1, clientOneCards.size());
        CARD_MATCHER.assertMatch(clientOneCards.get(0), CARD_1);
    }

    @Test
    public void creditCardIssue() {
        List<CreditCard> cardList = service.getListOfCreditCards(CLIENT_1);
        CreditCard issuedCard = service.creditCardIssue(ACCOUNT_1);
        List<CreditCard> newCardList = service.getListOfCreditCards(CLIENT_1);
        Assert.assertEquals(cardList.size() + 1, newCardList.size());

        newCardList.removeAll(cardList);
        CARD_MATCHER.assertMatch(newCardList.get(0), issuedCard);
    }
}