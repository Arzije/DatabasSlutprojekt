package org.arzijeziberovska.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends DatabaseConnection {

    public DatabaseHandler() {

    }

    public void createTableUser() {
        try {
            Connection connection = super.getConnection();

            String query = "CREATE TABLE IF NOT EXISTS user " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "password VARCHAR(96), " +
                    "email VARCHAR(255), " +
                    "phonenumber VARCHAR(15), " +
                    "address VARCHAR(255), " +
                    "name VARCHAR(255), " +
                    "SSN VARCHAR(13));";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void createTableAccount() {
        try {
            Connection connection = super.getConnection();

            String query = "CREATE TABLE IF NOT EXISTS account " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "balance DECIMAL(19,2), " +
                    "created DATETIME, " +
                    "user_id INT, " +
                    "account_name VARCHAR(255), " +
                    "account_number VARCHAR(100), " +
                    "SSN VARCHAR(13), " +
                    "FOREIGN KEY (user_id) REFERENCES user(id));";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void createTableTransaction() {
        try {
            Connection connection = super.getConnection();

            String query = "CREATE TABLE IF NOT EXISTS transaction " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "message VARCHAR(255), " +
                    "created DATETIME, " +
                    "amount DECIMAL(19,2), " +
                    "to_account VARCHAR(100), " +
                    "from_account VARCHAR(100), " +
                    "SSN VARCHAR(13));";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}

