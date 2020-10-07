package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Client;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import static com.bank.AccountTestData.*;

public class AccountRepositoryImplTest {

    private static AccountRepository repository;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setup() {
        repository = new AccountRepositoryImpl(Utils.getDataSource());
        clientRepository = new ClientRepositoryImpl(Utils.getDataSource());
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
    public void addAccount(){
        try {
            Client client = Client.builder().id(100_006).name("Ivan").email("test@mail.ru").build();
            Account account =Account.builder()
                    .id(100003)
                    .client(client)
                    .number("40817810500550987654")
                    .amount(new BigDecimal(1000).setScale(2, BigDecimal.ROUND_CEILING))
                    .currency("RUB")
                    .build();
            repository.addAccount(client, account);
            Account account1 = repository.getAccountById(100003);
            Assert.assertEquals(account,account1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void getAllAccounts(){
        try {
            Client client = Client.builder().id(100006).name("Den").email("den@mail.ru").build();
            clientRepository.addClient(client);
            Account account = Account.builder()
                    .id(100007)
                    .number("111111")
                    .currency("RUB")
                    .amount(new BigDecimal(1000).setScale(2))
                    .client(client)
                    .build();
            Account account1 = Account.builder()
                    .id(100008)
                    .number("222222")
                    .currency("RUB")
                    .amount(new BigDecimal(2000))
                    .client(client)
                    .build();
            repository.addAccount(client, account);
            repository.addAccount(client, account1);
            List<Account> allClientAccounts = repository.getAllClientAccounts(client);
            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(allClientAccounts, account,account1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    public void  getAccById(){
        try {
            Account account = repository.getAccountById(100002);
            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account, ACCOUNT_1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void updateAccount() {

        try {
            Account account = ACCOUNT_1;
            account.setAmount(new BigDecimal(121212).setScale(2));
            repository.updateAccount(account);
            Account account1 = repository.getAccountById(ACCOUNT_1.getId());
            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(account,account1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void deleteAccount(){
        try{
            Client client = Client.builder().id(100006).name("Vlad").email("mqil@mail.ru").build();
            clientRepository.addClient(client);
            Account account = Account.builder().id(100003).number("123456").currency("RUB").amount(new BigDecimal(12).setScale(2)).build();
            Account account1 = Account.builder().id(100004).number("098765").currency("RUB").amount(new BigDecimal(1212).setScale(2)).build();
            repository.addAccount(client, account);
            repository.addAccount(client, account1);
            List<Account> accountList = repository.getAllClientAccounts(client);
            repository.deletAccount(account);
            List<Account> accountList1 = repository.getAllClientAccounts(client);
            ACCOUNT_MATCHER_WITHOUT_CLIENT.assertMatch(accountList,accountList1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

//    @Test
//    public void getCreditCardListByAccountId() {
//        try {
//            List<CreditCard> cardList = repository.getCreditCardListByAccountId(ACCOUNT_1.getId());
//            Assert.assertEquals(1, cardList.size());
//            Assert.assertEquals(cardList.get(0).getNumber(), CARD_1.getNumber());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void addCreditCard() {
//        try {
//            String newNumber = "0000000000";
//            repository.addCreditCard(ACCOUNT_1.getId(), newNumber);
//            List<CreditCard> cardList = repository.getCreditCardListByAccountId(ACCOUNT_1.getId());
//            Assert.assertEquals(2, cardList.size());
//            Collections.sort(cardList, CreditCardTestData.creditCardComparator);
//            Assert.assertEquals(newNumber, cardList.get(1).getNumber());
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    @Test
//    public void isCardNumberExists() {
//        try {
//            Assert.assertTrue(repository.isCardNumberExists(CARD_1.getNumber()));
//            Assert.assertFalse(repository.isCardNumberExists("00000000x"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
}