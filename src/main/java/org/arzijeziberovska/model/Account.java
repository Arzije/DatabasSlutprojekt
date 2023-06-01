package org.arzijeziberovska.model;

import org.arzijeziberovska.database.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account extends DatabaseConnection {

    private int id;
    private BigDecimal balance;
    private Timestamp created;
    private int userId;
    private String accountName;
    private String accountNumber;
    private String SSN;

    public Account(BigDecimal balance, int userId, String accountName, String accountNumber, String SSN) {
        this.balance = balance;
        this.userId = userId;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.SSN = SSN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return String.valueOf(accountNumber);
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }
}

