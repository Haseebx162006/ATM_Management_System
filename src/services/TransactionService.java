package services;

import models.Transaction;
import models.Transaction.TransactionType;
import utils.FileHandler;
import utils.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for transaction-related operations.
 * Handles transaction creation, retrieval, and history.
 */
public class TransactionService {
    private List<Transaction> transactions;
    
    /**
     * Constructor that loads transactions from file.
     */
    public TransactionService() {
        loadTransactions();
    }
    
    /**
     * Loads transactions from file storage.
     */
    private void loadTransactions() {
        transactions = FileHandler.readTransactions();
    }
    
    /**
     * Creates a new transaction record.
     * 
     * @param accountNumber The account number
     * @param type The transaction type
     * @param amount The transaction amount
     * @param description Optional description
     * @return The created Transaction object
     */
    public Transaction createTransaction(String accountNumber, TransactionType type, 
                                        double amount, String description) {
        String transactionId = SecurityUtils.generateTransactionId();
        Transaction transaction = new Transaction(transactionId, accountNumber, type, 
                                                 amount, LocalDateTime.now(), description);
        
        FileHandler.appendTransaction(transaction);
        transactions.add(transaction);
        
        return transaction;
    }
    
    /**
     * Creates a transfer transaction record for both accounts.
     * 
     * @param fromAccountNumber The source account
     * @param toAccountNumber The destination account
     * @param amount The transfer amount
     * @return The created Transaction object for the sender
     */
    public Transaction createTransferTransaction(String fromAccountNumber, String toAccountNumber, 
                                                double amount) {
        LocalDateTime now = LocalDateTime.now();
        
        // Create transaction for sender (outgoing transfer)
        String transactionId1 = SecurityUtils.generateTransactionId();
        Transaction senderTransaction = new Transaction();
        senderTransaction.setTransactionId(transactionId1);
        senderTransaction.setAccountNumber(fromAccountNumber);
        senderTransaction.setTargetAccountNumber(toAccountNumber);
        senderTransaction.setType(TransactionType.TRANSFER);
        senderTransaction.setAmount(amount);
        senderTransaction.setTimestamp(now);
        senderTransaction.setDescription("Transfer to " + toAccountNumber);
        
        FileHandler.appendTransaction(senderTransaction);
        transactions.add(senderTransaction);
        
        // Create transaction for receiver (incoming transfer/deposit)
        String transactionId2 = SecurityUtils.generateTransactionId();
        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setTransactionId(transactionId2);
        receiverTransaction.setAccountNumber(toAccountNumber);
        receiverTransaction.setTargetAccountNumber(null);
        receiverTransaction.setType(TransactionType.DEPOSIT);
        receiverTransaction.setAmount(amount);
        receiverTransaction.setTimestamp(now);
        receiverTransaction.setDescription("Transfer from " + fromAccountNumber);
        
        FileHandler.appendTransaction(receiverTransaction);
        transactions.add(receiverTransaction);
        
        return senderTransaction;
    }
    
    /**
     * Gets all transactions for a specific account.
     * 
     * @param accountNumber The account number
     * @return List of Transaction objects
     */
    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        loadTransactions(); // Refresh from file
        return transactions.stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber) || 
                           (t.getTargetAccountNumber() != null && 
                            t.getTargetAccountNumber().equals(accountNumber)))
                .sorted((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the last N transactions for an account (mini statement).
     * 
     * @param accountNumber The account number
     * @param limit The maximum number of transactions to return
     * @return List of Transaction objects
     */
    public List<Transaction> getMiniStatement(String accountNumber, int limit) {
        List<Transaction> allTransactions = getTransactionsByAccount(accountNumber);
        return allTransactions.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets all transactions (for admin purposes if needed).
     * 
     * @return List of all transactions
     */
    public List<Transaction> getAllTransactions() {
        loadTransactions();
        return transactions;
    }
}

