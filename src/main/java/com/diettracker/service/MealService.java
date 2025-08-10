package com.diettracker.service;

import com.diettracker.model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing meal-related operations.
 * Handles meal logging, daily summaries, and food item management.
 */
public class MealService {
    private List<Meal> meals;
    private UserService userService;
    
    public MealService(UserService userService) {
        this.userService = userService;
        this.meals = FileService.loadMeals();
    }
    
    /**
     * Adds a new meal for a user.
     * @param username The username
     * @param mealType The type of meal
     * @param foodItems List of food items in the meal
     * @return true if meal added successfully, false otherwise
     */
    public boolean addMeal(String username, Meal.MealType mealType, List<FoodItem> foodItems) {
        if (username == null || mealType == null || foodItems == null || foodItems.isEmpty()) {
            return false;
        }
        
        // Verify user exists
        if (userService.getUserByUsername(username) == null) {
            return false;
        }
        
        Meal meal = new Meal(username, mealType);
        for (FoodItem item : foodItems) {
            meal.addFoodItem(item);
        }
        
        meals.add(meal);
        saveMeals();
        return true;
    }
    
    /**
     * Gets all meals for a specific user.
     * @param username The username
     * @return List of meals for the user
     */
    public List<Meal> getMealsByUser(String username) {
        if (username == null) {
            return new ArrayList<>();
        }
        
        return meals.stream()
                .filter(meal -> meal.getUsername().equals(username))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets meals for a user on a specific date.
     * @param username The username
     * @param date The date
     * @return List of meals for the user on the specified date
     */
    public List<Meal> getMealsByUserAndDate(String username, LocalDate date) {
        if (username == null || date == null) {
            return new ArrayList<>();
        }
        
        return meals.stream()
                .filter(meal -> meal.getUsername().equals(username) && 
                               meal.getTimestamp().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }
    
    /**
     * Gets meals for a user by meal type.
     * @param username The username
     * @param mealType The meal type
     * @return List of meals of the specified type for the user
     */
    public List<Meal> getMealsByUserAndType(String username, Meal.MealType mealType) {
        if (username == null || mealType == null) {
            return new ArrayList<>();
        }
        
        return meals.stream()
                .filter(meal -> meal.getUsername().equals(username) && 
                               meal.getMealType() == mealType)
                .collect(Collectors.toList());
    }
    
    /**
     * Calculates total calories consumed by a user on a specific date.
     * @param username The username
     * @param date The date
     * @return Total calories consumed
     */
    public int getTotalCaloriesForDate(String username, LocalDate date) {
        List<Meal> dailyMeals = getMealsByUserAndDate(username, date);
        return dailyMeals.stream()
                .mapToInt(Meal::getTotalCalories)
                .sum();
    }
    
    /**
     * Creates a daily log for a user on a specific date.
     * @param username The username
     * @param date The date
     * @return DailyLog object with meal summary
     */
    public DailyLog createDailyLog(String username, LocalDate date) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return null;
        }
        
        List<Meal> dailyMeals = getMealsByUserAndDate(username, date);
        DailyLog dailyLog = new DailyLog(username, date, user.getDailyCalorieGoal());
        
        for (Meal meal : dailyMeals) {
            dailyLog.addMeal(meal);
        }
        
        return dailyLog;
    }
    
    /**
     * Removes a meal.
     * @param username The username
     * @param mealType The meal type
     * @param timestamp The meal timestamp
     * @return true if meal removed successfully, false otherwise
     */
    public boolean removeMeal(String username, Meal.MealType mealType, LocalDateTime timestamp) {
        Meal mealToRemove = null;
        
        for (Meal meal : meals) {
            if (meal.getUsername().equals(username) && 
                meal.getMealType() == mealType && 
                meal.getTimestamp().equals(timestamp)) {
                mealToRemove = meal;
                break;
            }
        }
        
        if (mealToRemove != null) {
            meals.remove(mealToRemove);
            saveMeals();
            return true;
        }
        
        return false;
    }
    
    /**
     * Gets the most recent meal for a user.
     * @param username The username
     * @return The most recent meal, or null if no meals exist
     */
    public Meal getMostRecentMeal(String username) {
        if (username == null) {
            return null;
        }
        
        return meals.stream()
                .filter(meal -> meal.getUsername().equals(username))
                .max((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()))
                .orElse(null);
    }
    
    /**
     * Gets meal statistics for a user.
     * @param username The username
     * @param days Number of days to look back
     * @return Array with [total meals, total calories, average calories per day]
     */
    public double[] getMealStatistics(String username, int days) {
        if (username == null || days <= 0) {
            return new double[]{0, 0, 0};
        }
        
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);
        
        List<Meal> recentMeals = meals.stream()
                .filter(meal -> meal.getUsername().equals(username) && 
                               !meal.getTimestamp().toLocalDate().isBefore(startDate) &&
                               !meal.getTimestamp().toLocalDate().isAfter(endDate))
                .collect(Collectors.toList());
        
        int totalMeals = recentMeals.size();
        int totalCalories = recentMeals.stream()
                .mapToInt(Meal::getTotalCalories)
                .sum();
        double avgCaloriesPerDay = days > 0 ? (double) totalCalories / days : 0;
        
        return new double[]{totalMeals, totalCalories, avgCaloriesPerDay};
    }
    
    /**
     * Validates food item data.
     * @param foodItem The food item to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidFoodItem(FoodItem foodItem) {
        if (foodItem == null) {
            return false;
        }
        
        return foodItem.getName() != null && !foodItem.getName().trim().isEmpty() &&
               foodItem.getCaloriesPerUnit() > 0 &&
               foodItem.getQuantity() > 0;
    }
    
    /**
     * Saves meals to file.
     */
    private void saveMeals() {
        FileService.saveMeals(meals);
    }
}
