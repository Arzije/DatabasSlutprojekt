package org.arzijeziberovska.service;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.Account;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.AccountRepository;
import org.arzijeziberovska.repository.UserRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class AccountService extends DatabaseConnection {
    private User authenticatedUser;
    private AccountRepository accountRepository;
    private Scanner scanner;

    public AccountService(User authenticatedUser, AccountRepository accountRepository) {
        this.authenticatedUser = authenticatedUser;
        this.accountRepository = accountRepository;
    }
    public AccountService() {
    }

    public void deleteAccount(String accountNumber, User authenticatedUser) {
        Scanner scanner = new Scanner(System.in);
        String ssn = authenticatedUser.getSSN();

        // Validate Account Ownership
        Account account = accountRepository.getAccount(accountNumber);
        if (account == null || !account.getSSN().equals(ssn)) {
            System.out.println("Invalid account number or the account does not belong to you.");
            return;
        }

        // Confirmation Prompt
        System.out.println("Are you sure you want to delete the account? (yes/no)");
        String confirmation = scanner.nextLine();
        if (!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("delete")) {
            System.out.println("Account deletion cancelled.");
            return;
        }

        // Delete the Account
        accountRepository.deleteAccount(accountNumber, ssn);
    }

}

