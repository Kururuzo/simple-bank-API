package com.bank;

import com.bank.model.Account;

import static com.bank.ClientTestData.*;

public class AccountTestData {
    public static TestMatcher<Account> ACCOUNT_MATCHER_WITHOUT_CLIENT =
            TestMatcher.usingFieldsComparator(Account.class, "client");

    public static final Account ACCOUNT_1 =
            new Account(100002, CLIENT_1, "1111111111", 1000d, "RUB");

}
