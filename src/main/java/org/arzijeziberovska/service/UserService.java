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

//    public User authenticateUser(User user, String password) {
//        boolean authenticated = false;
//
//        while (!authenticated) {
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("Enter your SSN:");
//            String ssn = scanner.nextLine();
//
//            System.out.println("Enter your password:");
//            String password = scanner.nextLine();
//
//            User user = userRepository.getUserBySSN(ssn);
//
//            String hashedPassword = user.getPassword();
//
//            if (user != null && PasswordService.Verify(password, hashedPassword)) {
//                authenticatedUser = user;
//                System.out.println("You are now logged in!");
//                return authenticatedUser;
//            } else {
//                System.out.println("Wrong credentials!");
//            }
////        }
//
//        return null;
//    }

//    public boolean verifyUserCredentials(String password, String ssn) { //använts ej
//        User user = userRepository.getUserBySSN(ssn);
//
//        if (user == null) {
//            return false;
//        }
//
//        String hashedPassword = user.getPassword();
//        return PasswordService.Verify(password, hashedPassword);
//    }

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

        public void createUser(String name, String SSN, String email, String address, String phone, String password)
                throws SQLException, IOException {

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

//    public void updateUserInfo() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter a new password (leave blank to keep the existing)");
//        String newPassword = scanner.nextLine();
//
//        System.out.println("Enter an email of choice (leave blank to keep the existing)");
//        String email = scanner.nextLine();
//
//        System.out.println("Enter your phone number (leave blank to keep the existing)");
//        String phone = scanner.nextLine();
//
//        System.out.println("Enter your address (leave blank to keep the existing)");
//        String address = scanner.nextLine();
//
//        System.out.println("Enter your first and last name (leave blank to keep the existing)");
//        String name = scanner.nextLine();
//
//        User updatedUser = new User(authenticatedUser.getPassword(), authenticatedUser.getEmail(),
//                authenticatedUser.getPhoneNumber(), authenticatedUser.getAddress(), authenticatedUser.getName(),
//                authenticatedUser.getSSN());
//
//        if (!newPassword.isEmpty()) {
//            String hashedPassword = PasswordService.Hash(newPassword);
//            updatedUser.setPassword(hashedPassword);
//        }
//
//        if (!email.isEmpty()) {
//            updatedUser.setEmail(email);
//        }
//
//        if (!phone.isEmpty()) {
//            updatedUser.setPhoneNumber(phone);
//        }
//
//        if (!address.isEmpty()) {
//            updatedUser.setAddress(address);
//        }
//
//        if (!name.isEmpty()) {
//            updatedUser.setName(name);
//        }
//
//        userRepository.updateUser(updatedUser);
//    }

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





