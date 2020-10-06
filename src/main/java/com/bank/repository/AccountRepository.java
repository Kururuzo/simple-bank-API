package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.Client;
import com.bank.model.CreditCard;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {

    void addAccount(Client client, Account account) throws SQLException;

    List<Account> getAllClientAccounts(Client client) throws SQLException;

    Account getAccountByClientId(Client client) throws SQLException;

    void updateAccount(Client client, Account account);




//    BigDecimal checkBalanceByAccountNumber(String accountNumber) throws SQLException;
//    BigDecimal checkBalanceByAccountId(Integer accountId) throws SQLException;
//
//    boolean depositFunds(String accountNumber, BigDecimal amount) throws SQLException;
//
//    List<Account> getAccountListByClientId (Integer clientId) throws SQLException;
//
//    List<CreditCard> getCreditCardListByAccountId(Integer accountId) throws SQLException;
//
//
//    boolean addCreditCard(Integer accountId, String cardNumber) throws SQLException;
//
//    boolean isCardNumberExists(String cardNumber) throws SQLException;
//

}
