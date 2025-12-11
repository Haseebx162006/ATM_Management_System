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
    private String loggedInAccountNumber; // Store account number instead of Account object

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
            this.loggedInAccountNumber = accountNumber;
            return true;
        }
        return false;
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        this.loggedInAccountNumber = null;
    }

    /**
     * Gets the currently logged-in account.
     * Always fetches fresh data from storage.
     *
     * @return Current Account object, or null if not logged in
     */
    public Account getCurrentAccount() {
        if (loggedInAccountNumber != null) {
            return accountService.getAccountByNumber(loggedInAccountNumber);
        }
        return null;
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return true if logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedInAccountNumber != null;
    }

    /**
     * Refreshes the current account data from storage.
     * Since getCurrentAccount() always fetches fresh data, this is mainly for consistency.
     */
    public void refreshCurrentAccount() {
        // No need to do anything - getCurrentAccount() already fetches fresh data
    }
}