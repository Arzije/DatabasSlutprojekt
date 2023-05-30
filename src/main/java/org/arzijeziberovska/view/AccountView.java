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
                    System.out.println("Accounts //LISTA");
                    AccountRepository accountRepository = new AccountRepository();
                    AccountService accountService3 = new AccountService(authenticatedUser, accountRepository);
                    accountService3.showUserAndAccounts();
                    whileTrue = false;
                    break;
                case "2":
                    TransactionService transactionService = new TransactionService(authenticatedUser);
                    TransactionRepository transactionRepository = new TransactionRepository(authenticatedUser, transactionService);
                    transactionRepository.getBalanceByAccountNumber();

                    break;
                case "3":
                    AccountRepository accountRepository2 = new AccountRepository();
                    AccountService accountService1 = new AccountService(authenticatedUser, accountRepository2);
                    accountService1.createAccount();
                    break;
                case "4":
                    AccountRepository accountRepository3 = new AccountRepository();
                    AccountService accountService2 = new AccountService(authenticatedUser, accountRepository3);
                    AccountRepository accountRepository4 = new AccountRepository(authenticatedUser, accountService2);
                    accountRepository4.selectAccountByAccountNumber();
                    break;
                case "5":
                    System.out.println("See sent transactions");
                    TransactionService transactionService1 = new TransactionService(authenticatedUser);
                    TransactionRepository transactionRepository1 = new TransactionRepository(authenticatedUser, transactionService1);
                    transactionService1.showSentTransactions();
                    break;
                case "6":
                    System.out.println("See received transactions");
                    TransactionService transactionService2 = new TransactionService(authenticatedUser);
                    TransactionRepository transactionRepository2 = new TransactionRepository(authenticatedUser, transactionService2);
                    transactionService2.showReceivedTransactions();
                    break;

            }
        }

    }

}

