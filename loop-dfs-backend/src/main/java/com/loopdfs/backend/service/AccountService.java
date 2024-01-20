package com.loopdfs.backend.service;

import com.loopdfs.backend.model.Account;
import com.loopdfs.backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Retrieve all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Retrieve an account by ID
    public Account getAccountById(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        return optionalAccount.orElse(null);
    }

    // Create a new account
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Update an existing account
    public Account updateAccount(Long accountId, Account updatedAccount) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isPresent()) {
            Account existingAccount = optionalAccount.get();
            existingAccount.setIban(updatedAccount.getIban());
            existingAccount.setBicSwift(updatedAccount.getBicSwift());
            existingAccount.setClientId(updatedAccount.getClientId());
            return accountRepository.save(existingAccount);
        } else {
            return null;
        }
    }

    // Delete an account
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }
}
