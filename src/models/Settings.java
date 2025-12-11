package models;

/**
 * Settings model for storing application preferences.
 * Currently stores theme preference (dark/light mode).
 */
public class Settings {
    private boolean darkMode;
    
    /**
     * Default constructor with dark mode as default.
     */
    public Settings() {
        this.darkMode = true;
    }
    
    /**
     * Constructor with dark mode parameter.
     * 
     * @param darkMode Whether dark mode is enabled
     */
    public Settings(boolean darkMode) {
        this.darkMode = darkMode;
    }
    
    public boolean isDarkMode() {
        return darkMode;
    }
    
    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
    
    /**
     * Converts settings to string format for file storage.
     * Format: darkMode
     * 
     * @return String representation of settings
     */
    public String toFileString() {
        return String.valueOf(darkMode);
    }
    
    /**
     * Creates a Settings object from a file string.
     * 
     * @param fileString The string from the file
     * @return Settings object with default values if parsing fails
     */
    public static Settings fromFileString(String fileString) {
        try {
            return new Settings(Boolean.parseBoolean(fileString.trim()));
        } catch (Exception e) {
            System.err.println("Error parsing settings: " + e.getMessage());
            return new Settings(); // Return default settings
        }
    }
}

