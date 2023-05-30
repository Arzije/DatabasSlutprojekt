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
    private int accountNumber;
    private String SSN;
    public Account(){

    }

//    public Account(BigDecimal balance, Timestamp created, int userId, String accountName, int accountNumber, String SSN) {
////        this.id = id;
//        this.balance = balance;
//        this.created = created;
//        this.userId = userId;
//        this.accountName = accountName;
//        this.accountNumber = accountNumber;
//        this.SSN = SSN;
//    }

    public Account(BigDecimal balance, int userId, String accountName, int accountNumber, String SSN) {
//        this.id = id;
        this.balance = balance;
//        this.created = created;
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

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSSN() {
        return SSN;
    }
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }
}

