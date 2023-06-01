package org.arzijeziberovska.service;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Scanner;

public class TransactionService extends DatabaseConnection {

    private User authenticatedUser;
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
//        this.authenticatedUser = authenticatedUser;
        this.transactionRepository = transactionRepository;
    }

//    public void transferMoney() {
//        System.out.println("Transfer money");
//        try {
//            Connection connection = getConnection();
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.print("Enter the account number you want to transfer from: ");
//            int accountNumberFrom = scanner.nextInt();
//
//            System.out.print("Enter the account number you want to transfer to: ");
//            int accountNumberTo = scanner.nextInt();
//
//            System.out.print("Enter the amount you want to transfer: ");
//            BigDecimal amount = scanner.nextBigDecimal();
//            scanner.nextLine();
//
//            System.out.println("Enter a message");
//            String message = scanner.nextLine();
//
//            if (authenticatedUser != null) {
//                String ssn = authenticatedUser.getSSN();
//                transactionRepository.transferMoney(accountNumberFrom, accountNumberTo, amount, message, ssn);
//            } else {
//                System.out.println("User is not authenticated.");
//            }
//
//            connection.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public void transferMoney(User authenticatedUser, String accountNumberFrom, String accountNumberTo, BigDecimal amount, String message) {
    //kontrollera om kontot finns hos user först
        if (authenticatedUser != null)  {//&& accountNumberFrom == account.getAccountNumber())
            String ssn = authenticatedUser.getSSN();
            transactionRepository.transferMoney(accountNumberFrom, accountNumberTo, amount, message, ssn);
        } else {
            System.out.println("User is not authenticated.");
        }
    }



//    public void showSentTransactions() {
//        System.out.println("Show transactions");
//        try {
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.print("Enter the account number you want to show transactions from: ");
//            String accountNumberFrom = scanner.nextLine().trim();
//
//            System.out.println("Enter from date (yyyy-mm-dd): ");
//            String fromDate = scanner.nextLine().trim();
//
//            System.out.println("Enter to date (yyyy-mm-dd): ");
//            String toDate = scanner.nextLine().trim();
//
//            String ssn = authenticatedUser.getSSN();
//
//            transactionRepository.sentTransactions(accountNumberFrom, fromDate, toDate, ssn);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void showReceivedTransactions() {
//        System.out.println("Show transactions");
//        try {
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.print("Enter the account number: ");
//            String accountNumber = scanner.nextLine().trim();
//
//            System.out.println("Enter from date (yyyy-mm-dd): ");
//            String fromDate = scanner.nextLine().trim();
//
//            System.out.println("Enter to date (yyyy-mm-dd): ");
//            String toDate = scanner.nextLine().trim();
//
//            transactionRepository.receivedTransactions(accountNumber, fromDate, toDate);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}

