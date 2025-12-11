package models;

import java.time.LocalDateTime;

/**
 * Transaction model representing a financial transaction in the ATM system.
 * Stores transaction details including type, amount, accounts involved, and timestamp.
 */
public class Transaction {
    public enum TransactionType {
        DEPOSIT, WITHDRAW, TRANSFER
    }
    
    private String transactionId;
    private String accountNumber;
    private String targetAccountNumber; // For transfers
    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;
    private String description;
    
    /**
     * Default constructor.
     */
    public Transaction() {
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * Constructor with parameters.
     * 
     * @param transactionId Unique transaction ID
     * @param accountNumber The account number involved
     * @param type The type of transaction
     * @param amount The transaction amount
     * @param timestamp When the transaction occurred
     * @param description Optional description
     */
    public Transaction(String transactionId, String accountNumber, TransactionType type, 
                      double amount, LocalDateTime timestamp, String description) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
    }
    
    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }
    
    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Converts transaction to string format for file storage.
     * Format: transactionId|accountNumber|targetAccountNumber|type|amount|timestamp|description
     * 
     * @return String representation of the transaction
     */
    public String toFileString() {
        String target = targetAccountNumber != null ? targetAccountNumber : "";
        String desc = description != null ? description : "";
        return transactionId + "|" + accountNumber + "|" + target + "|" + 
               type.name() + "|" + amount + "|" + timestamp.toString() + "|" + desc;
    }
    
    /**
     * Creates a Transaction object from a file string.
     * 
     * @param fileString The string from the file
     * @return Transaction object or null if parsing fails
     */
    public static Transaction fromFileString(String fileString) {
        try {
            String[] parts = fileString.split("\\|");
            if (parts.length >= 6) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(parts[0]);
                transaction.setAccountNumber(parts[1]);
                if (!parts[2].isEmpty()) {
                    transaction.setTargetAccountNumber(parts[2]);
                }
                transaction.setType(TransactionType.valueOf(parts[3]));
                transaction.setAmount(Double.parseDouble(parts[4]));
                transaction.setTimestamp(LocalDateTime.parse(parts[5]));
                if (parts.length > 6) {
                    transaction.setDescription(parts[6]);
                }
                return transaction;
            }
        } catch (Exception e) {
            System.err.println("Error parsing transaction: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}

