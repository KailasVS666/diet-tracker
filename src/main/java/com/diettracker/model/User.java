package com.diettracker.model;

import java.io.Serializable;

/**
 * Represents a user in the diet tracking system.
 * Contains user information including username, password, and daily calorie goal.
 */
public class User implements Serializable {
    private String username;
    private String password;
    private int dailyCalorieGoal;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.dailyCalorieGoal = 2000; // Default calorie goal
    }
    
    public User(String username, String password, int dailyCalorieGoal) {
        this.username = username;
        this.password = password;
        this.dailyCalorieGoal = dailyCalorieGoal;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }
    
    public void setDailyCalorieGoal(int dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }
    
    @Override
    public String toString() {
        return username + "," + password + "," + dailyCalorieGoal;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return username.equals(user.username);
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
