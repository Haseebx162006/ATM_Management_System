package services;

import models.Settings;
import utils.FileHandler;

/**
 * Service class for application settings operations.
 * Handles loading and saving of user preferences.
 */
public class SettingsService {
    private Settings settings;
    
    /**
     * Constructor that loads settings from file.
     */
    public SettingsService() {
        loadSettings();
    }
    
    /**
     * Loads settings from file storage.
     */
    private void loadSettings() {
        settings = FileHandler.readSettings();
    }
    
    /**
     * Gets the current settings.
     * 
     * @return Settings object
     */
    public Settings getSettings() {
        return settings;
    }
    
    /**
     * Updates the dark mode setting.
     * 
     * @param darkMode Whether dark mode should be enabled
     */
    public void setDarkMode(boolean darkMode) {
        settings.setDarkMode(darkMode);
        FileHandler.writeSettings(settings);
    }
    
    /**
     * Checks if dark mode is enabled.
     * 
     * @return true if dark mode is enabled, false otherwise
     */
    public boolean isDarkMode() {
        return settings.isDarkMode();
    }
}

