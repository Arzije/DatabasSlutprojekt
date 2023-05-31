package org.arzijeziberovska.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler extends DatabaseConnection {

    public DatabaseHandler() {
        createTableUser();
        createTableAccount();
        createTableTransaction();
    }

    public void createTableUser() {
        try {
            Connection connection = super.getConnection();
            Statement statement = connection.createStatement();

            String query = "CREATE TABLE IF NOT EXISTS user " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT);";

            int result = statement.executeUpdate(query);
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Table created successfully");
        }
    }

    public void createTableAccount() {
        try {
            Connection connection = super.getConnection();
            Statement statement = connection.createStatement();

            String query = "CREATE TABLE IF NOT EXISTS account " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT);";

            int result = statement.executeUpdate(query);
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Table created successfully");
        }
    }

    public void createTableTransaction() {
        try {
            Connection connection = super.getConnection();
            Statement statement = connection.createStatement();

            String query = "CREATE TABLE IF NOT EXISTS transaction " +
                    "(id INT PRIMARY KEY AUTO_INCREMENT);";

            int result = statement.executeUpdate(query);
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Table created successfully");
        }
    }
}

