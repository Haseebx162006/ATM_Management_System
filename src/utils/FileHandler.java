package utils;

import models.Account;
import models.Transaction;
import models.Settings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for file operations.
 * Handles reading and writing of accounts, transactions, and settings.
 */
public class FileHandler {
    private static final String STORAGE_DIR = "storage";
    private static final String ACCOUNTS_FILE = STORAGE_DIR + File.separator + "accounts.txt";
    private static final String TRANSACTIONS_FILE = STORAGE_DIR + File.separator + "transactions.txt";
    private static final String SETTINGS_FILE = STORAGE_DIR + File.separator + "settings.txt";
    
    /**
     * Ensures the storage directory exists.
     */
    private static void ensureStorageDirectory() {
        try {
            Path path = Paths.get(STORAGE_DIR);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.err.println("Error creating storage directory: " + e.getMessage());
        }
    }
    
    /**
     * Reads all accounts from the accounts file.
     * 
     * @return List of Account objects
     */
    public static List<Account> readAccounts() {
        ensureStorageDirectory();
        List<Account> accounts = new ArrayList<>();
        File file = new File(ACCOUNTS_FILE);
        
        if (!file.exists()) {
            return accounts;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Account account = Account.fromFileString(line);
                    if (account != null) {
                        accounts.add(account);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts: " + e.getMessage());
        }
        
        return accounts;
    }
    
    /**
     * Writes all accounts to the accounts file.
     * 
     * @param accounts List of Account objects to write
     */
    public static void writeAccounts(List<Account> accounts) {
        ensureStorageDirectory();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account account : accounts) {
                writer.write(account.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing accounts: " + e.getMessage());
        }
    }
    
    /**
     * Reads all transactions from the transactions file.
     * 
     * @return List of Transaction objects
     */
    public static List<Transaction> readTransactions() {
        ensureStorageDirectory();
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        
        if (!file.exists()) {
            return transactions;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Transaction transaction = Transaction.fromFileString(line);
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transactions: " + e.getMessage());
        }
        
        return transactions;
    }
    
    /**
     * Writes all transactions to the transactions file.
     * 
     * @param transactions List of Transaction objects to write
     */
    public static void writeTransactions(List<Transaction> transactions) {
        ensureStorageDirectory();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing transactions: " + e.getMessage());
        }
    }
    
    /**
     * Appends a single transaction to the transactions file.
     * 
     * @param transaction The transaction to append
     */
    public static void appendTransaction(Transaction transaction) {
        ensureStorageDirectory();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            writer.write(transaction.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending transaction: " + e.getMessage());
        }
    }
    
    /**
     * Reads settings from the settings file.
     * 
     * @return Settings object, or default settings if file doesn't exist
     */
    public static Settings readSettings() {
        ensureStorageDirectory();
        File file = new File(SETTINGS_FILE);
        
        if (!file.exists()) {
            return new Settings();
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null && !line.trim().isEmpty()) {
                return Settings.fromFileString(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading settings: " + e.getMessage());
        }
        
        return new Settings();
    }
    
    /**
     * Writes settings to the settings file.
     * 
     * @param settings The Settings object to write
     */
    public static void writeSettings(Settings settings) {
        ensureStorageDirectory();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SETTINGS_FILE))) {
            writer.write(settings.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing settings: " + e.getMessage());
        }
    }
}

