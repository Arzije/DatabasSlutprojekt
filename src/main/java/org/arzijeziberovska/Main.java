package org.arzijeziberovska;

import org.arzijeziberovska.database.DatabaseHandler;
import org.arzijeziberovska.repository.AccountRepository;
import org.arzijeziberovska.repository.TransactionRepository;
import org.arzijeziberovska.repository.UserRepository;
import org.arzijeziberovska.service.AccountService;
import org.arzijeziberovska.service.PasswordService;
import org.arzijeziberovska.service.TransactionService;
import org.arzijeziberovska.service.UserService;
import org.arzijeziberovska.view.InitialView;

import static org.arzijeziberovska.database.DatabaseConnection.configureDataSource;

public class Main {

    public static void main(String[] args) {
        configureDataSource();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.createTableUser();
        databaseHandler.createTableAccount();
        databaseHandler.createTableTransaction();

        UserRepository userRepository = new UserRepository();
        PasswordService passwordService = new PasswordService();
        UserService userService = new UserService(userRepository, passwordService);

        AccountRepository accountRepository = new AccountRepository();
        AccountService accountService = new AccountService(accountRepository);

        TransactionRepository transactionRepository = new TransactionRepository();
        TransactionService transactionService = new TransactionService(transactionRepository);

        InitialView initialView = new InitialView
                (userService,
                accountRepository, transactionService,
                transactionRepository, accountService);
        initialView.firstView();

    }
}