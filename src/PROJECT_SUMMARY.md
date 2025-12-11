# ATM Management System - Project Summary

## ✅ Completed Features

### Core Features
- ✅ Create Account with secure PIN hashing
- ✅ Login with account number + PIN
- ✅ Change PIN functionality
- ✅ Forgot PIN / Reset PIN
- ✅ Deposit money
- ✅ Withdraw money
- ✅ Transfer money between accounts
- ✅ Check balance
- ✅ Mini statement (last 10 transactions)
- ✅ Full transaction history

### Advanced Features
- ✅ PIN hashing using SHA-256 algorithm
- ✅ Auto logout after 5 minutes of inactivity
- ✅ Dark/Light mode with persistence
- ✅ Modern JavaFX UI with smooth transitions
- ✅ File-based storage (no database)

### UI Screens
- ✅ Splash Screen
- ✅ Login Screen
- ✅ Create Account Screen
- ✅ Forgot PIN Screen
- ✅ Dashboard
- ✅ Deposit Screen
- ✅ Withdraw Screen
- ✅ Transfer Screen
- ✅ Mini Statement Screen
- ✅ Transaction History Screen
- ✅ Settings Screen

## Project Structure

```
src/
├── com.atm.management.Main.java                    # Application entry point
├── module-info.java             # Java module configuration
├── application.css              # Light theme styles
├── dark-theme.css              # Dark theme styles
│
├── controllers/                 # MVC Controllers (11 files)
│   ├── SplashController.java
│   ├── LoginController.java
│   ├── CreateAccountController.java
│   ├── ForgotPinController.java
│   ├── DashboardController.java
│   ├── DepositController.java
│   ├── WithdrawController.java
│   ├── TransferController.java
│   ├── MiniStatementController.java
│   ├── TransactionHistoryController.java
│   └── SettingsController.java
│
├── models/                      # Data Models (3 files)
│   ├── Account.java
│   ├── Transaction.java
│   └── Settings.java
│
├── services/                    # Business Logic (4 files)
│   ├── AccountService.java
│   ├── AuthService.java
│   ├── TransactionService.java
│   └── SettingsService.java
│
├── utils/                       # Utilities (2 files)
│   ├── FileHandler.java
│   └── SecurityUtils.java
│
├── views/                       # FXML Views (11 files)
│   ├── Splash.fxml
│   ├── Login.fxml
│   ├── CreateAccount.fxml
│   ├── ForgotPin.fxml
│   ├── Dashboard.fxml
│   ├── Deposit.fxml
│   ├── Withdraw.fxml
│   ├── Transfer.fxml
│   ├── MiniStatement.fxml
│   ├── TransactionHistory.fxml
│   └── Settings.fxml
│
├── resources/                   # Resource folder for IDE
│   ├── views/                   # FXML files (copy of views/)
│   ├── application.css
│   └── dark-theme.css
│
└── storage/                     # Data Storage (3 files)
    ├── accounts.txt
    ├── transactions.txt
    └── settings.txt
```

## Architecture

### MVC Pattern
- **Models**: Data structures (Account, Transaction, Settings)
- **Views**: FXML files defining UI layout
- **Controllers**: Handle user interactions and coordinate with services

### Service Layer
- **AccountService**: Manages account operations (create, update, balance)
- **AuthService**: Handles authentication and session management
- **TransactionService**: Manages transaction records
- **SettingsService**: Manages application preferences

### Utility Layer
- **FileHandler**: Handles all file I/O operations
- **SecurityUtils**: Provides PIN hashing and ID generation

## Data Storage Format

### Accounts (accounts.txt)
```
accountNumber|name|hashedPin|balance|creationDate
```

### Transactions (transactions.txt)
```
transactionId|accountNumber|targetAccountNumber|type|amount|timestamp|description
```

### Settings (settings.txt)
```
darkMode (true/false)
```

## Security Features

1. **PIN Hashing**: SHA-256 algorithm
2. **Secure IDs**: Cryptographically secure random generation
3. **Session Management**: Auto logout on inactivity
4. **Input Validation**: All user inputs validated

## Running the Application

### VM Arguments Required:
```
--module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
```

### Example:
```
--module-path "C:\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
```

## Code Quality

- ✅ Clean, modular code following MVC architecture
- ✅ JavaDoc comments on all classes and methods
- ✅ No unused imports (cleaned up)
- ✅ Proper error handling
- ✅ Consistent naming conventions
- ✅ Well-organized package structure

## Notes

- The application uses file-based storage (no database required)
- All PINs are hashed before storage
- Account numbers are auto-generated (10 digits)
- Transactions are automatically recorded with timestamps
- Dark mode preference persists across sessions
- Auto logout timer resets on any user activity

## Future Enhancements (Optional)

- Add account locking after failed login attempts
- Implement transaction limits
- Add email notifications
- Export transaction history to PDF
- Add biometric authentication
- Multi-language support

