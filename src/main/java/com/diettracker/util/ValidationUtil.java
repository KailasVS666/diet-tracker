package com.diettracker.util;

import java.util.Scanner;

/**
 * Utility class for input validation and common validation methods.
 * Provides methods to validate user input and ensure data integrity.
 */
public class ValidationUtil {
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Validates if a string is not null or empty.
     * @param input The string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidString(String input) {
        return input != null && !input.trim().isEmpty();
    }
    
    /**
     * Validates if a string represents a positive integer.
     * @param input The string to validate
     * @return true if valid positive integer, false otherwise
     */
    public static boolean isValidPositiveInteger(String input) {
        if (!isValidString(input)) {
            return false;
        }
        
        try {
            int value = Integer.parseInt(input.trim());
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates if a string represents a positive double.
     * @param input The string to validate
     * @return true if valid positive double, false otherwise
     */
    public static boolean isValidPositiveDouble(String input) {
        if (!isValidString(input)) {
            return false;
        }
        
        try {
            double value = Double.parseDouble(input.trim());
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validates if a string represents a valid integer within a range.
     * @param input The string to validate
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return true if valid integer in range, false otherwise
     */
    public static boolean isValidIntegerInRange(String input, int min, int max) {
        if (!isValidString(input)) {
            return false;
        }
        
        try {
            int value = Integer.parseInt(input.trim());
            return value >= min && value <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Gets a valid string input from the user.
     * @param prompt The prompt to display
     * @return Valid string input
     */
    public static String getValidStringInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!isValidString(input)) {
                System.out.println("Error: Input cannot be empty. Please try again.");
            }
        } while (!isValidString(input));
        
        return input;
    }
    
    /**
     * Gets a valid positive integer input from the user.
     * @param prompt The prompt to display
     * @return Valid positive integer
     */
    public static int getValidPositiveIntegerInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!isValidPositiveInteger(input)) {
                System.out.println("Error: Please enter a valid positive number.");
            }
        } while (!isValidPositiveInteger(input));
        
        return Integer.parseInt(input);
    }
    
    /**
     * Gets a valid positive double input from the user.
     * @param prompt The prompt to display
     * @return Valid positive double
     */
    public static double getValidPositiveDoubleInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!isValidPositiveDouble(input)) {
                System.out.println("Error: Please enter a valid positive number.");
            }
        } while (!isValidPositiveDouble(input));
        
        return Double.parseDouble(input);
    }
    
    /**
     * Gets a valid integer input within a specified range from the user.
     * @param prompt The prompt to display
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Valid integer in range
     */
    public static int getValidIntegerInRangeInput(String prompt, int min, int max) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!isValidIntegerInRange(input, min, max)) {
                System.out.println("Error: Please enter a number between " + min + " and " + max + ".");
            }
        } while (!isValidIntegerInRange(input, min, max));
        
        return Integer.parseInt(input);
    }
    
    /**
     * Gets a yes/no confirmation from the user.
     * @param prompt The prompt to display
     * @return true for yes, false for no
     */
    public static boolean getYesNoInput(String prompt) {
        String input;
        do {
            System.out.print(prompt + " (y/n): ");
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Error: Please enter 'y' for yes or 'n' for no.");
            }
        } while (true);
    }
    
    /**
     * Validates email format (basic validation).
     * @param email The email to validate
     * @return true if valid email format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (!isValidString(email)) {
            return false;
        }
        
        // Basic email validation
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.trim().matches(emailRegex);
    }
    
    /**
     * Validates username format.
     * @param username The username to validate
     * @return true if valid username format, false otherwise
     */
    public static boolean isValidUsername(String username) {
        if (!isValidString(username)) {
            return false;
        }
        
        String trimmed = username.trim();
        // Username should be 3-20 characters, alphanumeric and underscore only
        return trimmed.length() >= 3 && trimmed.length() <= 20 && 
               trimmed.matches("^[a-zA-Z0-9_]+$");
    }
    
    /**
     * Validates password strength.
     * @param password The password to validate
     * @return true if valid password, false otherwise
     */
    public static boolean isValidPassword(String password) {
        if (!isValidString(password)) {
            return false;
        }
        
        // Password should be at least 6 characters
        return password.trim().length() >= 6;
    }
    
    /**
     * Closes the scanner.
     */
    public static void closeScanner() {
        scanner.close();
    }
}
