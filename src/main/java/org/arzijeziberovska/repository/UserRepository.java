package org.arzijeziberovska.repository;


import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.User;

import java.io.IOException;
import java.sql.*;

public class UserRepository extends DatabaseConnection {

    private User authenticatedUser;

    public UserRepository() {

    }

    public void saveUser(User user) throws SQLException, IOException {
        try {
            Connection connection = super.getConnection();

            String query = "INSERT INTO user " +
                    "(password, email, phonenumber, address, name, SSN) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getSSN());

            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("""
                    
                    Insert completed""");
        }
    }

    public void updateUser(User user) {
        try {
            Connection connection = super.getConnection();

            String query;
            PreparedStatement preparedStatement;
            if (!user.getPassword().isEmpty()) {
                query = "UPDATE user SET password = ?, email = ?, phonenumber = ?, address = ?, name = ? WHERE SSN = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, user.getPassword());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPhoneNumber());
                preparedStatement.setString(4, user.getAddress());
                preparedStatement.setString(5, user.getName());
                preparedStatement.setString(6, user.getSSN());
            } else {
                query = "UPDATE user SET email = ?, phonenumber = ?, address = ?, name = ? WHERE SSN = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getPhoneNumber());
                preparedStatement.setString(3, user.getAddress());
                preparedStatement.setString(4, user.getName());
                preparedStatement.setString(5, user.getSSN());
            }

            preparedStatement.executeUpdate();
            System.out.println("""
                    Your information has been updated""");

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public User getUserBySSN(String ssn) {
        try {
            Connection connection = getConnection();

            String query = "SELECT * FROM user WHERE SSN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ssn);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phonenumber");
                String address = resultSet.getString("address");

                return new User(password, email, phoneNumber, address, name, ssn);
            }

            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public void deleteUser(User authenticatedUser) { //används inte
        try {
            Connection connection = super.getConnection();

            String query = "DELETE FROM user WHERE SSN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, authenticatedUser.getSSN());

            int result = preparedStatement.executeUpdate();
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteUserAndAccounts(User authenticatedUser) {
        try {
            Connection connection = super.getConnection();

            String deleteAccountQuery = "DELETE FROM account WHERE SSN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAccountQuery);
            preparedStatement.setString(1, authenticatedUser.getSSN());

            String deleteUserQuery = "DELETE FROM user WHERE SSN = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(deleteUserQuery);
            preparedStatement1.setString(1, authenticatedUser.getSSN());

            preparedStatement.executeUpdate();

            preparedStatement1.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("""
                    Deletion completed
                    """);
        }
    }


}
