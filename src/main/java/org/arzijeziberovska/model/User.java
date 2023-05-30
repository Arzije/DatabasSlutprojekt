package org.arzijeziberovska.model;

import org.arzijeziberovska.database.DatabaseConnection;

public class User extends DatabaseConnection {

    private int id;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String name;
    private String SSN;

    public User(){

    }

    public User(String password, String email, String phoneNumber, String address, String name, String SSN) {
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.name = name;
        this.SSN = SSN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }


}

