package org.arzijeziberovska.service;

import org.arzijeziberovska.database.DatabaseConnection;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.TransactionRepository;

import java.math.BigDecimal;

public class TransactionService extends DatabaseConnection {
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    //kontrollerar om användaren är inloggad och skickar sedan vidare till repository
    public void transferMoney(User authenticatedUser, String accountNumberFrom, String accountNumberTo, BigDecimal amount, String message) {
        if (authenticatedUser != null)  {
            String ssn = authenticatedUser.getSSN();
            transactionRepository.transferMoney(accountNumberFrom, accountNumberTo, amount, message, ssn);
        } else {
            System.out.println("User is not authenticated.");
        }
    }
}

