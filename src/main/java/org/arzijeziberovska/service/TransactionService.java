package org.arzijeziberovska.service;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Transaction;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TransactionService extends DatabaseConnection {

    private User authenticatedUser;
    private TransactionRepository transactionRepository;

    private Transaction transaction;


    public TransactionService(User authenticatedUser, TransactionRepository transactionRepository) {
        this.authenticatedUser = authenticatedUser;
        this.transactionRepository = transactionRepository;
    }

    public TransactionService(){

    }


//    public void transferMoney(BigDecimal balance) {
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
//            String query = "UPDATE account SET balance = balance - ? WHERE account_number = ? AND SSN ? ?";// SSN = ? AND
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setBigDecimal(1, amount);
//            preparedStatement.setInt(2, accountNumberFrom);
//            preparedStatement.setString(3, authenticatedUser.getSSN());
//
//
//            if (balance != null && amount.compareTo(balance) > 0) { //&& eller ||
//                System.out.println("You don't have enough money in your account!");
//            } else {
//
//                preparedStatement.executeUpdate();
//
//                Transaction updatedTransaction = new Transaction(message, amount, accountNumberTo, accountNumberFrom, authenticatedUser.getSSN());
//
//                query = "UPDATE account SET balance = balance + ? WHERE account_number = ?";
//                preparedStatement = connection.prepareStatement(query);
//                preparedStatement.setBigDecimal(1, amount);
//                preparedStatement.setInt(2, accountNumberTo);
//
//                preparedStatement.executeUpdate();
//
//                System.out.println("Transfer successful!");
//
//                transactionRepository.saveTransaction(updatedTransaction);
//
//                String query2 = "INSERT IGNORE INTO transaction " +
//                        "(message, amount, to_account, from_account, SSN) " +
//                        "VALUES (?, ?, ?, ?, ?) ";
//
//                PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
//                preparedStatement1.setString(1, message != null ? message : null);
//                preparedStatement1.setBigDecimal(2, amount);
//                preparedStatement1.setInt(3, accountNumberTo);
//                preparedStatement1.setInt(4, accountNumberFrom);
//                preparedStatement1.setString(5, authenticatedUser.getSSN());
//
//                preparedStatement1.executeUpdate();
//
//                Transaction updatedTransaction1 = new Transaction(message, amount, accountNumberTo, accountNumberFrom, authenticatedUser.getSSN());
//                transactionRepository.saveTransaction(updatedTransaction1);
//
//                preparedStatement1.close();
//
//            }
//            preparedStatement.close();
//
//            connection.close();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public void transferMoney() {
        System.out.println("Transfer money");
        try {
            Connection connection = getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the account number you want to transfer from: ");
            int accountNumberFrom = scanner.nextInt();

            System.out.print("Enter the account number you want to transfer to: ");
            int accountNumberTo = scanner.nextInt();

            System.out.print("Enter the amount you want to transfer: ");
            BigDecimal amount = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.println("Enter a message");
            String message = scanner.nextLine();

            if (authenticatedUser != null) {
                String ssn = authenticatedUser.getSSN();
                transactionRepository.transferMoney(accountNumberFrom, accountNumberTo, amount, message, ssn);
            } else {
                System.out.println("User is not authenticated.");
            }

            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


//    public void showSentTransactions() {
//        System.out.println("Show transactions");
//        try {
//            Connection connection = getConnection();
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
//            String fromDateStart = fromDate + " 00:00:00";
//            String toDateEnd = toDate + " 23:59:59";
//
//            String query = "SELECT * FROM transaction WHERE from_account = ? AND SSN = ? AND created BETWEEN ? AND ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, accountNumberFrom);
//            preparedStatement.setString(2, authenticatedUser.getSSN());
//            preparedStatement.setString(3, fromDateStart);
//            preparedStatement.setString(4, toDateEnd);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (!resultSet.isBeforeFirst()) {
//                System.out.println("You have no sent transactions");
//            } else {
//                while (resultSet.next()) {
//                    System.out.println("Transaction ID: " + resultSet.getInt("id"));
//                    System.out.println("Message: " + resultSet.getString("message"));
//                    System.out.println("Amount: " + resultSet.getBigDecimal("amount"));
//                    System.out.println("To account: " + resultSet.getString("to_account"));
//                    System.out.println("Created: " + resultSet.getTimestamp("created"));
//                    System.out.println();
//                }
//            }
//
//            resultSet.close();
//            preparedStatement.close();
//            connection.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public void showSentTransactions() {
        System.out.println("Show transactions");
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


//    public void showReceivedTransactions(){
//        System.out.println("Show transactions");
//        try {
//            Connection connection = getConnection();
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.print("Enter the account number you want to show transactions from: ");
//            String accountNumberTo = scanner.nextLine().trim();
//
//            System.out.println("Enter from date (yyyy-mm-dd): ");
//            String fromDate = scanner.nextLine().trim();
//
//            System.out.println("Enter to date (yyyy-mm-dd): ");
//            String toDate = scanner.nextLine().trim();
//
//            String fromDateStart = fromDate + " 00:00:00";
//            String toDateEnd = toDate + " 23:59:59";
//
//            String query = "SELECT * FROM transaction WHERE to_account = ? AND SSN = ? AND created BETWEEN ? AND ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, accountNumberTo);
//            preparedStatement.setString(2, authenticatedUser.getSSN());
//            preparedStatement.setString(3, fromDateStart);
//            preparedStatement.setString(4, toDateEnd);
//
////            preparedStatement.executeQuery();
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            if (!resultSet.isBeforeFirst()) {
//                System.out.println("You have no received transactions");
//            } else {
//                while (resultSet.next()) {
//                    System.out.println("Transaction ID: " + resultSet.getInt("id"));
//                    System.out.println("Message: " + resultSet.getString("message"));
//                    System.out.println("Amount: " + resultSet.getBigDecimal("amount"));
//                    System.out.println("To account: " + resultSet.getString("from_account"));
//                    System.out.println("Created: " + resultSet.getTimestamp("created"));
//                    System.out.println();
//                }
//            }
//
//            preparedStatement.close();
//            connection.close();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public void showReceivedTransactions() {
        System.out.println("Show transactions");
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

