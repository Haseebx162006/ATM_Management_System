# Setup Guide for ATM Management System

## Quick Start

### 1. Download JavaFX SDK
- Visit https://openjfx.io/
- Download JavaFX SDK for your platform (Windows/Mac/Linux)
- Extract to a folder (e.g., `C:\javafx-sdk`)

### 2. Configure Your IDE

#### IntelliJ IDEA:
1. **File → Project Structure → Modules**
   - Ensure module name is `atm.management`
   - Add JavaFX SDK to module dependencies

2. **Mark Resources Folder:**
   - Right-click `resources/` folder
   - Select "Mark Directory as → Resources Root"

3. **Run Configuration:**
   - Edit Configurations → VM options:
   ```
   --module-path "C:\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
   ```
   (Replace path with your JavaFX SDK location)

#### Eclipse:
1. **Right-click project → Properties → Java Build Path → Libraries**
   - Add External JARs from `javafx-sdk/lib/`

2. **Run → Run Configurations → Arguments**
   - VM arguments:
   ```
   --module-path "C:\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
   ```

3. **Ensure resources folder is in classpath**

#### VS Code:
1. Install "Extension Pack for Java"
2. Create `.vscode/launch.json`:
```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Launch ATM System",
            "request": "launch",
            "mainClass": "com.atm.management.Main",
            "vmArgs": "--module-path \"C:\\javafx-sdk\\lib\" --add-modules javafx.controls,javafx.fxml"
        }
    ]
}
```

### 3. Project Structure
Ensure your project structure looks like this:
```
src/
├── com.atm.management.Main.java
├── module-info.java
├── controllers/
├── models/
├── services/
├── utils/
├── views/              # FXML files (also copied to resources/views/)
├── resources/          # Resource folder (for IDE)
│   ├── views/         # FXML files
│   ├── application.css
│   └── dark-theme.css
├── application.css     # CSS files (also in resources/)
├── dark-theme.css
└── storage/            # Data files (created at runtime)
```

### 4. Run the Application
- Run `com.atm.management.Main.java` with the VM arguments specified above
- The application will start with a splash screen
- Create an account or login to begin

## Troubleshooting

**Error: "javafx.controls cannot be resolved"**
- Solution: Add JavaFX SDK to module path and include in VM arguments

**Error: "Resource not found: /views/..."**
- Solution: Ensure `resources/views/` folder is marked as Resources Root in IDE
- Or ensure `views/` folder is in the classpath

**Error: "Module not found"**
- Solution: Verify `module-info.java` is correct and JDK 25+ is being used

**Application runs but UI doesn't appear**
- Solution: Check VM arguments are correctly set
- Verify JavaFX SDK version matches your JDK version

## Testing

1. **Create Account:**
   - Click "Create New Account"
   - Enter name and PIN (min 4 digits)
   - Note your account number

2. **Login:**
   - Enter account number and PIN
   - You should see the dashboard

3. **Test Features:**
   - Deposit money
   - Withdraw money
   - Transfer to another account
   - View transaction history
   - Change PIN
   - Toggle dark mode

## Notes

- All data is stored in `storage/` folder as text files
- PINs are hashed using SHA-256
- Auto logout occurs after 5 minutes of inactivity
- Dark mode preference is saved in `storage/settings.txt`

