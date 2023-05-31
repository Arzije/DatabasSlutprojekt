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
    private String SSN;

    public Transaction(String message, BigDecimal amount, int fromAccount, int toAccount, String SSN) {
        //id
        this.message = message;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.SSN = SSN;
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
    } //ta bort ??

    public void setCreated(Timestamp created) {
        this.created = created;
    } //ta bort ??

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

    public String getSSN() {
        return SSN;
    }
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }
}


