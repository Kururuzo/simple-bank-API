package com.bank.model;

import java.util.Date;
import java.util.Objects;

public class CreditCard extends AbstractEntity{
    private Account account;
    private String number;
    private Date registered = new Date();
    private Client client;

    public CreditCard() {
    }

    public CreditCard(Account account, String number) {
        this.account = account;
        this.number = number;
    }

    public CreditCard(Account account, String number, Client client) {
        this.account = account;
        this.number = number;
        this.client = client;
    }

    public CreditCard(Integer id, Account account, String number, Client client) {
        super(id);
        this.account = account;
        this.number = number;
        this.client = client;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "account=" + account +
                ", number='" + number + '\'' +
                ", registered=" + registered +
                ", client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(number, that.number) &&
                Objects.equals(registered, that.registered) &&
                Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, number, registered, client);
    }
}
