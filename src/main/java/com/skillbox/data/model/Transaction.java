package com.skillbox.data.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction implements Serializable {
    private Long id;
    private Long accountId;
    private LocalDate date;
    private double amount;
    private String category;
    private String description;


    public Transaction(Long id, Long accountId, LocalDate date,
                       double amount, String category, String description) {
        this.id = id;
        this.accountId = accountId;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }


    public Long getId() { return id; }
    public Long getAccountId() { return accountId; }
    public LocalDate getDate() { return date; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Transaction{id=%d, accountId=%d, date=%s, amount=%.2f, category='%s'}",
                id, accountId, date, amount, category);
    }
}