package org.arzijeziberovska.repository;



import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Transaction;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.service.TransactionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.math.BigDecimal;

public class TransactionRepository extends DatabaseConnection {

    private User authenticatedUser;
    private TransactionService transactionService;



//    public TransactionRepository(User authenticatedUser, TransactionService transactionService) {
//        this.authenticatedUser = authenticatedUser;
//        this.transactionService = transactionService;
//    }

    public TransactionRepository() {
    }

    public void transferMoney(int accountNumberFrom, int accountNumberTo, BigDecimal amount, String message, String ssn) {
        try {
            Connection connection = getConnection();

            String query = "UPDATE account SET balance = balance - ? WHERE account_number = ? AND SSN = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setInt(2, accountNumberFrom);
            preparedStatement.setString(3, ssn);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("You don't have enough money in your account!");
                preparedStatement.close();
                connection.close();
                return;
            }

            query = "UPDATE account SET balance = balance + ? WHERE account_number = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setInt(2, accountNumberTo);

            preparedStatement.executeUpdate();

            System.out.println("Transfer successful!");

            Transaction updatedTransaction = new Transaction(message, amount, accountNumberFrom, accountNumberTo, ssn);
            saveTransaction(updatedTransaction);

            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void saveTransaction(Transaction updatedTransaction) {
        try {
            Connection connection = getConnection();

            String query = "INSERT INTO transaction (message, amount, from_account, to_account, SSN) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, updatedTransaction.getMessage());
            preparedStatement.setBigDecimal(2, updatedTransaction.getAmount());
            preparedStatement.setInt(3, updatedTransaction.getFromAccount());
            preparedStatement.setInt(4, updatedTransaction.getToAccount());
            preparedStatement.setString(5, updatedTransaction.getSSN());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                updatedTransaction.setId(id);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sentTransactions(String accountNumberFrom, String fromDate, String toDate, String ssn) {
        try {
            Connection connection = getConnection();

            String fromDateStart = fromDate + " 00:00:00";
            String toDateEnd = toDate + " 23:59:59";

            String query = "SELECT * FROM transaction WHERE from_account = ? AND SSN = ? AND created BETWEEN ? AND ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumberFrom);
            preparedStatement.setString(2, ssn);
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

    public void receivedTransactions(String accountNumberTo, String fromDate, String toDate) {
        System.out.println(accountNumberTo + " " + fromDate + " " + toDate);
        try {
            Connection connection = getConnection();

            String fromDateStart = fromDate + " 00:00:00";
            String toDateEnd = toDate + " 23:59:59";

            String query = "SELECT * FROM transaction WHERE to_account = ? AND created BETWEEN ? AND ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumberTo);
//            preparedStatement.setString(2, ssn);
            preparedStatement.setString(2, fromDateStart);
            preparedStatement.setString(3, toDateEnd);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("You have no received transactions");
            } else {
                while (resultSet.next()) {
                    System.out.println("Transaction ID: " + resultSet.getInt("id"));
                    System.out.println("Message: " + resultSet.getString("message"));
                    System.out.println("Amount: " + resultSet.getBigDecimal("amount"));
                    System.out.println("From account: " + resultSet.getString("from_account"));
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
}

