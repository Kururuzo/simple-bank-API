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

import javax.sql.DataSource;
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

    private static AccountRepository repository;

    @BeforeClass
    public static void setup() {
        repository = new AccountRepositoryImpl(Utils.getDataSource());
    }

    @Before
    public void setUp() {
        try (Connection connection = Utils.getConnection()){
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2init.SQL"));
            RunScript.execute(connection, new FileReader("src/main/resources/dataBase/H2populate.SQL"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkBalanceByAccountNumber() {
        try {
            BigDecimal d = repository.checkBalanceByAccountNumber(ACCOUNT_1.getNumber());
            Assert.assertEquals(0, d.compareTo(ACCOUNT_1.getAmount()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkBalanceByAccountId() {
        try {
            BigDecimal d = repository.checkBalanceByAccountId(ACCOUNT_1.getId());
            Assert.assertEquals(0, d.compareTo(ACCOUNT_1.getAmount()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void depositFunds() {
        try {
            BigDecimal deposite = new BigDecimal(1000);
            boolean success = repository.depositFunds(ACCOUNT_1.getNumber(), deposite);
            Assert.assertTrue(success);
            BigDecimal d = repository.checkBalanceByAccountId(ACCOUNT_1.getId());
            BigDecimal expected = ACCOUNT_1.getAmount().add(deposite);
            Assert.assertEquals(expected, d);
            Assert.assertEquals(0, d.compareTo(expected));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAccountListByClientId() {
        try {
            List<Account> accountListByClientId = repository.getAccountListByClientId(ClientTestData.CLIENT_1_ID);
            Assert.assertEquals(1, accountListByClientId.size());
            Account account = accountListByClientId.get(0);
            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, ACCOUNT_1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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