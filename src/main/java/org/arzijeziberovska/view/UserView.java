package org.arzijeziberovska.view;

import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.AccountRepository;
import org.arzijeziberovska.repository.TransactionRepository;
import org.arzijeziberovska.repository.UserRepository;
import org.arzijeziberovska.service.AccountService;
import org.arzijeziberovska.service.TransactionService;
import org.arzijeziberovska.service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserView {
    private User authenticatedUser;
    private UserService userService;
    private Scanner scanner;

    public UserView(User authenticatedUser, UserService userService) {
        this.authenticatedUser = authenticatedUser;
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("User authentication failed. Please make sure the authentication process returns a valid User object.");
        }
        this.userService = userService;
    }

    public void userView() {
        boolean whileTrue = true;

        while (whileTrue){
            scanner = new Scanner(System.in);
            System.out.println("""
                Choose from the menu below:
                    1. Accounts
                    2. Update user info
                    3. Delete user
                    4. Log out        
                """);

            switch (scanner.nextLine().trim()){
                case "1":
                    AccountRepository accountRepository = new AccountRepository();
                    TransactionRepository transactionRepository1 = new TransactionRepository();
                    TransactionService transactionService = new TransactionService(transactionRepository1);
                    TransactionRepository transactionRepository = new TransactionRepository();
                    AccountService accountService = new AccountService(authenticatedUser, accountRepository);
                    AccountView accountView = new AccountView(authenticatedUser, accountRepository, transactionService, transactionRepository, accountService);
                    accountView.showAccountView();
                    whileTrue = false;
                    break;
                case "2":
                    updateUserInfo();
                    break;
                case "3":
                    UserRepository userRepository3 = new UserRepository();
                    userRepository3.deleteUserAndAccounts(authenticatedUser);
                    break;
                case "4":
                    System.out.println("You have been logged out!");
                    whileTrue = false;
                    break;
            }
        }
    }

    //tar in input från användaren och skickar till userService för att uppdatera användarens info
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

        userService.updateUserInfo(authenticatedUser, newPassword, email, phone, address, name);
    }

}





