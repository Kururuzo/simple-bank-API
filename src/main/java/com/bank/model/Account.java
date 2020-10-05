package com.bank.model;

import java.util.Objects;

public class Account extends AbstractEntity{
    private Client client;
    private String number;
    private Double amount;
    private String currency;

    public Account() {
    }

    public Account(Client client, String number, Double amount, String currency) {
        this.client = client;
        this.number = number;
        this.amount = amount;
        this.currency = currency;
    }

    public Account(Integer id, Client client, String number, Double amount, String currency) {
        super(id);
        this.client = client;
        this.number = number;
        this.amount = amount;
        this.currency = currency;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Account{" +
                "client=" + client +
                ", number='" + number + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.amount, amount) == 0 &&
                Objects.equals(client, account.client) &&
                Objects.equals(number, account.number) &&
                Objects.equals(currency, account.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, number, amount, currency);
    }
}
