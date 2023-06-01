package org.arzijeziberovska.view;


import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.UserRepository;
import org.arzijeziberovska.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class InitialView {
    private Scanner scanner;
    private UserService userService;
    private User authenticatedUser;
    private UserRepository userRepository;

    public InitialView(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
;    }

    public void firstView() {
        boolean whileTrue = true;

        while (whileTrue) {
            scanner = new Scanner(System.in);

            System.out.println("""
                    Welcome to Swosh!
                    
                    Choose from the menu below:
                    1. Create user account.
                    2. Login to an existing account.
                    3. Quit.
                    """);

            switch (scanner.nextLine().trim()) {
                case "1":
                    createUser();
                    break;

                case "2":
                    authenticatedUser = getAuthenticatedUser();
                    UserService userService1 = new UserService(userRepository);
                    UserView userView = new UserView(authenticatedUser, userService1);
                    userView.userView();
                    whileTrue = false;
                    break;

                case "3":
                    whileTrue = false;

                default:
                    break;
            }
        }
    }

    //tar in input från användaren och skapar en ny användare
    private void createUser() {
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

        userService.createUser(name, SSN, email, address, phone, password);
    }

    //tar in input från användaren och autentiserar användaren
    public User getAuthenticatedUser() {
        boolean authenticated = false;
        User authenticatedUser = null;

        while (!authenticated) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your SSN:");
            String ssn = scanner.nextLine();

            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            authenticatedUser = userService.authenticateUser(ssn, password);

            if (authenticatedUser != null) {
                authenticated = true;
            }
        }
        return authenticatedUser;
    }
}

