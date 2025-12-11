package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.Account;
import services.AccountService;
import services.AuthService;
import services.TransactionService;

/**
 * Controller for the transfer screen.
 * Handles money transfer operations between accounts.
 */
public class TransferController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label currentBalanceLabel;
    @FXML
    private TextField targetAccountField;
    @FXML
    private TextField amountField;
    @FXML
    private Button transferButton;
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
        // Get fresh account data from file
        Account currentAccount = authService.getCurrentAccount();
        if (currentAccount != null) {
            double balance = currentAccount.getBalance();
            currentBalanceLabel.setText("Current Balance: $" + String.format("%.2f", balance));
        }
    }

    @FXML
    private void handleTransfer() {
        // Clear previous messages
        messageLabel.setVisible(false);
        errorLabel.setVisible(false);
        messageLabel.setText("");
        errorLabel.setText("");

        try {
            String targetAccountNumber = targetAccountField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());

            if (targetAccountNumber.isEmpty()) {
                errorLabel.setText("Please enter target account number");
                errorLabel.setVisible(true);
                return;
            }

            if (amount <= 0) {
                errorLabel.setText("Amount must be greater than zero");
                errorLabel.setVisible(true);
                return;
            }

            Account currentAccount = authService.getCurrentAccount();
            String fromAccountNumber = currentAccount.getAccountNumber();

            if (fromAccountNumber.equals(targetAccountNumber)) {
                errorLabel.setText("Cannot transfer to the same account");
                errorLabel.setVisible(true);
                return;
            }

            Account targetAccount = accountService.getAccountByNumber(targetAccountNumber);
            if (targetAccount == null) {
                errorLabel.setText("Target account not found");
                errorLabel.setVisible(true);
                return;
            }

            double currentBalance = currentAccount.getBalance();
            if (amount > currentBalance) {
                errorLabel.setText("Insufficient balance");
                errorLabel.setVisible(true);
                return;
            }

            // Perform the transfer
            if (accountService.transfer(fromAccountNumber, targetAccountNumber, amount)) {
                // Create transaction record
                transactionService.createTransferTransaction(fromAccountNumber, targetAccountNumber, amount);

                messageLabel.setText("✅ Transfer successful! Amount: $" + String.format("%.2f", amount) +
                        " to " + targetAccountNumber);
                messageLabel.setVisible(true);
                errorLabel.setVisible(false);
                targetAccountField.clear();
                amountField.clear();

                // Refresh balance immediately by reading fresh data from file
                updateBalance();
            } else {
                errorLabel.setText("Transfer failed. Please try again.");
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