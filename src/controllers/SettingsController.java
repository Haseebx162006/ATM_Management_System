package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import services.AccountService;
import services.AuthService;
import services.SettingsService;
import utils.SecurityUtils;

/**
 * Controller for the settings screen.
 * Handles PIN change and theme settings.
 */
public class SettingsController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private PasswordField currentPinField;
    @FXML
    private PasswordField newPinField;
    @FXML
    private PasswordField confirmPinField;
    @FXML
    private Button changePinButton;
    @FXML
    private CheckBox darkModeCheckBox;
    @FXML
    private Button backButton;
    @FXML
    private Label messageLabel;
    @FXML
    private Label errorLabel;
    
    private AuthService authService;
    private AccountService accountService;
    private SettingsService settingsService;
    
    @FXML
    public void initialize() {
        authService = AuthService.getInstance();
        accountService = new AccountService();
        settingsService = new SettingsService();
        
        if (!authService.isLoggedIn()) {
            loadDashboard();
            return;
        }
        
        darkModeCheckBox.setSelected(true); // Dark mode is now default and always enabled
        darkModeCheckBox.setDisable(true); // Disable toggle since dark mode is always on
        messageLabel.setText("");
        errorLabel.setText("");
        messageLabel.setVisible(false);
        errorLabel.setVisible(false);
    }
    
    @FXML
    private void handleChangePin() {
        String currentPin = currentPinField.getText();
        String newPin = newPinField.getText();
        String confirmPin = confirmPinField.getText();
        
        // Clear previous messages
        messageLabel.setVisible(false);
        errorLabel.setVisible(false);
        messageLabel.setText("");
        errorLabel.setText("");
        
        if (currentPin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()) {
            errorLabel.setText("Please fill in all fields");
            errorLabel.setVisible(true);
            return;
        }
        
        // Verify current PIN
        if (!SecurityUtils.verifyPin(currentPin, authService.getCurrentAccount().getHashedPin())) {
            errorLabel.setText("Current PIN is incorrect");
            errorLabel.setVisible(true);
            return;
        }
        
        if (newPin.length() < 4) {
            errorLabel.setText("PIN must be at least 4 digits");
            errorLabel.setVisible(true);
            return;
        }
        
        if (!newPin.equals(confirmPin)) {
            errorLabel.setText("New PINs do not match");
            errorLabel.setVisible(true);
            return;
        }
        
        String accountNumber = authService.getCurrentAccount().getAccountNumber();
        if (accountService.updatePin(accountNumber, newPin)) {
            authService.refreshCurrentAccount();
            messageLabel.setText("✅ PIN changed successfully!");
            messageLabel.setVisible(true);
            errorLabel.setVisible(false);
            currentPinField.clear();
            newPinField.clear();
            confirmPinField.clear();
        } else {
            errorLabel.setText("Failed to change PIN. Please try again.");
            errorLabel.setVisible(true);
        }
    }
    
    @FXML
    private void handleThemeToggle() {
        boolean darkMode = darkModeCheckBox.isSelected();
        settingsService.setDarkMode(darkMode);
        
        // Reload scene with new theme
        try {
            Scene scene = rootPane.getScene();
            if (darkMode) {
                scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());
            } else {
                scene.getStylesheets().remove(getClass().getResource("/dark-theme.css").toExternalForm());
            }
        } catch (Exception e) {
            e.printStackTrace();
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

