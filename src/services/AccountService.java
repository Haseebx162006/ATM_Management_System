package services;

import models.Account;
import utils.FileHandler;
import utils.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for account-related operations.
 * Handles account creation, retrieval, and updates.
 */
public class AccountService {
    private List<Account> accounts;
    
    /**
     * Constructor that loads accounts from file.
     */
    public AccountService() {
        loadAccounts();
    }
    
    /**
     * Loads accounts from file storage.
     */
    private void loadAccounts() {
        accounts = FileHandler.readAccounts();
    }
    
    /**
     * Saves accounts to file storage.
     */
    private void saveAccounts() {
        FileHandler.writeAccounts(accounts);
    }
    
    /**
     * Creates a new account.
     * 
     * @param name The account holder's name
     * @param pin The PIN for the account (will be hashed)
     * @return The newly created Account object, or null if creation fails
     */
    public Account createAccount(String name, String pin) {
        if (name == null || name.trim().isEmpty() || pin == null || pin.length() < 4) {
            return null;
        }
        
        String accountNumber = SecurityUtils.generateAccountNumber();
        // Ensure account number is unique
        while (getAccountByNumber(accountNumber) != null) {
            accountNumber = SecurityUtils.generateAccountNumber();
        }
        
        String hashedPin = SecurityUtils.hashPin(pin);
        Account account = new Account(accountNumber, name.trim(), hashedPin, 0.0, LocalDateTime.now());
        
        accounts.add(account);
        saveAccounts();
        
        return account;
    }
    
    /**
     * Retrieves an account by account number.
     * 
     * @param accountNumber The account number to search for
     * @return Account object if found, null otherwise
     */
    public Account getAccountByNumber(String accountNumber) {
        loadAccounts(); // Refresh from file
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Updates an account's PIN.
     * 
     * @param accountNumber The account number
     * @param newPin The new PIN (will be hashed)
     * @return true if update successful, false otherwise
     */
    public boolean updatePin(String accountNumber, String newPin) {
        if (newPin == null || newPin.length() < 4) {
            return false;
        }
        
        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            account.setHashedPin(SecurityUtils.hashPin(newPin));
            saveAccounts();
            return true;
        }
        
        return false;
    }
    
    /**
     * Updates account balance.
     * 
     * @param accountNumber The account number
     * @param newBalance The new balance
     * @return true if update successful, false otherwise
     */
    public boolean updateBalance(String accountNumber, double newBalance) {
        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            account.setBalance(newBalance);
            saveAccounts();
            return true;
        }
        return false;
    }
    
    /**
     * Deposits money into an account.
     * 
     * @param accountNumber The account number
     * @param amount The amount to deposit
     * @return true if deposit successful, false otherwise
     */
    public boolean deposit(String accountNumber, double amount) {
        if (amount <= 0) {
            return false;
        }
        
        Account account = getAccountByNumber(accountNumber);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            saveAccounts();
            return true;
        }
        return false;
    }
    
    /**
     * Withdraws money from an account.
     * 
     * @param accountNumber The account number
     * @param amount The amount to withdraw
     * @return true if withdrawal successful, false otherwise
     */
    public boolean withdraw(String accountNumber, double amount) {
        if (amount <= 0) {
            return false;
        }
        
        Account account = getAccountByNumber(accountNumber);
        if (account != null && account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            saveAccounts();
            return true;
        }
        return false;
    }
    
    /**
     * Transfers money from one account to another.
     * 
     * @param fromAccountNumber The source account number
     * @param toAccountNumber The destination account number
     * @param amount The amount to transfer
     * @return true if transfer successful, false otherwise
     */
    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        if (amount <= 0) {
            return false;
        }
        
        Account fromAccount = getAccountByNumber(fromAccountNumber);
        Account toAccount = getAccountByNumber(toAccountNumber);
        
        if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= amount) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            saveAccounts();
            return true;
        }
        
        return false;
    }
    
    /**
     * Gets all accounts (for admin purposes if needed).
     * 
     * @return List of all accounts
     */
    public List<Account> getAllAccounts() {
        loadAccounts();
        return accounts;
    }
}

