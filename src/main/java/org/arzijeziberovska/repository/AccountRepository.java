package org.arzijeziberovska.repository;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Account;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.service.AccountService;
import org.arzijeziberovska.view.AccountView;


import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class AccountRepository extends DatabaseConnection{

    private User authenticatedUser;
    private AccountService accountService;
    private Scanner scanner;

    public AccountRepository(User authenticatedUser, AccountService accountService) {
        this.authenticatedUser = authenticatedUser;
        this.accountService = accountService;
    }

    public AccountRepository() {
    }

    public void getAccountsByUserSSN(User user) { //Stoppar in usern

        try {
            Connection connection = super.getConnection();

            String query = "SELECT account_name, balance " +
                    "FROM account AS a " +
                    "JOIN user AS u ON a.SSN = u.SSN " +
                    "WHERE u.SSN = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getSSN()); //Hämtar userns SSN

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String accountName = resultSet.getString("account_name");
                BigDecimal balance = resultSet.getBigDecimal("balance");

                System.out.println("Account name: " + accountName + " Balance: " + balance);
            }

            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

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

    public void selectAccountByAccountNumber() {
        try {
            scanner = new Scanner(System.in);
            System.out.println("Enter account number: ");
            String accountNumber = scanner.nextLine();

            Connection connection = super.getConnection();

            String query = "SELECT account_name, COUNT(*) FROM account WHERE account_number = ? AND SSN = ?";
//            String subQuery = "IF EXISTS (SELECT * FROM account WHERE account_number = ? AND SSN = ?) THEN 1 ELSE 0 END IF";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, authenticatedUser.getSSN());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(2) > 0) {
                String accountName = resultSet.getString("account_name");
                System.out.println("Account name: " + accountName);

            } else {
                System.out.println("Account does not exist!");
            }
            accountService.deleteAccount(accountNumber);

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}

