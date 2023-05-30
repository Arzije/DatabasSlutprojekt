package org.arzijeziberovska.model;

import org.arzijeziberovska.database.DatabaseConnection;


import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction extends DatabaseConnection {

    private int id;
    private String message;
    private Timestamp created;
    private BigDecimal amount;
    private int fromAccount;
    private int toAccount;

    public Transaction(int id, String message, Timestamp created, BigDecimal amount, int fromAccount, int toAccount) {
        this.id = id;
        this.message = message;
        this.created = created;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(int fromAccount) {
        this.fromAccount = fromAccount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }
}

