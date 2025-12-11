package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.Transaction;
import services.AuthService;
import services.TransactionService;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for the mini statement screen.
 * Displays the last 10 transactions for the logged-in account.
 */
public class MiniStatementController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    @FXML
    private TableColumn<Transaction, String> amountColumn;
    @FXML
    private TableColumn<Transaction, String> descriptionColumn;
    @FXML
    private Button backButton;
    
    private AuthService authService;
    private TransactionService transactionService;
    
    @FXML
    public void initialize() {
        authService = AuthService.getInstance();
        transactionService = new TransactionService();
        
        if (!authService.isLoggedIn()) {
            loadDashboard();
            return;
        }
        
        setupTable();
        loadTransactions();
    }
    
    private void setupTable() {
        dateColumn.setCellValueFactory(cellData -> {
            Transaction t = cellData.getValue();
            if (t != null && t.getTimestamp() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                    t.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        
        typeColumn.setCellValueFactory(cellData -> {
            Transaction t = cellData.getValue();
            if (t != null && t.getType() != null) {
                return new javafx.beans.property.SimpleStringProperty(t.getType().name());
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        
        amountColumn.setCellValueFactory(cellData -> {
            Transaction t = cellData.getValue();
            if (t != null) {
                String sign = t.getType() == Transaction.TransactionType.DEPOSIT ? "+" : "-";
                return new javafx.beans.property.SimpleStringProperty(
                    sign + "$" + String.format("%.2f", t.getAmount()));
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
        
        descriptionColumn.setCellValueFactory(cellData -> {
            Transaction t = cellData.getValue();
            if (t != null) {
                String desc = t.getDescription() != null ? t.getDescription() : "";
                if (t.getTargetAccountNumber() != null && !t.getTargetAccountNumber().isEmpty()) {
                    desc += " (To: " + t.getTargetAccountNumber() + ")";
                }
                return new javafx.beans.property.SimpleStringProperty(desc);
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });
    }
    
    private void loadTransactions() {
        if (authService.getCurrentAccount() != null) {
            String accountNumber = authService.getCurrentAccount().getAccountNumber();
            List<Transaction> transactions = transactionService.getMiniStatement(accountNumber, 10);
            transactionTable.getItems().clear();
            if (transactions != null && !transactions.isEmpty()) {
                transactionTable.getItems().setAll(transactions);
            }
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

