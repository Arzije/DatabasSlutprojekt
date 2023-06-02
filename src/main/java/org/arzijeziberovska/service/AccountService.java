package org.arzijeziberovska.service;

import org.arzijeziberovska.model.Account;
import org.arzijeziberovska.model.User;
import org.arzijeziberovska.repository.AccountRepository;

import java.util.Scanner;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    //säkerställer att det är rätt användare och en extra koll innan kontot raderas
    public void deleteAccount(String accountNumber, User authenticatedUser) {
        Scanner scanner = new Scanner(System.in);
        String ssn = authenticatedUser.getSSN();

        Account account = accountRepository.getAccount(accountNumber);
        if (account == null || !account.getSSN().equals(ssn)) {
            System.out.println("Invalid account number or the account does not belong to you.");
            return;
        }

        System.out.println("Are you sure you want to delete the account? (yes/no)");
        String confirmation = scanner.nextLine();
        if (!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("delete")) {
            System.out.println("Account deletion cancelled.");
            return;
        }

        accountRepository.deleteAccount(accountNumber, ssn);
    }

}

