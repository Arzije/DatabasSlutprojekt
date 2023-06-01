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
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticateUser(String ssn, String password) {
        User user = userRepository.getUserBySSN(ssn);

        if (user != null && verifyUserCredentials(password, user.getPassword())) {
            authenticatedUser = user;
            System.out.println("""
                    You are now logged in!
                    """);
            return authenticatedUser;
        } else {
            System.out.println("Wrong credentials! Please try again.");
        }
        return null;
    }

    private boolean verifyUserCredentials(String password, String hashedPassword) {
        return PasswordService.Verify(password, hashedPassword);
    }

        public void createUser(String name, String SSN, String email, String address, String phone, String password) {

            if (userRepository.getUserBySSN(SSN) != null) {
                System.out.println("""
                        User with SSN " + SSN + " already exists
                        
                        """);

            } else {
                User newUser = new User(PasswordService.Hash(password), email, phone, address, name, SSN);
                userRepository.saveUser(newUser);
                System.out.println("");
            }
        }

    public void updateUserInfo(User authenticatedUser, String newPassword, String email, String phone, String address, String name) {
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
}





