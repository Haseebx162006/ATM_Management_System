package controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the splash screen.
 * Shows a welcome screen and transitions to the login screen after a delay.
 */
public class SplashController {
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    public void initialize() {
        // Fade in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), rootPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        
        // Transition to login after delay
        fadeIn.setOnFinished(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), rootPane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> loadLogin());
            fadeOut.play();
        });
    }
    
    private void loadLogin() {
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

