package org.arzijeziberovska.repository;



import org.arzijeziberovska.database.DatabaseConnection;
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

    public TransactionRepository(User authenticatedUser, TransactionService transactionService) {
        this.authenticatedUser = authenticatedUser;
        this.transactionService = transactionService;
    }


    public BigDecimal getBalanceByAccountNumber() {
        try {
            Connection connection = getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the account number: ");
            String accountNumber = scanner.nextLine().trim();

            String query = "SELECT balance FROM account WHERE SSN = ? AND account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            String ssn = authenticatedUser.getSSN();
            preparedStatement.setString(1, ssn);
            preparedStatement.setString(2, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                BigDecimal balance = resultSet.getBigDecimal("balance");
                System.out.println("Account balance: " + balance);
                if (balance != null) {
                    transactionService.transferMoney(balance);
                }
                System.out.println("Account balance: " + balance);
            } else {
                System.out.println("Account not found!");
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void getTranferLogg(){
        // denna ska anropas varje gång, men ska ersätta den befintliga loggen för att kunna sortas
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            String query = "SELECT account_number FROM transactions ORDER BY created WHERE SSN = ? ";


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

//    public void transferMoney(){
//        try {
//            Connection connection = getConnection();
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.print("Enter the account number you want to transfer from: ");
//            String accountNumberFrom = scanner.nextLine().trim();
//
//            System.out.print("Enter the account number you want to transfer to: ");
//            String accountNumberTo = scanner.nextLine().trim();
//
//            System.out.print("Enter the amount you want to transfer: ");
//            BigDecimal amount = scanner.nextBigDecimal();
//
//            String query = "UPDATE account SET balance = balance - ? WHERE account_number = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setBigDecimal(1, amount);
//            preparedStatement.setString(2, accountNumberFrom);
//
//            preparedStatement.executeUpdate();
//
//            query = "UPDATE account SET balance = balance + ? WHERE account_number = ?";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setBigDecimal(1, amount);
//            preparedStatement.setString(2, accountNumberTo);
//
//            preparedStatement.executeUpdate();
//
//            System.out.println("Transfer successful!");
//
//            preparedStatement.close();
//            connection.close();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}

