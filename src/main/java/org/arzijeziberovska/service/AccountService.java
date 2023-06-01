package org.arzijeziberovska.service;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Account;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.AccountRepository;
import org.arzijeziberovska.repository.UserRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class AccountService extends DatabaseConnection {
    private User authenticatedUser;
    private AccountRepository accountRepository;
    private Scanner scanner;

    public AccountService(User authenticatedUser, AccountRepository accountRepository) {
        this.authenticatedUser = authenticatedUser;
        this.accountRepository = accountRepository;
    }
    public AccountService() {
    }

//    public void showUserAndAccounts() {
//        String ssn = authenticatedUser.getSSN();
//        List<String[]> accounts = accountRepository.getUserAccounts(ssn);
//
//        if (!accounts.isEmpty()) {
//            System.out.println("User Information:");
//            System.out.println("Name: " + authenticatedUser.getName());
//            System.out.println("Email: " + authenticatedUser.getEmail());
//            System.out.println("Address: " + authenticatedUser.getAddress());
//            System.out.println("Phone Number: " + authenticatedUser.getPhoneNumber());
//
//            System.out.println("Accounts:");
//            System.out.println("--------------------");
//            for (String[] accountData : accounts) {
//                String accountName = accountData[0];
//                String balance = accountData[1];
//
//                System.out.println("Account Name: " + accountName);
//                System.out.println("Balance: " + balance);
//                System.out.println("--------------------");
//            }
//        } else {
//            System.out.println("User Information:");
//            System.out.println("Name: " + authenticatedUser.getName());
//            System.out.println("Email: " + authenticatedUser.getEmail());
//            System.out.println("Address: " + authenticatedUser.getAddress());
//            System.out.println("Phone Number: " + authenticatedUser.getPhoneNumber());
//
//            System.out.println("Accounts:");
//            System.out.println("You have no accounts yet!");
//        }
//    }

//    public void createAccount(){
//            scanner = new Scanner(System.in);
//            System.out.println("Enter account name: ");
//            String accountName = scanner.nextLine();
//
//            System.out.println("Enter balance: ");
//            BigDecimal balance = scanner.nextBigDecimal();
//            scanner.nextLine();
//
//            System.out.println("Enter SSN: ");
//            String ssn = scanner.nextLine();
//
//            System.out.println("Enter userid: ");
//            int userId = scanner.nextInt();
//
//            System.out.println("Enter account number: ");
//            int accountNumber = scanner.nextInt();
//
//            System.out.println("Account created!");
//
//            Account newAccount = new Account(balance, userId, accountName, accountNumber, ssn);
//            accountRepository.saveAccount(newAccount);
//    }

//    public void deleteAccount() {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Enter account number: ");
//            String accountNumber = scanner.nextLine();
//
//            String ssn = authenticatedUser.getSSN();
//            accountRepository.deleteAccount(accountNumber, ssn);
//      }
}

