package com.atm.management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.SettingsService;

/**
 * Main application class for the ATM Management System.
 * Initializes JavaFX application and loads the splash screen.
 */
public class Main extends Application {
    private static SettingsService settingsService;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            settingsService = new SettingsService();
            
            // Load splash screen
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/Splash.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root, 800, 600);
            
            // Apply CSS - Dark theme is now the default
            String css = Main.class.getResource("/application.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            primaryStage.setTitle("ATM Management System");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static SettingsService getSettingsService() {
        if (settingsService == null) {
            settingsService = new SettingsService();
        }
        return settingsService;
    }
}
