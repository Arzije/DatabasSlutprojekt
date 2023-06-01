package org.arzijeziberovska.repository;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Transaction;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.service.TransactionService;

import java.sql.*;
import java.math.BigDecimal;

public class TransactionRepository extends DatabaseConnection {

    public TransactionRepository() {
    }

    public void transferMoney(String accountNumberFrom, String accountNumberTo, BigDecimal amount, String message, String ssn) {
        try {
            Connection connection = getConnection();

            // kontrollerar om kontot finns hos den inloggade användaren
            String accountOwnershipCheckQuery = "SELECT 1 FROM account WHERE account_number = ? AND SSN = ?";

            PreparedStatement accountOwnershipCheckStatement = connection.prepareStatement(accountOwnershipCheckQuery);
            accountOwnershipCheckStatement.setString(1, accountNumberFrom);
            accountOwnershipCheckStatement.setString(2, ssn);

            ResultSet accountOwnershipResult = accountOwnershipCheckStatement.executeQuery();
            if (!accountOwnershipResult.next()) {
                System.out.println("You don't have the specified account.");

                accountOwnershipCheckStatement.close();
                connection.close();
                return;
            }

            // kontrollerar saldo
            String balanceCheckQuery = "SELECT balance FROM account WHERE account_number = ? AND SSN = ?";

            PreparedStatement balanceCheckStatement = connection.prepareStatement(balanceCheckQuery);
            balanceCheckStatement.setString(1, accountNumberFrom);
            balanceCheckStatement.setString(2, ssn);

            ResultSet balanceResult = balanceCheckStatement.executeQuery();
            if (!balanceResult.next() || balanceResult.getBigDecimal("balance").compareTo(amount) < 0) {
                System.out.println("You don't have enough money in your account!");

                accountOwnershipCheckStatement.close();
                balanceCheckStatement.close();
                connection.close();
                return;
            }

            // uppdaterar saldot på kontot som pengarna ska dras från
            String updateFromQuery = "UPDATE account SET balance = balance - ? WHERE account_number = ? AND SSN = ?";

            PreparedStatement updateFromStatement = connection.prepareStatement(updateFromQuery);
            updateFromStatement.setBigDecimal(1, amount);
            updateFromStatement.setString(2, accountNumberFrom);
            updateFromStatement.setString(3, ssn);

            int rowsAffected = updateFromStatement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Transfer failed: Account number or SSN is incorrect.");

                accountOwnershipCheckStatement.close();
                balanceCheckStatement.close();
                updateFromStatement.close();
                connection.close();
                return;
            }

            // uppdatrar saldot på kontot som pengarna ska överföras till
            String updateToQuery = "UPDATE account SET balance = balance + ? WHERE account_number = ?";

            PreparedStatement updateToStatement = connection.prepareStatement(updateToQuery);
            updateToStatement.setBigDecimal(1, amount);
            updateToStatement.setString(2, accountNumberTo);

            updateToStatement.executeUpdate();

            System.out.println("Transfer successful!");

            Transaction updatedTransaction = new Transaction(message, amount, accountNumberFrom, accountNumberTo, ssn);
            saveTransaction(updatedTransaction);

            accountOwnershipCheckStatement.close();
            balanceCheckStatement.close();
            updateFromStatement.close();
            updateToStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // sparar gjorda transaktioner
    public void saveTransaction(Transaction updatedTransaction) {
        try {
            Connection connection = getConnection();

            String query = "INSERT INTO transaction (message, amount, from_account, to_account, SSN) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, updatedTransaction.getMessage());
            preparedStatement.setBigDecimal(2, updatedTransaction.getAmount());
            preparedStatement.setString(3, updatedTransaction.getFromAccount());
            preparedStatement.setString(4, updatedTransaction.getToAccount());
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

    // hämtar skickade transaktioner
    public void sentTransactions(String accountNumberFrom, String fromDate, String toDate, String ssn) {
        try {
            Connection connection = getConnection();

            String accountOwnershipCheckQuery = "SELECT 1 FROM account WHERE account_number = ? AND SSN = ?";
            PreparedStatement accountOwnershipCheckStatement = connection.prepareStatement(accountOwnershipCheckQuery);
            accountOwnershipCheckStatement.setString(1, accountNumberFrom);
            accountOwnershipCheckStatement.setString(2, ssn);

            ResultSet accountOwnershipResult = accountOwnershipCheckStatement.executeQuery();
            if (!accountOwnershipResult.next()) {
                System.out.println("You don't have the specified account.");
                accountOwnershipCheckStatement.close();
                connection.close();
                return;
            }

            String fromDateStart = fromDate + " 00:00:00";
            String toDateEnd = toDate + " 23:59:59";

            String query = "SELECT * FROM transaction WHERE from_account = ? AND SSN = ? " +
                    "AND created BETWEEN ? AND ? ORDER BY created ASC ";


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

    // hämtar mottagna transaktioner
    public void receivedTransactions(String accountNumberTo, String fromDate, String toDate) {
        try {
            Connection connection = getConnection();

            String accountOwnershipCheckQuery = "SELECT 1 FROM account WHERE account_number = ?";
            PreparedStatement accountOwnershipCheckStatement = connection.prepareStatement(accountOwnershipCheckQuery);
            accountOwnershipCheckStatement.setString(1, accountNumberTo);

            ResultSet accountOwnershipResult = accountOwnershipCheckStatement.executeQuery();
            if (!accountOwnershipResult.next()) {
                System.out.println("You don't have the specified account.");
                accountOwnershipCheckStatement.close();
                connection.close();
                return;
            }

            String fromDateStart = fromDate + " 00:00:00";
            String toDateEnd = toDate + " 23:59:59";

            String query = "SELECT * FROM transaction WHERE to_account = ? AND created BETWEEN ? AND ? " +
                    "ORDER BY created ASC ";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumberTo);
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

