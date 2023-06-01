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

}

