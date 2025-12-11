package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for security operations including PIN hashing.
 * Uses SHA-256 hashing algorithm for PIN security.
 */
public class SecurityUtils {
    private static final String ALGORITHM = "SHA-256";
    
    /**
     * Hashes a PIN using SHA-256 algorithm.
     * 
     * @param pin The PIN to hash
     * @return Hashed PIN as a hexadecimal string
     */
    public static String hashPin(String pin) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = digest.digest(pin.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error hashing PIN: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Verifies a PIN against a stored hash.
     * 
     * @param pin The PIN to verify
     * @param hashedPin The stored hash to compare against
     * @return true if PIN matches, false otherwise
     */
    public static boolean verifyPin(String pin, String hashedPin) {
        if (pin == null || hashedPin == null) {
            return false;
        }
        String hashedInput = hashPin(pin);
        return hashedInput != null && hashedInput.equals(hashedPin);
    }
    
    /**
     * Generates a random transaction ID.
     * 
     * @return A random transaction ID string
     */
    public static String generateTransactionId() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    
    /**
     * Generates a random account number.
     * 
     * @return A random 10-digit account number
     */
    public static String generateAccountNumber() {
        SecureRandom random = new SecureRandom();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
}

