package com.bank.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Client extends AbstractEntity {
    private String name;
    private String email;
    private Date registered = new Date();

    public Client() {
    }

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Client(String name, String email, List<Account> accounts) {
        this.name = name;
        this.email = email;
        this.accounts = accounts;
    }
    public Client(int id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }

    public Client(int id, String name, String email, List<Account> accounts) {
        super(id);
        this.name = name;
        this.email = email;
        this.accounts = accounts;
    }

    private List<Account> accounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", registered=" + registered +
                ", accounts=" + accounts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) &&
                Objects.equals(email, client.email) &&
                Objects.equals(registered, client.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, registered);
    }
}
