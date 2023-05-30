package org.arzijeziberovska.repository;


import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.User;

import java.io.IOException;
import java.sql.*;

public class UserRepository extends DatabaseConnection {

    public void saveUser(User user) throws SQLException, IOException {
        try {
            Connection connection = super.getConnection();

            String query = "INSERT IGNORE INTO user " +
                    "(password, email, phonenumber, address, name, SSN) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getSSN());

            int result = preparedStatement.executeUpdate();
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Insert completed");
        }
    }

    public void updateUser(User user) {

        try {
            Connection connection = super.getConnection();

            String query = "UPDATE user SET password = ?, email = ?, phonenumber = ?, address = ?, name = ? WHERE SSN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setString(6, user.getSSN());

            System.out.println("preparedStatement: " + preparedStatement);

            int result = preparedStatement.executeUpdate();
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public User getUserBySSN(String ssn) {
        System.out.println("i getUserBySSN ssn: " + ssn);
        try {
            Connection connection = getConnection();

            System.out.println("");
            System.out.println("Här körs getUserBySSN som finns i UserRepo");
            System.out.println("");

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

    public void deleteUser(User authenticatedUser) {
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


//    public boolean verifyUserCredentials(String name, String ssn, String password) {
//        try {
//            Connection connection = getConnection();
//
//            String query = "SELECT * FROM user WHERE name = ? AND SSN = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, ssn);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                String hashedPassword = resultSet.getString("password");
//                return PasswordService.Verify(password, hashedPassword);
//            }
//
//            resultSet.close();
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//
//        return false;
//    }

//    public User getUserByNameAndSSN(String name, String ssn) {
//        try {
//            Connection connection = getConnection();
//
//            System.out.println("");
//            System.out.println("Här körs getUserByNameAndSSN som finns i UserRepo");
//            System.out.println("");
//
//            String query = "SELECT * FROM user WHERE name = ? AND SSN = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, ssn);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                String password = resultSet.getString("password");
//                String email = resultSet.getString("email");
//                String phoneNumber = resultSet.getString("phonenumber");
//                String address = resultSet.getString("address");
//
//                return new User(password, email, phoneNumber, address, name, ssn);
//            }
//
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//
//        return null;
//    }
//}



/*
public void saveUser(User user) {
        try {
            Connection connection = super.getConnection();

            String query = "INSERT IGNORE INTO user " +
                    "(password, email, phonenumber, address, name, SSN) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(3, user.getSSN());

            int result = preparedStatement.executeUpdate();
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Insert completed");
        }
    }
 */

}
