//package org.arzijeziberovska.service;
//
//
//import org.arzijeziberovska.model.User;
//import org.arzijeziberovska.repository.UserRepository;
//
//import java.sql.SQLException;
//import java.util.Scanner;
//
//public class AuthenticateUser {
//    private UserService userService;
//    private UserRepository userRepository;
//
//    public AuthenticateUser(UserService userService, UserRepository userRepository) {
//        this.userService = userService;
//        this.userRepository = userRepository;
//    }
//
//    public User authenticate() throws SQLException {
//
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
//            authenticated = userService.verifyUserCredentials(password, ssn);
//
//            if (authenticated) {
//                System.out.println("""
//
//                        You are now logged in!
//                        """);
//                return userRepository.getUserBySSN(ssn);
//            } else {
//                System.out.println("Wrong credentials!");
//            }
//        }
//        return null;
//    }
//}
//
//
//
//
