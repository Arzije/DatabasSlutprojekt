package org.arzijeziberovska.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler extends DatabaseConnection {

    public DatabaseHandler() throws SQLException {
        createTableUser();
        createTableAccount();
        createTableTransaction();
    }

    public void createDatabase() throws SQLException { // SEN
        try {
            Connection connection = super.getConnection();

            String query = "CREATE DATABASE IF NOT EXISTS final_database;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            int result = preparedStatement.executeUpdate(query);
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Database created successfully");
        }
    }

    public void createTableUser() throws SQLException {
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

    public void createTableAccount() throws SQLException {
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

    public void createTableTransaction() throws SQLException {
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

