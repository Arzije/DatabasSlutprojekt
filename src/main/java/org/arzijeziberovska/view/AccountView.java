package org.arzijeziberovska.view;


import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Account;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.AccountRepository;
import org.arzijeziberovska.repository.TransactionRepository;
import org.arzijeziberovska.service.AccountService;
import org.arzijeziberovska.service.TransactionService;
import org.arzijeziberovska.service.UserService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class AccountView extends DatabaseConnection {
    private User authenticatedUser;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private TransactionService transactionService;
    private Scanner scanner;

    public AccountView(User authenticatedUser, AccountRepository accountRepository, TransactionService transactionService, TransactionRepository transactionRepository ) {
        this.authenticatedUser = authenticatedUser;
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }
    public void showAccountView() throws SQLException {

        boolean whileTrue = true;

        while (whileTrue){
            scanner = new Scanner(System.in);
            System.out.println("""
                    Welcome to your account!
                    
                    Choose from the menu below:
                    1. See user, accounts and balance
                    2. Make transaction
                    3. Create account
                    4. Delete account
                    5. See sent transactions
                    6. See received transactions
                    7. Logout
                    """);

            switch (scanner.nextLine().trim()){
                case "1":
//                    AccountRepository accountRepository = new AccountRepository();
//                    AccountService accountService3 = new AccountService(authenticatedUser, accountRepository);
//                    accountService3.
                            showUserAndAccounts();
                    whileTrue = false;
                    break;
                case "2":
//                    TransactionRepository transactionRepository = new TransactionRepository();
//                    TransactionService transactionService = new TransactionService(authenticatedUser, transactionRepository);
//                    transactionService.
                            transferMoney();

                    break;
                case "3":
//                    AccountRepository accountRepository2 = new AccountRepository();
//                    AccountService accountService1 = new AccountService(authenticatedUser, accountRepository2);
//                    accountService1.
                            createAccount();
                    break;
                case "4":
//                    AccountRepository accountRepository3 = new AccountRepository();
//                    AccountService accountService2 = new AccountService(authenticatedUser, accountRepository3);
//                    accountService2.
                            deleteAccount();
                    break;
                case "5":
//                    TransactionRepository transactionRepository3 = new TransactionRepository();
//                    TransactionService transactionService1 = new TransactionService(authenticatedUser, transactionRepository3);
//                    transactionService1.
                            showSentTransactions();
                    break;
                case "6":
//                    TransactionRepository transactionRepository4 = new TransactionRepository();
//                    TransactionService transactionService2 = new TransactionService(authenticatedUser, transactionRepository4);
//                    transactionService2.
                            showReceivedTransactions();
                    break;
                case "7":
                    System.out.println("You have been logged out!");
                    whileTrue = false;
                    break;

            }
        }

    }

    public void showUserAndAccounts() {
        String ssn = authenticatedUser.getSSN();
        List<String[]> accounts = accountRepository.getUserAccounts(ssn);

        if (!accounts.isEmpty()) {
            System.out.println("User Information:");
            System.out.println("Name: " + authenticatedUser.getName());
            System.out.println("Email: " + authenticatedUser.getEmail());
            System.out.println("Address: " + authenticatedUser.getAddress());
            System.out.println("Phone Number: " + authenticatedUser.getPhoneNumber());

            System.out.println("Accounts:");
            System.out.println("--------------------");
            for (String[] accountData : accounts) {
                String accountName = accountData[0];
                String balance = accountData[1];

                System.out.println("Account Name: " + accountName);
                System.out.println("Balance: " + balance);
                System.out.println("--------------------");
            }
        } else {
            System.out.println("User Information:");
            System.out.println("Name: " + authenticatedUser.getName());
            System.out.println("Email: " + authenticatedUser.getEmail());
            System.out.println("Address: " + authenticatedUser.getAddress());
            System.out.println("Phone Number: " + authenticatedUser.getPhoneNumber());

            System.out.println("Accounts:");
            System.out.println("You have no accounts yet!");
        }
    }

    public void createAccount(){
        scanner = new Scanner(System.in);
        System.out.println("Enter account name: ");
        String accountName = scanner.nextLine();

        System.out.println("Enter balance: ");
        BigDecimal balance = scanner.nextBigDecimal();
        scanner.nextLine();

        System.out.println("Enter SSN: ");
        String ssn = scanner.nextLine();

        System.out.println("Enter userid: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.println("""
                
                Account created!
                """);

        Account newAccount = new Account(balance, userId, accountName, accountNumber, ssn);
        accountRepository.saveAccount(newAccount);
    }

    public void deleteAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter account number: ");
        String accountNumber = scanner.nextLine();

        String ssn = authenticatedUser.getSSN();
        accountRepository.deleteAccount(accountNumber, ssn);
    }

    public void transferMoney() {
        try {
            Connection connection = getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the account number you want to transfer from: ");
            String accountNumberFrom = scanner.nextLine().trim();

            System.out.print("Enter the account number you want to transfer to: ");
            String accountNumberTo = scanner.nextLine().trim();

            System.out.print("Enter the amount you want to transfer: ");
            BigDecimal amount = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.println("Enter a message");
            String message = scanner.nextLine();

            transactionService.transferMoney(authenticatedUser, accountNumberFrom, accountNumberTo, amount, message);

            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void showSentTransactions() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the account number you want to show transactions from: ");
            String accountNumberFrom = scanner.nextLine().trim();

            System.out.println("Enter from date (yyyy-mm-dd): ");
            String fromDate = scanner.nextLine().trim();

            System.out.println("Enter to date (yyyy-mm-dd): ");
            String toDate = scanner.nextLine().trim();

            String ssn = authenticatedUser.getSSN();

            transactionRepository.sentTransactions(accountNumberFrom, fromDate, toDate, ssn);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showReceivedTransactions() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the account number: ");
            String accountNumber = scanner.nextLine().trim();

            System.out.println("Enter from date (yyyy-mm-dd): ");
            String fromDate = scanner.nextLine().trim();

            System.out.println("Enter to date (yyyy-mm-dd): ");
            String toDate = scanner.nextLine().trim();

            transactionRepository.receivedTransactions(accountNumber, fromDate, toDate);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}

