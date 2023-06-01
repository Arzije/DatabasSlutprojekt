package org.arzijeziberovska.view;


import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.AccountRepository;
import org.arzijeziberovska.repository.TransactionRepository;
import org.arzijeziberovska.service.AccountService;
import org.arzijeziberovska.service.TransactionService;

import java.sql.SQLException;
import java.util.Scanner;


public class AccountView {
    private User authenticatedUser;
    private AccountService accountService;
    private AccountRepository accountRepository;
    private Scanner scanner;

    public AccountView(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }
    public void showAccountView() throws SQLException {

        boolean whileTrue = true;

        while (whileTrue){
            scanner = new Scanner(System.in);
            System.out.println("""
                    Welcome to your account!
                    
                    Choose from the menu below:
                    1. See User, Accounts and balance
                    2. Make transaction
                    3. Create account
                    4. Delete account
                    5. See sent transactions
                    6. See received transactions
                    7. Logout
                    """);

            switch (scanner.nextLine().trim()){
                case "1":
                    AccountRepository accountRepository = new AccountRepository();
                    AccountService accountService3 = new AccountService(authenticatedUser, accountRepository);
                    accountService3.showUserAndAccounts();
                    whileTrue = false;
                    break;
                case "2":
                    TransactionRepository transactionRepository = new TransactionRepository();
                    TransactionService transactionService = new TransactionService(authenticatedUser, transactionRepository);
                    transactionService.transferMoney();

                    break;
                case "3":
                    AccountRepository accountRepository2 = new AccountRepository();
                    AccountService accountService1 = new AccountService(authenticatedUser, accountRepository2);
                    accountService1.createAccount();
                    break;
                case "4":
                    AccountRepository accountRepository3 = new AccountRepository();
                    AccountService accountService2 = new AccountService(authenticatedUser, accountRepository3);
                    accountService2.deleteAccount();
                    break;
                case "5":
                    TransactionRepository transactionRepository3 = new TransactionRepository();
                    TransactionService transactionService1 = new TransactionService(authenticatedUser, transactionRepository3);
                    transactionService1.showSentTransactions();
                    break;
                case "6":
                    TransactionRepository transactionRepository4 = new TransactionRepository();
                    TransactionService transactionService2 = new TransactionService(authenticatedUser, transactionRepository4);
                    transactionService2.showReceivedTransactions();
                    break;

            }
        }

    }

}

