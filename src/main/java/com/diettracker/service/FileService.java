package com.diettracker.service;

import com.diettracker.model.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling file I/O operations.
 * Manages reading and writing data to text files for persistence.
 */
public class FileService {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + "/users.txt";
    private static final String MEALS_FILE = DATA_DIR + "/meals.txt";
    private static final String DAILY_LOGS_FILE = DATA_DIR + "/daily_logs.txt";
    
    /**
     * Ensures the data directory exists.
     */
    private static void ensureDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    /**
     * Saves a list of users to file.
     * @param users List of users to save
     */
    public static void saveUsers(List<User> users) {
        ensureDataDirectory();
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.println(user.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }
    
    /**
     * Loads users from file.
     * @return List of loaded users
     */
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        
        if (!file.exists()) {
            return users;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String username = parts[0];
                        String password = parts[1];
                        int calorieGoal = Integer.parseInt(parts[2]);
                        users.add(new User(username, password, calorieGoal));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        
        return users;
    }
    
    /**
     * Saves a list of meals to file.
     * @param meals List of meals to save
     */
    public static void saveMeals(List<Meal> meals) {
        ensureDataDirectory();
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEALS_FILE))) {
            for (Meal meal : meals) {
                writer.println(meal.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving meals: " + e.getMessage());
        }
    }
    
    /**
     * Loads meals from file.
     * @return List of loaded meals
     */
    public static List<Meal> loadMeals() {
        List<Meal> meals = new ArrayList<>();
        File file = new File(MEALS_FILE);
        
        if (!file.exists()) {
            return meals;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String username = parts[0];
                        Meal.MealType mealType = Meal.MealType.valueOf(parts[1]);
                        LocalDateTime timestamp = LocalDateTime.parse(parts[2], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        
                        Meal meal = new Meal(username, mealType, timestamp);
                        
                        // Parse food items (starting from index 3)
                        for (int i = 3; i < parts.length; i += 4) {
                            if (i + 3 < parts.length) {
                                String foodName = parts[i];
                                int caloriesPerUnit = Integer.parseInt(parts[i + 1]);
                                double quantity = Double.parseDouble(parts[i + 2]);
                                String unit = parts[i + 3];
                                
                                FoodItem foodItem = new FoodItem(foodName, caloriesPerUnit, quantity, unit);
                                meal.addFoodItem(foodItem);
                            }
                        }
                        
                        meals.add(meal);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading meals: " + e.getMessage());
        }
        
        return meals;
    }
    
    /**
     * Saves a list of daily logs to file.
     * @param dailyLogs List of daily logs to save
     */
    public static void saveDailyLogs(List<DailyLog> dailyLogs) {
        ensureDataDirectory();
        try (PrintWriter writer = new PrintWriter(new FileWriter(DAILY_LOGS_FILE))) {
            for (DailyLog log : dailyLogs) {
                writer.println(log.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving daily logs: " + e.getMessage());
        }
    }
    
    /**
     * Loads daily logs from file.
     * @return List of loaded daily logs
     */
    public static List<DailyLog> loadDailyLogs() {
        List<DailyLog> dailyLogs = new ArrayList<>();
        File file = new File(DAILY_LOGS_FILE);
        
        if (!file.exists()) {
            return dailyLogs;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String username = parts[0];
                        LocalDate date = LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        int totalCalories = Integer.parseInt(parts[2]);
                        int dailyGoal = Integer.parseInt(parts[3]);
                        
                        DailyLog log = new DailyLog(username, date, dailyGoal);
                        dailyLogs.add(log);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading daily logs: " + e.getMessage());
        }
        
        return dailyLogs;
    }
    
    /**
     * Clears all data files.
     */
    public static void clearAllData() {
        File usersFile = new File(USERS_FILE);
        File mealsFile = new File(MEALS_FILE);
        File dailyLogsFile = new File(DAILY_LOGS_FILE);
        
        if (usersFile.exists()) usersFile.delete();
        if (mealsFile.exists()) mealsFile.delete();
        if (dailyLogsFile.exists()) dailyLogsFile.delete();
    }
}
