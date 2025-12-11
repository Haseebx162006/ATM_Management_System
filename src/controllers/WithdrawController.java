package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.Transaction.TransactionType;
import services.AccountService;
import services.AuthService;
import services.TransactionService;

/**
 * Controller for the withdraw screen.
 * Handles money withdrawal operations.
 */
public class WithdrawController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label currentBalanceLabel;
    @FXML
    private TextField amountField;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button backButton;
    @FXML
    private Label messageLabel;
    @FXML
    private Label errorLabel;
    
    private AuthService authService;
    private AccountService accountService;
    private TransactionService transactionService;
    
    @FXML
    public void initialize() {
        authService = AuthService.getInstance();
        accountService = new AccountService();
        transactionService = new TransactionService();
        
        if (!authService.isLoggedIn()) {
            loadDashboard();
            return;
        }
        
        updateBalance();
        messageLabel.setText("");
        errorLabel.setText("");
        messageLabel.setVisible(false);
        errorLabel.setVisible(false);
    }
    
    private void updateBalance() {
        authService.refreshCurrentAccount();
        if (authService.getCurrentAccount() != null) {
            currentBalanceLabel.setText("Current Balance: $" + 
                String.format("%.2f", authService.getCurrentAccount().getBalance()));
        }
    }
    
    @FXML
    private void handleWithdraw() {
        // Clear previous messages
        messageLabel.setVisible(false);
        errorLabel.setVisible(false);
        messageLabel.setText("");
        errorLabel.setText("");
        
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            
            if (amount <= 0) {
                errorLabel.setText("Amount must be greater than zero");
                errorLabel.setVisible(true);
                return;
            }
            
            String accountNumber = authService.getCurrentAccount().getAccountNumber();
            double currentBalance = authService.getCurrentAccount().getBalance();
            
            if (amount > currentBalance) {
                errorLabel.setText("Insufficient balance");
                errorLabel.setVisible(true);
                return;
            }
            
            if (accountService.withdraw(accountNumber, amount)) {
                transactionService.createTransaction(accountNumber, TransactionType.WITHDRAW, 
                    amount, "Withdrawal");
                messageLabel.setText("✅ Withdrawal successful! Amount: $" + String.format("%.2f", amount));
                messageLabel.setVisible(true);
                errorLabel.setVisible(false);
                amountField.clear();
                updateBalance();
            } else {
                errorLabel.setText("Withdrawal failed. Please try again.");
                errorLabel.setVisible(true);
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Please enter a valid amount");
            errorLabel.setVisible(true);
        }
    }
    
    @FXML
    private void handleBack() {
        loadDashboard();
    }
    
    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = rootPane.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

