package org.arzijeziberovska.repository;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Account;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository extends DatabaseConnection{

    public void saveAccount(Account account) {
        try {
            Connection connection = super.getConnection();

            String query = "INSERT INTO account " +
                    "(balance, user_id, account_name, account_number, SSN) " +
                    "VALUES (?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBigDecimal(1, account.getBalance());
            preparedStatement.setInt(2, account.getUserId());
            preparedStatement.setString(3, account.getAccountName());
            preparedStatement.setString(4, account.getAccountNumber());
            preparedStatement.setString(5, account.getSSN());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //raderar konto baserat på kontonummer och SSN
    public void deleteAccount(String accountNumber, String ssn) {
        try {
            Connection connection = super.getConnection();
            String query = "DELETE FROM account WHERE account_number = ? AND SSN = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, ssn);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("""
                        Account deleted!""");
            } else {
                System.out.println("""
                        Account does not exist!""");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //hämtar lista på konton baserat på SSN
    public List<String[]> getUserAccounts(String ssn) {
        List<String[]> accounts = new ArrayList<>();

        try {
            Connection connection = getConnection();

            String query = "SELECT account_name, balance " +
                    "FROM account " +
                    "WHERE SSN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ssn);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String accountName = resultSet.getString("account_name");
                double balance = resultSet.getDouble("balance");

                String[] accountData = { accountName, String.valueOf(balance) };
                accounts.add(accountData);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return accounts;
    }

    public Account getAccount(String accountNumber) {
        try {
            Connection connection = getConnection();

            String query = "SELECT * FROM account WHERE account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String accountName = resultSet.getString("account_name");
                String accountNumberFromDB = resultSet.getString("account_number");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                int userId = resultSet.getInt("user_id");
                String SSN = resultSet.getString("SSN");

                Account account = new Account(balance, userId, accountName, accountNumberFromDB, SSN);

                resultSet.close();
                preparedStatement.close();
                connection.close();

                return account;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}

