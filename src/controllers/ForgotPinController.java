package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.Account;
import services.AccountService;

/**
 * Controller for the forgot PIN screen.
 * Handles PIN reset functionality.
 */
public class ForgotPinController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField accountNumberField;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField newPinField;
    @FXML
    private PasswordField confirmPinField;
    @FXML
    private Button resetButton;
    @FXML
    private Button backButton;
    @FXML
    private Label messageLabel;
    @FXML
    private Label errorLabel;
    
    private AccountService accountService;
    
    @FXML
    public void initialize() {
        accountService = new AccountService();
        messageLabel.setText("");
        errorLabel.setText("");
        messageLabel.setVisible(false);
        errorLabel.setVisible(false);
    }
    
    @FXML
    private void handleReset() {
        // Clear previous messages
        messageLabel.setVisible(false);
        errorLabel.setVisible(false);
        messageLabel.setText("");
        errorLabel.setText("");
        
        String accountNumber = accountNumberField.getText().trim();
        String name = nameField.getText().trim();
        String newPin = newPinField.getText();
        String confirmPin = confirmPinField.getText();
        
        if (accountNumber.isEmpty() || name.isEmpty()) {
            errorLabel.setText("Please enter account number and name");
            errorLabel.setVisible(true);
            return;
        }
        
        Account account = accountService.getAccountByNumber(accountNumber);
        if (account == null || !account.getName().equalsIgnoreCase(name)) {
            errorLabel.setText("Account not found or name does not match");
            errorLabel.setVisible(true);
            return;
        }
        
        if (newPin.length() < 4) {
            errorLabel.setText("PIN must be at least 4 digits");
            errorLabel.setVisible(true);
            return;
        }
        
        if (!newPin.equals(confirmPin)) {
            errorLabel.setText("PINs do not match");
            errorLabel.setVisible(true);
            return;
        }
        
        if (accountService.updatePin(accountNumber, newPin)) {
            messageLabel.setText("✅ PIN reset successfully!");
            messageLabel.setVisible(true);
            errorLabel.setVisible(false);
            
            // Clear fields
            accountNumberField.clear();
            nameField.clear();
            newPinField.clear();
            confirmPinField.clear();
        } else {
            errorLabel.setText("Failed to reset PIN. Please try again.");
            errorLabel.setVisible(true);
        }
    }
    
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();
            Scene scene = rootPane.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

