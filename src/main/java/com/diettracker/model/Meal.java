package com.diettracker.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a meal with its type, food items, and timestamp.
 * Supports different meal types: breakfast, lunch, dinner, and snacks.
 */
public class Meal implements Serializable {
    public enum MealType {
        BREAKFAST("Breakfast"),
        LUNCH("Lunch"),
        DINNER("Dinner"),
        SNACK("Snack");
        
        private final String displayName;
        
        MealType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    private String username;
    private MealType mealType;
    private List<FoodItem> foodItems;
    private LocalDateTime timestamp;
    
    public Meal(String username, MealType mealType) {
        this.username = username;
        this.mealType = mealType;
        this.foodItems = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }
    
    public Meal(String username, MealType mealType, LocalDateTime timestamp) {
        this.username = username;
        this.mealType = mealType;
        this.foodItems = new ArrayList<>();
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public MealType getMealType() {
        return mealType;
    }
    
    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
    
    public List<FoodItem> getFoodItems() {
        return new ArrayList<>(foodItems);
    }
    
    public void setFoodItems(List<FoodItem> foodItems) {
        this.foodItems = new ArrayList<>(foodItems);
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * Adds a food item to this meal.
     * @param foodItem The food item to add
     */
    public void addFoodItem(FoodItem foodItem) {
        foodItems.add(foodItem);
    }
    
    /**
     * Removes a food item from this meal.
     * @param foodItem The food item to remove
     * @return true if the item was removed, false otherwise
     */
    public boolean removeFoodItem(FoodItem foodItem) {
        return foodItems.remove(foodItem);
    }
    
    /**
     * Calculates the total calories for this meal.
     * @return Total calories from all food items
     */
    public int getTotalCalories() {
        return foodItems.stream()
                .mapToInt(FoodItem::getTotalCalories)
                .sum();
    }
    
    /**
     * Gets the formatted date string for this meal.
     * @return Formatted date string
     */
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return timestamp.format(formatter);
    }
    
    /**
     * Gets the formatted time string for this meal.
     * @return Formatted time string
     */
    public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return timestamp.format(formatter);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(",")
          .append(mealType.name()).append(",")
          .append(timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        for (FoodItem item : foodItems) {
            sb.append(",").append(item.toString());
        }
        
        return sb.toString();
    }
}
