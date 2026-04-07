package com.skillbox.data.model;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    private final Long id;
    private String name;
    private double balance;
    private String currency;

    public Account(Long id, String name, double balance) {
        this(id, name, balance, "RUB");
    }

    public Account(Long id, String name, double balance, String currency) {
        if (id == null) throw new IllegalArgumentException("ID cannot be null");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty");
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");

        this.id = id;
        this.name = name;
        this.balance = balance;
        this.currency = currency != null ? currency : "RUB";
    }


    public Long getId() { return id; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public String getCurrency() { return currency; }


    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }

    public void setBalance(double balance) {
        if (balance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency != null ? currency : "RUB";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Account{id=%d, name='%s', balance=%.2f %s}",
                id, name, balance, currency);
    }
}