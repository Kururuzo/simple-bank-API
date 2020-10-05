package com.bank;

import com.bank.model.Account;
import com.bank.model.Client;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClientTestData {
    public static final TestMatcher<Client> CLIENTS_MATCHER =
            TestMatcher.usingFieldsComparator(Client.class,  "registered", "accounts");

    public static final int CLIENT_1_ID = 100000;
    public static final int CLIENT_2_ID = 100001;
    public static final int CLIENT_3_ID = 100006;

    public static final Client CLIENT_1 =
            new Client(CLIENT_1_ID, "Vasay", "vasyaTheGreat@mail.ru",
                    Arrays.asList(new Account(100002, null, "1111111111", 1000d, "RUB")));
//    public static final Client CLIENT_1 = new Client(CLIENT_1_ID, "Vasay", "vasyaTheGreat@mail.ru");
    public static final Client CLIENT_2 = new Client(CLIENT_2_ID, "Petya", "petayTheBest@yandex.ru");
    public static final Client CLIENT_3 = new Client(CLIENT_3_ID, "Grisha", "grishaTheAdmin@gmail.ru");

    public static final List<Client> CLIENTS = Arrays.asList(CLIENT_1, CLIENT_2);


    public static final Comparator<Client> clientComparator = new Comparator<Client>() {
        @Override
        public int compare(Client o1, Client o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };
}
