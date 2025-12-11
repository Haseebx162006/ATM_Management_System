package models;

import java.time.LocalDateTime;

/**
 * Account model representing a bank account in the ATM system.
 * Stores account information including account number, name, PIN hash, balance, and creation date.
 */
public class Account {
    private String accountNumber;
    private String name;
    private String hashedPin;
    private double balance;
    private LocalDateTime creationDate;
    
    /**
     * Default constructor.
     */
    public Account() {
        this.creationDate = LocalDateTime.now();
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param accountNumber The unique account number
     * @param name The account holder's name
     * @param hashedPin The hashed PIN for security
     * @param balance The account balance
     * @param creationDate The account creation date
     */
    public Account(String accountNumber, String name, String hashedPin, double balance, LocalDateTime creationDate) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.hashedPin = hashedPin;
        this.balance = balance;
        this.creationDate = creationDate;
    }
    
    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getHashedPin() {
        return hashedPin;
    }
    
    public void setHashedPin(String hashedPin) {
        this.hashedPin = hashedPin;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    
    /**
     * Converts account to string format for file storage.
     * Format: accountNumber|name|hashedPin|balance|creationDate
     * 
     * @return String representation of the account
     */
    public String toFileString() {
        return accountNumber + "|" + name + "|" + hashedPin + "|" + balance + "|" + creationDate.toString();
    }
    
    /**
     * Creates an Account object from a file string.
     * 
     * @param fileString The string from the file
     * @return Account object or null if parsing fails
     */
    public static Account fromFileString(String fileString) {
        try {
            String[] parts = fileString.split("\\|");
            if (parts.length == 5) {
                Account account = new Account();
                account.setAccountNumber(parts[0]);
                account.setName(parts[1]);
                account.setHashedPin(parts[2]);
                account.setBalance(Double.parseDouble(parts[3]));
                account.setCreationDate(LocalDateTime.parse(parts[4]));
                return account;
            }
        } catch (Exception e) {
            System.err.println("Error parsing account: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                '}';
    }
}

