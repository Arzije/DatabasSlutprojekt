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


    public TransactionService(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }


    public void transferMoney(BigDecimal balance) {
        System.out.println("Transfer money");
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

            String query = "UPDATE account SET balance = balance - ? WHERE account_number = ?";// SSN = ? AND

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setString(2, accountNumberFrom);


            if (balance != null && amount.compareTo(balance) > 0) { //&& eller ||
                System.out.println("You don't have enough money in your account!");
            } else {

                preparedStatement.executeUpdate();

                query = "UPDATE account SET balance = balance + ? WHERE account_number = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setBigDecimal(1, amount);
                preparedStatement.setString(2, accountNumberTo);

                preparedStatement.executeUpdate();

                System.out.println("Transfer successful!");

                String query2 = "INSERT IGNORE INTO transaction " +
                        "(message, amount, to_account, from_account, SSN) " +
                        "VALUES (?, ?, ?, ?, ?) ";

                PreparedStatement preparedStatement1 = connection.prepareStatement(query2);
                preparedStatement1.setString(1, message != null ? message : null);
                preparedStatement1.setBigDecimal(2, amount);
                preparedStatement1.setString(3, accountNumberTo);
                preparedStatement1.setString(4, accountNumberFrom);
                preparedStatement1.setString(5, authenticatedUser.getSSN());

                preparedStatement1.executeUpdate();

                preparedStatement1.close();

            }
            preparedStatement.close();

            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    public void showSentTransactions(){
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
//            String query = "SELECT * FROM transaction WHERE from_account = ? AND SSN = ? AND created BETWEEN ? AND ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, accountNumberFrom);
//            preparedStatement.setString(2, authenticatedUser.getSSN());
//            preparedStatement.setString(3, fromDate);
//            preparedStatement.setString(4, toDate);
//
//            preparedStatement.executeQuery();
//
//            if (preparedStatement.getResultSet().next()) {
//                System.out.println("You have no transactions");
//            } else {
//                ResultSet resultSet = preparedStatement.getResultSet();
//                while (resultSet.next()) {
//                    System.out.println("Transaction ID: " + resultSet.getInt("transaction_id"));
//                    System.out.println("Message: " + resultSet.getString("message"));
//                    System.out.println("Amount: " + resultSet.getBigDecimal("amount"));
//                    System.out.println("To account: " + resultSet.getString("to_account"));
////                    System.out.println("From account: " + resultSet.getString("from_account"));
//                    System.out.println("Created: " + resultSet.getTimestamp("created"));
////                    System.out.println("SSN: " + resultSet.getString("SSN"));
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

    public void showSentTransactions() {
        System.out.println("Show transactions");
        try {
            Connection connection = getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the account number you want to show transactions from: ");
            String accountNumberFrom = scanner.nextLine().trim();

            System.out.println("Enter from date (yyyy-mm-dd): ");
            String fromDate = scanner.nextLine().trim();

            System.out.println("Enter to date (yyyy-mm-dd): ");
            String toDate = scanner.nextLine().trim();

            String fromDateStart = fromDate + " 00:00:00";
            String toDateEnd = toDate + " 23:59:59";

            String query = "SELECT * FROM transaction WHERE from_account = ? AND SSN = ? AND created BETWEEN ? AND ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumberFrom);
            preparedStatement.setString(2, authenticatedUser.getSSN());
            preparedStatement.setString(3, fromDateStart);
            preparedStatement.setString(4, toDateEnd);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("You have no sent transactions");
            } else {
                while (resultSet.next()) {
                    System.out.println("Transaction ID: " + resultSet.getInt("id"));
                    System.out.println("Message: " + resultSet.getString("message"));
                    System.out.println("Amount: " + resultSet.getBigDecimal("amount"));
                    System.out.println("To account: " + resultSet.getString("to_account"));
                    System.out.println("Created: " + resultSet.getTimestamp("created"));
                    System.out.println();
                }
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void showReceivedTransactions(){
        System.out.println("Show transactions");
        try {
            Connection connection = getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the account number you want to show transactions from: ");
            String accountNumberTo = scanner.nextLine().trim();

            System.out.println("Enter from date (yyyy-mm-dd): ");
            String fromDate = scanner.nextLine().trim();

            System.out.println("Enter to date (yyyy-mm-dd): ");
            String toDate = scanner.nextLine().trim();

            String fromDateStart = fromDate + " 00:00:00";
            String toDateEnd = toDate + " 23:59:59";

            String query = "SELECT * FROM transaction WHERE to_account = ? AND SSN = ? AND created BETWEEN ? AND ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumberTo);
            preparedStatement.setString(2, authenticatedUser.getSSN());
            preparedStatement.setString(3, fromDateStart);
            preparedStatement.setString(4, toDateEnd);

//            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("You have no received transactions");
            } else {
                while (resultSet.next()) {
                    System.out.println("Transaction ID: " + resultSet.getInt("id"));
                    System.out.println("Message: " + resultSet.getString("message"));
                    System.out.println("Amount: " + resultSet.getBigDecimal("amount"));
                    System.out.println("To account: " + resultSet.getString("from_account"));
                    System.out.println("Created: " + resultSet.getTimestamp("created"));
                    System.out.println();
                }
            }

            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

