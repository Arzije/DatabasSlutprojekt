package org.arzijeziberovska.service;


import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Account;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.AccountRepository;
import org.arzijeziberovska.repository.UserRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class AccountService extends DatabaseConnection {
    private AuthenticateUser authenticateUser;
    private User authenticatedUser;
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private Scanner scanner;

    public AccountService(User authenticatedUser, AccountRepository accountRepository) {
        this.authenticatedUser = authenticatedUser;
        this.accountRepository = accountRepository;
    }
    public AccountService() {
    }

    //    public AccountService(User authenticatedUser, AccountRepository accountRepository) {
//        this.authenticatedUser = authenticatedUser;
//        this.accountRepository = accountRepository;
//
//    }
    public void showUserAndAccounts() throws SQLException {

        Connection connection = super.getConnection();

        String query = "SELECT name, email, address, phonenumber," +
                " account_name, balance " +
                "FROM account AS a " +
                "JOIN user AS u ON a.SSN = u.SSN " +
                "WHERE u.SSN = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

//        String ssn = authenticateUser.authenticate().getSSN();
        String ssn = authenticatedUser.getSSN();
        System.out.println("Är det här som den körs igen");
        preparedStatement.setString(1, ssn);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String adress = resultSet.getString("address");
            String phonenumber = resultSet.getString("phonenumber");
//            String id = resultSet.getString("id");
            String accountName = resultSet.getString("account_name");
            String balance = resultSet.getString("balance");
            System.out.println("""
                    Name: %s
                    Email: %s
                    Adress: %s
                    Phonenumber: %s
                    Account name: %s
                    Balance: %s
                    """.formatted(name, email, adress, phonenumber, accountName, balance));
        } else {
            System.out.println("No accounts yet!");
        }
        resultSet.close();
        connection.close();
//        return user;
    }


//    public void showAccountsBySSN() {
//        String ssn = authenticateUser.authenticate().getSSN();
//        User authenticatedUser = userRepository.getUserBySSN(ssn);
//        accountRepository.getAccountsByUserSSN(authenticatedUser);
//
//    }

//    public void showAccounts() throws SQLException {
//        System.out.println("Är det denna som sker 2 ggr?");
//        User authenticatedUser = authenticateUser.authenticate();
//        if (authenticatedUser != null) {
//            Connection connection = super.getConnection();
//
//            String query = "SELECT account_name, balance " +
//                    "FROM account AS a " +
//                    "JOIN user AS u ON a.SSN = u.SSN " +
//                    "WHERE u.SSN = ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, authenticatedUser.getSSN());
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                String accountName = resultSet.getString("account_name");
//                String balance = resultSet.getString("balance");
//                System.out.println("Account name: " + accountName + " Balance: " + balance);
//            }
//            resultSet.close();
//            connection.close();
//        } else {
//            System.out.println("Authentication failed!");
//        }
//    }




//    public void showAccountsBySSN() {
//        boolean authenticated = authenticateUser.authenticateUser();
//        if (authenticated) {
//            String ssn = authenticateUser.authenticate().getSSN();
//            User authenticatedUser = userRepository.getUserBySSN(ssn);
//            accountRepository.getAccountsByUserSSN(authenticatedUser);
//        } else {
//            System.out.println("Authentication required!");
//        }
//    }



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

            System.out.println("Enter account number: ");
            int accountNumber = scanner.nextInt();

            System.out.println("Account created!");

            Account newAccount = new Account(balance, userId, accountName, accountNumber, ssn);
            accountRepository.saveAccount(newAccount);
    }

//    public void deleteAccount(String accountNumber) {
//        try {
//            Connection connection = super.getConnection();
//            String query = "DELETE FROM account WHERE account_number = ? AND SSN = ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, accountNumber);
//
//            String ssn = authenticatedUser.getSSN();
//            preparedStatement.setString(2, ssn);
//
//            preparedStatement.executeUpdate();
////            accountRepository.removeSelectedAccount(accountNumber);
//
//            if(accountNumber == null) {
//                System.out.println("Account does not exist!");
//
//            } else {
//                System.out.println("Account deleted!");
//            }
//            connection.close();
//            preparedStatement.close();
//        } catch (SQLException e) {
//            System.out.println("Error " + e.getMessage());
//        }
//    }

    public void deleteAccount() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter account number: ");
            String accountNumber = scanner.nextLine();

            String ssn = authenticatedUser.getSSN();
            accountRepository.deleteAccount(accountNumber, ssn);
      }

//    public void deleteAccountByAccountNumber() {
//        try {
//            scanner = new Scanner(System.in);
//            System.out.println("Enter account number: ");
//            String accountNumber = scanner.nextLine();
//
//            Connection connection = super.getConnection();
//            String query = "DELETE FROM account WHERE account_number = ? AND SSN = ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, accountNumber);
//
//            String ssn = authenticatedUser.getSSN();
//            preparedStatement.setString(2, ssn);
//
//            preparedStatement.executeUpdate();
////            accountRepository.removeSelectedAccount(accountNumber);
//
//            if(accountNumber == null) {
//                System.out.println("Account does not exist!");
//
//            } else {
//                System.out.println("Account deleted!");
//            }
//
////            System.out.println("Account deleted!");
//
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Error " + e.getMessage());
//        }
//
//    }

//    public void deleteAccountByAccountNumber() {
//        try {
//            scanner = new Scanner(System.in);
//            System.out.println("Enter account number: ");
//            String accountNumber = scanner.nextLine();
//
//            Connection connection = super.getConnection();
//
//            String query = "SELECT account_name " +
//                    "FROM account " +
//                    "WHERE account_number = ? " +
//                    "  AND SSN = ? ";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, accountNumber);
//            String ssn = authenticatedUser.getSSN();
//            preparedStatement.setString(2, ssn);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                String accountName = resultSet.getString("account_name");
//                System.out.println(" accountName: " + accountName );
//
//            }
//
//            preparedStatement.executeQuery();
////            accountRepository.removeSelectedAccount(accountNumber);
//
//            if(accountNumber == null) {
//                System.out.println("Account does not exist!");
//
//            } else {
//                System.out.println("Account deleted!");
//            }
//
//            resultSet.close();
//            preparedStatement.close();
//            connection.close();
//        } catch (SQLException e) {
//            System.out.println("Error " + e.getMessage());
//        }
//
//    }






}

