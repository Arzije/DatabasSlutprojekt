package org.arzijeziberovska.view;

import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.UserRepository;
import org.arzijeziberovska.service.AuthenticateUser;
import org.arzijeziberovska.service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserView {
    private AuthenticateUser authenticateUser;
    private User authenticatedUser;
    private Scanner scanner;

    public UserView(AuthenticateUser authenticateUser) throws SQLException {
//        this.authenticateUser = authenticateUser;
        this.authenticatedUser = authenticateUser.authenticate();
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("User authentication failed. Please make sure the authentication process returns a valid User object.");
        }
    }

    public void userView() throws SQLException {
//        authenticatedUser = authenticateUser.authenticate();

        boolean whileTrue = true;

        while (whileTrue){
            scanner = new Scanner(System.in);
            System.out.println("""
                This is userView.
                Choose from the menu below:
                    1. Accounts
                    -           . Create account // ta bort dessa två
                    -           . Delete account
                    2. Update user info
                    3. Delete user
                    4. Quit         
                """);

            switch (scanner.nextLine().trim()){
                case "1":
//                AccountRepository accountRepository = new AccountRepository();
//                AccountService accountService3 = new AccountService(authenticatedUser, accountRepository);
//                accountService3.showAccounts();
//                whileTrue = false;
                    AccountView accountView = new AccountView(authenticatedUser);
                    accountView.showAccountView();
                    whileTrue = false;
                    break;
//            case "2":
//                AccountRepository accountRepository2 = new AccountRepository();
//                AccountService accountService1 = new AccountService(authenticatedUser, accountRepository2);
//                accountService1.createAccount();
//                break;
//            case "3":
//                AccountRepository accountRepository3 = new AccountRepository();
//                AccountService accountService2 = new AccountService(authenticatedUser, accountRepository3);
//                AccountRepository accountRepository4 = new AccountRepository(authenticatedUser, accountService2);
//                accountRepository4.selectAccountByAccountNumber();
//                break;
                case "2":
                    UserRepository userRepository2 = new UserRepository();
                    UserService userService = new UserService(authenticatedUser, userRepository2);
                    userService.updateUserInfo();

                    break;
                case "3":
                    UserRepository userRepository3 = new UserRepository();
                    UserService userService2 = new UserService(authenticatedUser, userRepository3);
                    userService2.deleteUserAndAccounts();
                    break;
                case "4":
                    whileTrue = false;

                    break;
            }

        }
    }
}


//        switch (userService.getUserInput()){
//            case "1":
//                accountService.showAccountsBySSN();
//                break;
//            case "2":
//                accountService.createAccount();
//                break;
//            case "3":
//                accountService.deleteAccount();
//                break;
//            case "4":
//                userService.updateUserInfo();
//                break;
//            case "5":
//                userService.deleteUser();
//                break;
//            case "6":
//                authenticateUser.authenticate();
//                break;
//        }




