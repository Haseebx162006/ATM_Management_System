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
 * Controller for the create account screen.
 * Handles new account creation.
 */
public class CreateAccountController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField pinField;
    @FXML
    private PasswordField confirmPinField;
    @FXML
    private Button createButton;
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
        messageLabel.setWrapText(true);
    }
    
    @FXML
    private void handleCreate() {
        String name = nameField.getText().trim();
        String pin = pinField.getText();
        String confirmPin = confirmPinField.getText();
        
        // Clear previous messages
        messageLabel.setVisible(false);
        errorLabel.setVisible(false);
        messageLabel.setText("");
        errorLabel.setText("");
        
        if (name.isEmpty()) {
            errorLabel.setText("Please enter your name");
            errorLabel.setVisible(true);
            return;
        }
        
        if (pin.length() < 4) {
            errorLabel.setText("PIN must be at least 4 digits");
            errorLabel.setVisible(true);
            return;
        }
        
        if (!pin.equals(confirmPin)) {
            errorLabel.setText("PINs do not match");
            errorLabel.setVisible(true);
            return;
        }
        
        Account account = accountService.createAccount(name, pin);
        if (account != null) {
            String accountNumber = account.getAccountNumber();
            messageLabel.setText("✅ Account created successfully!\n\nYour Account Number: " + accountNumber + "\n\nPlease save this number securely!");
            messageLabel.setVisible(true);
            errorLabel.setVisible(false);
            
            // Clear fields
            nameField.clear();
            pinField.clear();
            confirmPinField.clear();
        } else {
            errorLabel.setText("Failed to create account. Please try again.");
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

