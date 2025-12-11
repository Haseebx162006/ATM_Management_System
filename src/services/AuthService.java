package services;

import models.Account;
import utils.SecurityUtils;

/**
 * Service class for authentication operations.
 * Handles login and PIN verification.
 * Uses singleton pattern to maintain session across controllers.
 */
public class AuthService {
    private static AuthService instance;
    private AccountService accountService;
    private Account currentAccount;
    
    /**
     * Private constructor for singleton pattern.
     */
    private AuthService() {
        this.accountService = new AccountService();
    }
    
    /**
     * Gets the singleton instance of AuthService.
     * 
     * @return The AuthService instance
     */
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }
    
    /**
     * Authenticates a user with account number and PIN.
     * 
     * @param accountNumber The account number
     * @param pin The PIN to verify
     * @return true if authentication successful, false otherwise
     */
    public boolean login(String accountNumber, String pin) {
        Account account = accountService.getAccountByNumber(accountNumber);
        if (account != null && SecurityUtils.verifyPin(pin, account.getHashedPin())) {
            this.currentAccount = account;
            return true;
        }
        return false;
    }
    
    /**
     * Logs out the current user.
     */
    public void logout() {
        this.currentAccount = null;
    }
    
    /**
     * Gets the currently logged-in account.
     * 
     * @return Current Account object, or null if not logged in
     */
    public Account getCurrentAccount() {
        return currentAccount;
    }
    
    /**
     * Checks if a user is currently logged in.
     * 
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return currentAccount != null;
    }
    
    /**
     * Refreshes the current account data from storage.
     */
    public void refreshCurrentAccount() {
        if (currentAccount != null) {
            Account refreshed = accountService.getAccountByNumber(currentAccount.getAccountNumber());
            if (refreshed != null) {
                this.currentAccount = refreshed;
            }
        }
    }
}

