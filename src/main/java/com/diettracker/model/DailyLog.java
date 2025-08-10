package com.diettracker.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a daily summary of a user's food intake.
 * Contains the date, total calories consumed, and goal comparison.
 */
public class DailyLog implements Serializable {
    private String username;
    private LocalDate date;
    private List<Meal> meals;
    private int dailyCalorieGoal;
    
    public DailyLog(String username, LocalDate date, int dailyCalorieGoal) {
        this.username = username;
        this.date = date;
        this.meals = new ArrayList<>();
        this.dailyCalorieGoal = dailyCalorieGoal;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public List<Meal> getMeals() {
        return new ArrayList<>(meals);
    }
    
    public void setMeals(List<Meal> meals) {
        this.meals = new ArrayList<>(meals);
    }
    
    public int getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }
    
    public void setDailyCalorieGoal(int dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }
    
    /**
     * Adds a meal to this daily log.
     * @param meal The meal to add
     */
    public void addMeal(Meal meal) {
        meals.add(meal);
    }
    
    /**
     * Removes a meal from this daily log.
     * @param meal The meal to remove
     * @return true if the meal was removed, false otherwise
     */
    public boolean removeMeal(Meal meal) {
        return meals.remove(meal);
    }
    
    /**
     * Calculates the total calories consumed for this day.
     * @return Total calories from all meals
     */
    public int getTotalCaloriesConsumed() {
        return meals.stream()
                .mapToInt(Meal::getTotalCalories)
                .sum();
    }
    
    /**
     * Calculates the remaining calories for this day.
     * @return Remaining calories (goal - consumed)
     */
    public int getRemainingCalories() {
        return dailyCalorieGoal - getTotalCaloriesConsumed();
    }
    
    /**
     * Checks if the user has exceeded their daily calorie goal.
     * @return true if exceeded, false otherwise
     */
    public boolean isGoalExceeded() {
        return getTotalCaloriesConsumed() > dailyCalorieGoal;
    }
    
    /**
     * Gets the percentage of daily goal achieved.
     * @return Percentage as a double (0.0 to 1.0+)
     */
    public double getGoalPercentage() {
        if (dailyCalorieGoal == 0) return 0.0;
        return (double) getTotalCaloriesConsumed() / dailyCalorieGoal;
    }
    
    /**
     * Gets meals by type for this day.
     * @param mealType The type of meal to filter by
     * @return List of meals of the specified type
     */
    public List<Meal> getMealsByType(Meal.MealType mealType) {
        List<Meal> filteredMeals = new ArrayList<>();
        for (Meal meal : meals) {
            if (meal.getMealType() == mealType) {
                filteredMeals.add(meal);
            }
        }
        return filteredMeals;
    }
    
    /**
     * Gets the formatted date string.
     * @return Formatted date string
     */
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
    
    @Override
    public String toString() {
        return username + "," + getFormattedDate() + "," + 
               getTotalCaloriesConsumed() + "," + dailyCalorieGoal;
    }
}
