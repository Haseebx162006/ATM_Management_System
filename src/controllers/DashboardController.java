package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import services.AuthService;
import services.SettingsService;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller for the dashboard screen.
 * Main screen after login showing account balance and transaction options.
 */
public class DashboardController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button depositButton;
    @FXML
    private Button transferButton;
    @FXML
    private Button checkBalanceButton;
    @FXML
    private Button miniStatementButton;
    @FXML
    private Button transactionHistoryButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;
    
    private AuthService authService;
    private SettingsService settingsService;
    private Timer inactivityTimer;
    private static final long INACTIVITY_TIMEOUT = 5 * 60 * 1000; // 5 minutes
    
    @FXML
    public void initialize() {
        authService = AuthService.getInstance();
        settingsService = new SettingsService();
        
        if (!authService.isLoggedIn()) {
            loadLogin();
            return;
        }
        
        updateDisplay();
        startInactivityTimer();
    }
    
    private void updateDisplay() {
        authService.refreshCurrentAccount();
        if (authService.getCurrentAccount() != null) {
            welcomeLabel.setText("Welcome, " + authService.getCurrentAccount().getName() + "!");
            balanceLabel.setText("Balance: $" + String.format("%.2f", authService.getCurrentAccount().getBalance()));
        }
    }
    
    private void startInactivityTimer() {
        if (inactivityTimer != null) {
            inactivityTimer.cancel();
        }
        
        inactivityTimer = new Timer();
        inactivityTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> {
                    authService.logout();
                    loadLogin();
                });
            }
        }, INACTIVITY_TIMEOUT);
    }
    
    @FXML
    private void handleDeposit() {
        loadScreen("/views/Deposit.fxml");
    }
    
    @FXML
    private void handleWithdraw() {
        loadScreen("/views/Withdraw.fxml");
    }
    
    @FXML
    private void handleTransfer() {
        loadScreen("/views/Transfer.fxml");
    }
    
    @FXML
    private void handleCheckBalance() {
        startInactivityTimer(); // Reset timer on activity
        updateDisplay(); // Refresh balance display
        authService.refreshCurrentAccount();
        if (authService.getCurrentAccount() != null) {
            double balance = authService.getCurrentAccount().getBalance();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Balance");
            alert.setHeaderText("Your Account Balance");
            alert.setContentText("Balance: $" + String.format("%.2f", balance));
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleMiniStatement() {
        loadScreen("/views/MiniStatement.fxml");
    }
    
    @FXML
    private void handleTransactionHistory() {
        loadScreen("/views/TransactionHistory.fxml");
    }
    
    @FXML
    private void handleSettings() {
        loadScreen("/views/Settings.fxml");
    }
    
    @FXML
    private void handleExit() {
        if (inactivityTimer != null) {
            inactivityTimer.cancel();
        }
        authService.logout();
        Platform.exit();
    }
    
    private void loadScreen(String fxmlPath) {
        startInactivityTimer(); // Reset timer on activity
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = rootPane.getScene();
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();
            Scene scene = rootPane.getScene();
            if (scene == null) {
                scene = new Scene(root, 800, 600);
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(scene);
            } else {
                scene.setRoot(root);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

