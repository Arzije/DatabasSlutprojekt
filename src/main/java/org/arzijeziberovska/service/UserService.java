package org.arzijeziberovska.service;


import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.UserRepository;

import java.io.IOException;
import java.util.Scanner;
import java.sql.*;

public class UserService extends DatabaseConnection {
    private UserRepository userRepository;
    private User authenticatedUser;

    public UserService() {

    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;


    }

    public UserService(User authenticatedUser, UserRepository userRepository) {
        this.authenticatedUser = authenticatedUser;
        this.userRepository = userRepository;
    }

    /*
    createUser(User user): Skapar en ny användare.
    deleteUser(int userId): Tar bort en användare baserat på användar-ID.
    updateUser(User user): Uppdaterar användaruppgifter för en befintlig användare.
    getUserById(int userId): Hämtar en användare baserat på användar-ID.
    getAllUsers(): Hämtar alla användare i systemet.
     */

    public void createUser() {
        try {
            Connection connection = super.getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your first and last name");
            String name = scanner.nextLine();
            System.out.println("Enter your SSN");
            String SSN = scanner.nextLine();
            System.out.println("Enter an email of choice");
            String email = scanner.nextLine();
            System.out.println("Enter your address");
            String address = scanner.nextLine();
            System.out.println("Enter your phone number");
            String phone = scanner.nextLine();
            System.out.println("Enter a password");
            String password = scanner.nextLine();

            String query = "INSERT IGNORE INTO user " +
                    "(password, email, phonenumber, address, name, SSN) " +
                    "VALUES (?, ?, ?, ?, ?, ?);"; // ta bort detta görs redan i saveUser metoden

            // if () email exists, print choose another email adress

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, PasswordService.Hash(password));
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, name);
            preparedStatement.setString(6, SSN); // ta bort görs redan i saveUser metoden

            System.out.println(" i create preparedStatement: " + preparedStatement);

            int result = preparedStatement.executeUpdate();
            System.out.println("Result: " + result);

            User newUser = new User(password, email, phone, address, name, SSN);
            System.out.println("i create newUser: " + newUser);
            userRepository.saveUser(newUser);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Insert completed");
        }
    }

//    public void updateUserInfo(){
//        try {
//            Connection connection = super.getConnection();
//
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("Enter your first and last name");
//            String name = scanner.nextLine();
//            System.out.println("Enter an email of choice");
//            String email = scanner.nextLine();
//            System.out.println("Enter your address");
//            String address = scanner.nextLine();
//            System.out.println("Enter your phone number");
//            String phone = scanner.nextLine();
//            System.out.println("Enter a password");
//            String password = scanner.nextLine();
//
//            String query = "UPDATE user SET password = ?, email = ?, phonenumber = ?, address = ?, name = ? WHERE SSN = ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//            preparedStatement.setString(1, PasswordService.Hash(password));
//            preparedStatement.setString(2, email);
//            preparedStatement.setString(3, phone);
//            preparedStatement.setString(4, address);
//            preparedStatement.setString(5, name);
//
//
//            String ssn = authenticatedUser.getSSN();
//            preparedStatement.setString(6, ssn);
//
//            int result = preparedStatement.executeUpdate();
//            System.out.println("Result: " + result);
//
//            User updatedUser = new User(password, email, phone, address, name, ssn);
//            userRepository.updateUser(updatedUser);
//
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        } finally {
//            System.out.println("Update completed");
//
//        }
//    }



    public void updateUserInfo() {
        try {
            Connection connection = super.getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter a new password (leave blank to keep the existing password)");
            String newPassword = scanner.nextLine();

            System.out.println("Enter an email of choice");
            String email = scanner.nextLine();

            System.out.println("Enter your phone number");
            String phone = scanner.nextLine();

            System.out.println("Enter your address");
            String address = scanner.nextLine();

            System.out.println("Enter your first and last name");
            String name = scanner.nextLine();


            String query;
            PreparedStatement preparedStatement;

            if (!newPassword.isEmpty()) {
                query = "UPDATE user SET password = ?, email = ?, phonenumber = ?, address = ?, name = ? WHERE SSN = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, PasswordService.Hash(newPassword));
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, phone);
                preparedStatement.setString(4, address);
                preparedStatement.setString(5, name);
                preparedStatement.setString(6, authenticatedUser.getSSN());

                System.out.println("newPassword: " + newPassword);
                System.out.println("email: " + email);
                System.out.println("phone: " + phone);
                System.out.println("address: " + address);
                System.out.println("name: " + name);
                System.out.println("SSN: " + authenticatedUser.getSSN());

            } else {
                query = "UPDATE user SET email = ?, phonenumber = ?, address = ?, name = ? WHERE SSN = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, phone);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, name);
                preparedStatement.setString(5, authenticatedUser.getSSN());
            }

            int result = preparedStatement.executeUpdate();
            System.out.println("Result: " + result);

            User updatedUser;
            if (!newPassword.isEmpty()) {
                updatedUser = new User(PasswordService.Hash(newPassword), email, phone, address, name, authenticatedUser.getSSN());
            } else {
                updatedUser = new User(authenticatedUser.getPassword(), email, phone, address, name, authenticatedUser.getSSN());
            }
            userRepository.updateUser(updatedUser);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Update completed");
        }
    }

    public void deleteUserAndAccounts(){
        try {
            Connection connection = super.getConnection();

            String deleteAccountQuery = "DELETE FROM account WHERE SSN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteAccountQuery);
            preparedStatement.setString(1, authenticatedUser.getSSN());


            String deleteUserQuery = "DELETE FROM user WHERE SSN = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(deleteUserQuery);
            preparedStatement1.setString(1, authenticatedUser.getSSN());

            int result = preparedStatement.executeUpdate();
            System.out.println("Result: " + result);

            userRepository.deleteUser(authenticatedUser);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Delete completed");
        }
    }




    public boolean verifyUserCredentials(String password, String ssn) {
        User user = userRepository.getUserBySSN(ssn);
        System.out.println("i verifyUserCredentials ssn: " + ssn);

        if (user == null) {
            return false; // Användaren hittades inte
        }

        String hashedPassword = user.getPassword();
        return PasswordService.Verify(password, hashedPassword);
    }













//    public void getUserById(int userId) {
//        try {
//            Connection connection = super.getConnection();
//
//            String query = "SELECT * FROM user WHERE id = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, userId);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String SSN = resultSet.getString("SSN");
//                String email = resultSet.getString("email");
//                String address = resultSet.getString("address");
//                String phone = resultSet.getString("phonenumber");
//                String password = resultSet.getString("password");
//
//                User user = new User(password, email, phone, address, name, SSN);
//                System.out.println(user);
//            }
//
//            resultSet.close();
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }

//    public User getUserBySSN(String ssn) {
//        try {
//            Connection connection = super.getConnection();
//
//            String query = "SELECT * FROM user WHERE SSN = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, ssn);
//            System.out.println("SSN: " + ssn);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String SSN = resultSet.getString("SSN");
//                String email = resultSet.getString("email");
//                String address = resultSet.getString("address");
//                String phone = resultSet.getString("phonenumber");
//                String password = resultSet.getString("password");
//                System.out.println("getUserBySSN metoden: " + address);
//
//                User user = new User(password, email, phone, address, name, SSN);
//                System.out.println("getUserBySSN metoden: " +user);
//                return user;
//            }
//
//            resultSet.close();
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//        return null;
//    }
//
//    public User getUserByEmail(String email) {
//        try {
//            Connection connection = super.getConnection();
//
//            String query = "SELECT * FROM user WHERE email = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, email);
//            System.out.println("Email: " + email);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String SSN = resultSet.getString("SSN");
//                String email1 = resultSet.getString("email");
//                String address = resultSet.getString("address");
//                String phone = resultSet.getString("phonenumber");
//                String password = resultSet.getString("password");
//                System.out.println("getUserByEmail metoden: " + address);
//
//                User user = new User(password, email1, phone, address, name, SSN);
//                System.out.println("getUserByEmail metoden: " +user);
//            }
//
//            resultSet.close();
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//        return null;
//    }
//
//
//
//        public void getAllUsers(){
//            try {
//                Connection connection = super.getConnection();
//
//                String query = "SELECT * FROM user";
//                PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//                ResultSet resultSet = preparedStatement.executeQuery();
//
//                while (resultSet.next()) {
//                    String name = resultSet.getString("name");
//                    String SSN = resultSet.getString("SSN");
//                    String email = resultSet.getString("email");
//                    String address = resultSet.getString("address");
//                    String phone = resultSet.getString("phonenumber");
//                    String password = resultSet.getString("password");
//
//                    User user = new User(password, email, phone, address, name, SSN);
//                    System.out.println(user);
//                }
//
//                resultSet.close();
//                connection.close();
//            } catch (SQLException e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
}





