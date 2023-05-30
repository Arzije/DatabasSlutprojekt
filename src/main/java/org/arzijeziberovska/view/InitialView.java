package org.arzijeziberovska.view;


import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.UserRepository;
import org.arzijeziberovska.service.AccountService;
import org.arzijeziberovska.service.AuthenticateUser;
import org.arzijeziberovska.service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class InitialView {
    private Scanner scanner;
    private User authenticatedUser;

    public InitialView() throws SQLException {
        firstView();
    }

    public InitialView(User authenticatedUser) throws SQLException {
        this.authenticatedUser = authenticatedUser;
        firstView();
    }

    public void firstView() throws SQLException {

        boolean whileTrue = true;

        while (whileTrue){
            scanner = new Scanner(System.in);

            System.out.println("""
                    Welcome to Swosh!

                    Choose from the menu below:
                    1. Create user account.
                    2. Login to existing account.
                    3. Quit.
                    """);

            switch (scanner.nextLine().trim()){
                case "1":
                    UserRepository userRepository1 = new UserRepository();
//                    UserService userService = new UserService(authenticatedUser, userRepository1);
                    UserService userService = new UserService(authenticatedUser, userRepository1);
                    userService.createUser();
                    break;

                case "2":
                    UserRepository userRepository4 = new UserRepository();
                    UserService userService1 = new UserService(authenticatedUser, userRepository4);
                    AuthenticateUser authenticateUser = new AuthenticateUser(userService1, userRepository4);
                    AccountService accountService4 = new AccountService(); //
//                    authenticateUser.authenticate();
                    UserView userView = new UserView(authenticateUser);//, accountService
//                    UserView userView = new UserView();
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
}

