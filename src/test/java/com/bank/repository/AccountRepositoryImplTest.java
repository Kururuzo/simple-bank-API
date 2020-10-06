package com.bank.repository;

import com.bank.ClientTestData;
import com.bank.CreditCardTestData;
import com.bank.model.Account;
import com.bank.model.CreditCard;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.bank.AccountTestData.*;
import static com.bank.CreditCardTestData.CARD_1;

public class AccountRepositoryImplTest {
    public static final String DB_URL = "jdbc:h2:mem:bank;"
            + "DB_CLOSE_DELAY=-1;"
            + "DATABASE_TO_UPPER=false;";

    private static AccountRepository repository;
    private static JdbcDataSource dataSource;

    @BeforeClass
    public static void setup() {
        dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        repository = new AccountRepositoryImpl(dataSource);
    }

    @Before
    public void setUp() {
        try (Connection connection = dataSource.getConnection()){
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void checkBalanceByAccountNumber() {
//        try {
//            BigDecimal d = repository.checkBalanceByAccountNumber(ACCOUNT_1.getNumber());
//            Assert.assertEquals(d, ACCOUNT_1.getAmount());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void checkBalanceByAccountId() {
//        try {
//            BigDecimal d = repository.checkBalanceByAccountId(ACCOUNT_1.getId());
//            Assert.assertEquals(d, ACCOUNT_1.getAmount());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void depositFunds() {
//        try {
//            BigDecimal deposite = new BigDecimal(1000).setScale(2);
//            boolean success = repository.depositFunds(ACCOUNT_1.getNumber(), deposite);
//            Assert.assertTrue(success);
//            BigDecimal d = repository.checkBalanceByAccountId(ACCOUNT_1.getId());
//            BigDecimal expected = ACCOUNT_1.getAmount().add(deposite);
//            Assert.assertEquals(expected, d);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void getAccountListByClientId() {
//        try {
//            List<Account> accountListByClientId = repository.getAccountListByClientId(ClientTestData.CLIENT_1_ID);
//            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(accountListByClientId, Arrays.asList(ACCOUNT_1));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void getCreditCardListByAccountId() {
        try {
            List<CreditCard> cardList = repository.getCreditCardListByAccountId(ACCOUNT_1.getId());
            Assert.assertEquals(1, cardList.size());
            Assert.assertEquals(cardList.get(0).getNumber(), CARD_1.getNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addCreditCard() {
        try {
            String newNumber = "0000000000";
            repository.addCreditCard(ACCOUNT_1.getId(), newNumber);
            List<CreditCard> cardList = repository.getCreditCardListByAccountId(ACCOUNT_1.getId());
            Assert.assertEquals(2, cardList.size());
            Collections.sort(cardList, CreditCardTestData.creditCardComparator);
            Assert.assertEquals(newNumber, cardList.get(1).getNumber());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void isCardNumberExists() {
        try {
            Assert.assertTrue(repository.isCardNumberExists(CARD_1.getNumber()));
            Assert.assertFalse(repository.isCardNumberExists("00000000x"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}