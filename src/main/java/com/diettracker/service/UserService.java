package com.diettracker.service;

import com.diettracker.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing user-related operations.
 * Handles user registration, authentication, and profile management.
 */
public class UserService {
    private List<User> users;
    
    public UserService() {
        this.users = FileService.loadUsers();
    }
    
    /**
     * Registers a new user.
     * @param username The username
     * @param password The password
     * @return true if registration successful, false if username already exists
     */
    public boolean registerUser(String username, String password) {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return false;
        }
        
        // Check if username already exists
        if (getUserByUsername(username) != null) {
            return false;
        }
        
        User newUser = new User(username.trim(), password.trim());
        users.add(newUser);
        saveUsers();
        return true;
    }
    
    /**
     * Authenticates a user.
     * @param username The username
     * @param password The password
     * @return The authenticated user, or null if authentication fails
     */
    public User authenticateUser(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        
        User user = getUserByUsername(username.trim());
        if (user != null && user.getPassword().equals(password.trim())) {
            return user;
        }
        
        return null;
    }
    
    /**
     * Gets a user by username.
     * @param username The username to search for
     * @return The user if found, null otherwise
     */
    public User getUserByUsername(String username) {
        if (username == null) {
            return null;
        }
        
        for (User user : users) {
            if (user.getUsername().equals(username.trim())) {
                return user;
            }
        }
        
        return null;
    }
    
    /**
     * Updates a user's daily calorie goal.
     * @param username The username
     * @param newGoal The new calorie goal
     * @return true if update successful, false otherwise
     */
    public boolean updateCalorieGoal(String username, int newGoal) {
        if (newGoal <= 0) {
            return false;
        }
        
        User user = getUserByUsername(username);
        if (user != null) {
            user.setDailyCalorieGoal(newGoal);
            saveUsers();
            return true;
        }
        
        return false;
    }
    
    /**
     * Gets all users.
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    /**
     * Deletes a user.
     * @param username The username to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteUser(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            users.remove(user);
            saveUsers();
            return true;
        }
        
        return false;
    }
    
    /**
     * Changes a user's password.
     * @param username The username
     * @param oldPassword The current password
     * @param newPassword The new password
     * @return true if password change successful, false otherwise
     */
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }
        
        User user = authenticateUser(username, oldPassword);
        if (user != null) {
            user.setPassword(newPassword.trim());
            saveUsers();
            return true;
        }
        
        return false;
    }
    
    /**
     * Validates username format.
     * @param username The username to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        // Username should be 3-20 characters, alphanumeric and underscore only
        String trimmed = username.trim();
        return trimmed.length() >= 3 && trimmed.length() <= 20 && 
               trimmed.matches("^[a-zA-Z0-9_]+$");
    }
    
    /**
     * Validates password strength.
     * @param password The password to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        
        // Password should be at least 6 characters
        return password.trim().length() >= 6;
    }
    
    /**
     * Saves users to file.
     */
    private void saveUsers() {
        FileService.saveUsers(users);
    }
}
