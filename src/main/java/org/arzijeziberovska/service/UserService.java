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

    public UserService(User authenticatedUser, UserRepository userRepository) {
        this.authenticatedUser = authenticatedUser;
        this.userRepository = userRepository;
    }

    public void createUser() throws SQLException, IOException {
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

            if (userRepository.getUserBySSN(SSN) != null) {
                System.out.println("User with SSN " + SSN + " already exists");
                System.out.println("");
            } else {
                User newUser = new User(PasswordService.Hash(password), email, phone, address, name, SSN);
                System.out.println("i create newUser: " + newUser);
                userRepository.saveUser(newUser);
                System.out.println("Insert completed");
            }
    }

    public void updateUserInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a new password (leave blank to keep the existing)");
        String newPassword = scanner.nextLine();

        System.out.println("Enter an email of choice (leave blank to keep the existing)");
        String email = scanner.nextLine();

        System.out.println("Enter your phone number (leave blank to keep the existing)");
        String phone = scanner.nextLine();

        System.out.println("Enter your address (leave blank to keep the existing)");
        String address = scanner.nextLine();

        System.out.println("Enter your first and last name (leave blank to keep the existing)");
        String name = scanner.nextLine();

        User updatedUser = new User(authenticatedUser.getPassword(), authenticatedUser.getEmail(),
                authenticatedUser.getPhoneNumber(), authenticatedUser.getAddress(), authenticatedUser.getName(),
                authenticatedUser.getSSN());

        if (!newPassword.isEmpty()) {
            String hashedPassword = PasswordService.Hash(newPassword);
            updatedUser.setPassword(hashedPassword);
        }

        if (!email.isEmpty()) {
            updatedUser.setEmail(email);
        }

        if (!phone.isEmpty()) {
            updatedUser.setPhoneNumber(phone);
        }

        if (!address.isEmpty()) {
            updatedUser.setAddress(address);
        }

        if (!name.isEmpty()) {
            updatedUser.setName(name);
        }

        userRepository.updateUser(updatedUser);
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

        if (user == null) {
            return false;
        }

        String hashedPassword = user.getPassword();
        return PasswordService.Verify(password, hashedPassword);
    }
}





