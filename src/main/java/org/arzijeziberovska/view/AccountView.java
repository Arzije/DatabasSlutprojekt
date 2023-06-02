package org.arzijeziberovska.view;

import org.arzijeziberovska.model.Account;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.AccountRepository;
import org.arzijeziberovska.repository.TransactionRepository;
import org.arzijeziberovska.service.AccountService;
import org.arzijeziberovska.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class AccountView {
    private final User authenticatedUser;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private Scanner scanner;

    public AccountView(User authenticatedUser,
                       AccountRepository accountRepository,
                       TransactionService transactionService,
                       TransactionRepository transactionRepository,
                        AccountService accountService) {
        this.authenticatedUser = authenticatedUser;
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }
    public void showAccountView() {

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
                    showUserAndAccounts();
                    break;
                case "2":
                    transferMoney();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    deleteAccount();
                    break;
                case "5":
                    showSentTransactions();
                    break;
                case "6":
                    showReceivedTransactions();
                    break;
                case "7":
                    System.out.println("You have been logged out!");
                    whileTrue = false;
                    break;
                default:
                    System.out.println("Invalid input, try again!");
                    break;
            }
        }
        scanner.close();
    }

    //visar info om användaren och konton
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

    // tar in input från användaren och skickar vidare till service för att skapa nytt konto
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
        scanner.close();
    }

    // raderar konto baserat på kontonummer och ssn
    public void deleteAccount() {
        scanner = new Scanner(System.in);
        System.out.println("Enter account number: ");
        String accountNumber = scanner.nextLine();

        accountService.deleteAccount(accountNumber, authenticatedUser);
        scanner.close();
    }

    // tar in input från användaren och skickar vidare till service för att göra en transaktion
    public void transferMoney() {
            scanner = new Scanner(System.in);

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
            scanner.close();
    }


    //tar in input från användaren och skickar vidare till repository för att visa transaktioner som skickats
    public void showSentTransactions() {
            scanner = new Scanner(System.in);

            System.out.print("Enter the account number you want to show transactions from: ");
            String accountNumberFrom = scanner.nextLine().trim();

            System.out.println("Enter from date (yyyy-mm-dd): ");
            String fromDate = scanner.nextLine().trim();

            System.out.println("Enter to date (yyyy-mm-dd): ");
            String toDate = scanner.nextLine().trim();

            String ssn = authenticatedUser.getSSN();

            transactionRepository.sentTransactions(accountNumberFrom, fromDate, toDate, ssn);
            scanner.close();
    }

    //tar in input från användaren och skickar vidare till repository för att visa transaktioner som mottagits
    public void showReceivedTransactions() {
            scanner = new Scanner(System.in);

            System.out.print("Enter the account number: ");
            String accountNumber = scanner.nextLine().trim();

            System.out.println("Enter from date (yyyy-mm-dd): ");
            String fromDate = scanner.nextLine().trim();

            System.out.println("Enter to date (yyyy-mm-dd): ");
            String toDate = scanner.nextLine().trim();

            transactionRepository.receivedTransactions(accountNumber, fromDate, toDate);
            scanner.close();
    }
}

