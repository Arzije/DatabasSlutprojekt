package org.arzijeziberovska.repository;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.User;

import java.sql.*;

public class UserRepository extends DatabaseConnection {

    public UserRepository() {
    }

    //sparar användare i databasen
    public void saveUser(User user) {
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

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("""
                    
                    Insert completed""");
        }
    }

    //uppdaterar användarinfo
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

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //hämtar användare från databasen baserat på SSN
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

            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    //tar bort användare och alla konton som tillhör användaren
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

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("""
                    Deletion completed
                    """);
        }
    }

    public Object getUserByEmail(String email) {
        try {
            Connection connection = getConnection();

            String query = "SELECT * FROM user WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phonenumber");
                String address = resultSet.getString("address");
                String ssn = resultSet.getString("SSN");

                return new User(password, email, phoneNumber, address, name, ssn);
            }

            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
