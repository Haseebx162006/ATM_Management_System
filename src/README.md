# ATM Management System

A complete ATM Management System built with Java (JDK 25) and JavaFX.

## Features

- **Account Management**: Create new accounts with secure PIN hashing
- **Authentication**: Secure login with account number and PIN
- **Transactions**: Deposit, withdraw, and transfer money between accounts
- **Transaction History**: View mini statement (last 10 transactions) and full transaction history
- **PIN Management**: Change PIN and reset forgotten PIN
- **Modern UI**: Beautiful JavaFX interface with dark/light mode support
- **Auto Logout**: Automatic logout after 5 minutes of inactivity
- **File Storage**: All data stored in text files (no database required)

## Project Structure

```
src/
├── com.atm.management.Main.java
├── module-info.java
├── controllers/          # MVC Controllers
├── models/              # Data models (Account, Transaction, Settings)
├── services/            # Business logic services
├── utils/               # Utility classes (FileHandler, SecurityUtils)
├── views/               # FXML view files
├── application.css      # Light theme styles
├── dark-theme.css       # Dark theme styles
└── storage/             # Data storage files
    ├── accounts.txt
    ├── transactions.txt
    └── settings.txt
```

## Setup Instructions

### Prerequisites

- JDK 25 or later
- JavaFX SDK (download from https://openjfx.io/)

### Configuration

1. **Download JavaFX SDK** and extract it to a location (e.g., `C:\javafx-sdk`)

2. **VM Arguments** - Add these VM arguments when running the application:
   ```
   --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
   ```
   
   Example:
   ```
   --module-path "C:\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
   ```

3. **Module Path Setup** - Ensure your IDE is configured to:
   - Recognize `module-info.java`
   - Include JavaFX modules in the module path
   - Place `views/` folder and CSS files in the classpath/resources

### Running the Application

1. Compile all Java files
2. Ensure FXML files in `views/` are accessible as resources
3. Run `com.atm.management.Main.java` with the VM arguments specified above

### IDE-Specific Setup

#### IntelliJ IDEA
1. File → Project Structure → Modules → Add JavaFX
2. Set VM options: `--module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml`
3. Mark `views/` folder as Resources Root (Right-click → Mark Directory as → Resources Root)

#### Eclipse
1. Right-click project → Properties → Java Build Path → Libraries → Add External JARs (JavaFX libs)
2. Run → Run Configurations → Arguments → VM arguments: `--module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml`
3. Ensure `views/` is in the classpath

#### VS Code
1. Install Java Extension Pack
2. Configure `launch.json` with VM arguments
3. Ensure resources are properly configured

## Usage

1. **Create Account**: Click "Create New Account" on the login screen
2. **Login**: Enter your account number and PIN
3. **Dashboard**: View balance and access all features
4. **Transactions**: Deposit, withdraw, or transfer money
5. **History**: View transaction history or mini statement
6. **Settings**: Change PIN or toggle dark/light mode

## Security Features

- PIN hashing using SHA-256 algorithm
- Secure account number generation
- Transaction ID generation
- Auto logout after inactivity

## Data Storage

All data is stored in text files in the `storage/` directory:
- `accounts.txt`: Account information
- `transactions.txt`: Transaction records
- `settings.txt`: Application settings (theme preference)

## Notes

- The application uses file-based storage (no database)
- All PINs are hashed before storage
- Account numbers are auto-generated (10 digits)
- Transactions are automatically recorded
- Dark mode preference is saved and persists across sessions

## Troubleshooting

**JavaFX not found errors:**
- Ensure JavaFX SDK is downloaded and path is correct in VM arguments
- Verify module-info.java includes javafx.controls and javafx.fxml

**Resource not found errors:**
- Ensure `views/` folder is marked as Resources Root in your IDE
- Verify FXML files are in the correct location

**Module errors:**
- Ensure JDK 25+ is being used
- Verify module-info.java is properly configured
- Check that all required modules are included

