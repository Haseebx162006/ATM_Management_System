package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import services.AuthService;

/**
 * Controller for the login screen.
 * Handles user authentication and navigation.
 */
public class LoginController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField accountNumberField;
    @FXML
    private PasswordField pinField;
    @FXML
    private Button loginButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button forgotPinButton;
    @FXML
    private Label errorLabel;
    
    private AuthService authService;
    
    @FXML
    public void initialize() {
        authService = AuthService.getInstance();
        errorLabel.setText("");
        errorLabel.setVisible(false);
        
        // Add Enter key support
        pinField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
        
        accountNumberField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                pinField.requestFocus();
            }
        });
    }
    
    @FXML
    private void handleLogin() {
        String accountNumber = accountNumberField.getText().trim();
        String pin = pinField.getText();
        
        // Clear previous errors
        errorLabel.setVisible(false);
        errorLabel.setText("");
        
        if (accountNumber.isEmpty() || pin.isEmpty()) {
            errorLabel.setText("Please enter account number and PIN");
            errorLabel.setVisible(true);
            return;
        }
        
        if (accountNumber.length() != 10) {
            errorLabel.setText("Account number must be 10 digits");
            errorLabel.setVisible(true);
            return;
        }
        
        if (pin.length() < 4) {
            errorLabel.setText("PIN must be at least 4 digits");
            errorLabel.setVisible(true);
            return;
        }
        
        if (authService.login(accountNumber, pin)) {
            errorLabel.setVisible(false);
            loadDashboard();
        } else {
            errorLabel.setText("Invalid account number or PIN");
            errorLabel.setVisible(true);
            pinField.clear();
        }
    }
    
    @FXML
    private void handleCreateAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CreateAccount.fxml"));
            Parent root = loader.load();
            Scene scene = rootPane.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleForgotPin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ForgotPin.fxml"));
            Parent root = loader.load();
            Scene scene = rootPane.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

