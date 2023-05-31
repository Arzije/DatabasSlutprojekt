package org.arzijeziberovska.repository;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Account;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.service.AccountService;

import java.sql.*;

public class AccountRepository extends DatabaseConnection{

    private User authenticatedUser;
    private AccountService accountService;

    public AccountRepository() {//User authenticatedUser, AccountService accountService) {
//        this.authenticatedUser = authenticatedUser;
//        this.accountService = accountService;
    }

//    public AccountRepository() {
//    }

    public void saveAccount(Account account) {
        try {
            Connection connection = super.getConnection();

            String query = "INSERT IGNORE INTO account " +
                    "(balance, user_id, account_name, account_number, SSN) " +
                    "VALUES (?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setBigDecimal(1, account.getBalance());
            preparedStatement.setInt(2, account.getUserId());
            preparedStatement.setString(3, account.getAccountName());
            preparedStatement.setInt(4, account.getAccountNumber());
            preparedStatement.setString(5, account.getSSN());

            int result = preparedStatement.executeUpdate();
            System.out.println("Result: " + result);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Insert completed");
        }
    }

    public void deleteAccount(String accountNumber, String ssn) {
        try {
            Connection connection = super.getConnection();
            String query = "DELETE FROM account WHERE account_number = ? AND SSN = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, ssn);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                System.out.println("Account deleted!");
            } else {
                System.out.println("Account does not exist!");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}

