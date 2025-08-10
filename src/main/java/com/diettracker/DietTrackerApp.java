package com.diettracker;

import com.diettracker.model.*;
import com.diettracker.service.*;
import com.diettracker.util.ValidationUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application class for the Diet Planner & Nutrition Tracker.
 * Provides a console-based interface for users to manage their diet tracking.
 */
public class DietTrackerApp {
    private UserService userService;
    private MealService mealService;
    private User currentUser;
    
    public DietTrackerApp() {
        this.userService = new UserService();
        this.mealService = new MealService(userService);
        this.currentUser = null;
    }
    
    /**
     * Main method to start the application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        DietTrackerApp app = new DietTrackerApp();
        app.run();
    }
    
    /**
     * Main application loop.
     */
    public void run() {
        System.out.println("=== Diet Planner & Nutrition Tracker ===");
        System.out.println("Welcome to your personal diet tracking system!");
        
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }
    
    /**
     * Displays the login/registration menu.
     */
    private void showLoginMenu() {
        System.out.println("\n=== Login Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        
        int choice = ValidationUtil.getValidIntegerInRangeInput("Enter your choice (1-3): ", 1, 3);
        
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Thank you for using Diet Planner & Nutrition Tracker!");
                ValidationUtil.closeScanner();
                System.exit(0);
        }
    }
    
    /**
     * Displays the main application menu.
     */
    private void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("Welcome, " + currentUser.getUsername() + "!");
        System.out.println("1. Log a Meal");
        System.out.println("2. View Today's Progress");
        System.out.println("3. View Meal History");
        System.out.println("4. Update Calorie Goal");
        System.out.println("5. View Statistics");
        System.out.println("6. Logout");
        System.out.println("7. Exit");
        
        int choice = ValidationUtil.getValidIntegerInRangeInput("Enter your choice (1-7): ", 1, 7);
        
        switch (choice) {
            case 1:
                logMeal();
                break;
            case 2:
                viewTodayProgress();
                break;
            case 3:
                viewMealHistory();
                break;
            case 4:
                updateCalorieGoal();
                break;
            case 5:
                viewStatistics();
                break;
            case 6:
                logout();
                break;
            case 7:
                System.out.println("Thank you for using Diet Planner & Nutrition Tracker!");
                ValidationUtil.closeScanner();
                System.exit(0);
        }
    }
    
    /**
     * Handles user login.
     */
    private void login() {
        System.out.println("\n=== Login ===");
        String username = ValidationUtil.getValidStringInput("Username: ");
        String password = ValidationUtil.getValidStringInput("Password: ");
        
        User user = userService.authenticateUser(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Login successful! Welcome back, " + username + "!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    
    /**
     * Handles user registration.
     */
    private void register() {
        System.out.println("\n=== Registration ===");
        
        String username;
        do {
            username = ValidationUtil.getValidStringInput("Username: ");
            if (!ValidationUtil.isValidUsername(username)) {
                System.out.println("Username must be 3-20 characters long and contain only letters, numbers, and underscores.");
            } else if (userService.getUserByUsername(username) != null) {
                System.out.println("Username already exists. Please choose a different one.");
            }
        } while (!ValidationUtil.isValidUsername(username) || userService.getUserByUsername(username) != null);
        
        String password;
        do {
            password = ValidationUtil.getValidStringInput("Password: ");
            if (!ValidationUtil.isValidPassword(password)) {
                System.out.println("Password must be at least 6 characters long.");
            }
        } while (!ValidationUtil.isValidPassword(password));
        
        if (userService.registerUser(username, password)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }
    
    /**
     * Handles meal logging.
     */
    private void logMeal() {
        System.out.println("\n=== Log a Meal ===");
        System.out.println("Select meal type:");
        System.out.println("1. Breakfast");
        System.out.println("2. Lunch");
        System.out.println("3. Dinner");
        System.out.println("4. Snack");
        
        int choice = ValidationUtil.getValidIntegerInRangeInput("Enter your choice (1-4): ", 1, 4);
        Meal.MealType mealType = Meal.MealType.values()[choice - 1];
        
        List<FoodItem> foodItems = new ArrayList<>();
        boolean addMore = true;
        
        while (addMore) {
            System.out.println("\n--- Add Food Item ---");
            String foodName = ValidationUtil.getValidStringInput("Food name: ");
            int caloriesPerUnit = ValidationUtil.getValidPositiveIntegerInput("Calories per unit: ");
            double quantity = ValidationUtil.getValidPositiveDoubleInput("Quantity: ");
            String unit = ValidationUtil.getValidStringInput("Unit (e.g., grams, pieces, cups): ");
            
            FoodItem foodItem = new FoodItem(foodName, caloriesPerUnit, quantity, unit);
            foodItems.add(foodItem);
            
            System.out.println("Added: " + foodName + " (" + foodItem.getTotalCalories() + " calories)");
            
            addMore = ValidationUtil.getYesNoInput("Add another food item?");
        }
        
        if (mealService.addMeal(currentUser.getUsername(), mealType, foodItems)) {
            System.out.println("Meal logged successfully!");
        } else {
            System.out.println("Failed to log meal. Please try again.");
        }
    }
    
    /**
     * Displays today's progress.
     */
    private void viewTodayProgress() {
        System.out.println("\n=== Today's Progress ===");
        LocalDate today = LocalDate.now();
        DailyLog dailyLog = mealService.createDailyLog(currentUser.getUsername(), today);
        
        if (dailyLog != null) {
            System.out.println("Date: " + dailyLog.getFormattedDate());
            System.out.println("Daily Goal: " + dailyLog.getDailyCalorieGoal() + " calories");
            System.out.println("Consumed: " + dailyLog.getTotalCaloriesConsumed() + " calories");
            System.out.println("Remaining: " + dailyLog.getRemainingCalories() + " calories");
            
            double percentage = dailyLog.getGoalPercentage() * 100;
            System.out.printf("Progress: %.1f%%\n", percentage);
            
            if (dailyLog.isGoalExceeded()) {
                System.out.println("⚠️  You have exceeded your daily calorie goal!");
            } else if (percentage >= 90) {
                System.out.println("🎉 Great job! You're close to your goal!");
            } else if (percentage >= 70) {
                System.out.println("👍 Good progress! Keep it up!");
            } else {
                System.out.println("💪 You still have room to reach your goal!");
            }
            
            // Show meals by type
            System.out.println("\nMeals today:");
            for (Meal.MealType mealType : Meal.MealType.values()) {
                List<Meal> mealsOfType = dailyLog.getMealsByType(mealType);
                if (!mealsOfType.isEmpty()) {
                    int totalCalories = mealsOfType.stream()
                            .mapToInt(Meal::getTotalCalories)
                            .sum();
                    System.out.println("  " + mealType.getDisplayName() + ": " + totalCalories + " calories");
                }
            }
        } else {
            System.out.println("No meals logged for today.");
        }
    }
    
    /**
     * Displays meal history.
     */
    private void viewMealHistory() {
        System.out.println("\n=== Meal History ===");
        List<Meal> userMeals = mealService.getMealsByUser(currentUser.getUsername());
        
        if (userMeals.isEmpty()) {
            System.out.println("No meals found in your history.");
            return;
        }
        
        // Group meals by date
        userMeals.sort((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));
        
        String currentDate = "";
        for (Meal meal : userMeals) {
            String mealDate = meal.getFormattedDate();
            if (!mealDate.equals(currentDate)) {
                currentDate = mealDate;
                System.out.println("\n" + mealDate + ":");
            }
            
            System.out.println("  " + meal.getFormattedTime() + " - " + 
                             meal.getMealType().getDisplayName() + 
                             " (" + meal.getTotalCalories() + " calories)");
            
            for (FoodItem item : meal.getFoodItems()) {
                System.out.println("    • " + item.getName() + " - " + 
                                 item.getQuantity() + " " + item.getUnit() + 
                                 " (" + item.getTotalCalories() + " calories)");
            }
        }
    }
    
    /**
     * Updates the user's calorie goal.
     */
    private void updateCalorieGoal() {
        System.out.println("\n=== Update Calorie Goal ===");
        System.out.println("Current daily calorie goal: " + currentUser.getDailyCalorieGoal() + " calories");
        
        int newGoal = ValidationUtil.getValidPositiveIntegerInput("Enter new daily calorie goal: ");
        
        if (userService.updateCalorieGoal(currentUser.getUsername(), newGoal)) {
            currentUser.setDailyCalorieGoal(newGoal);
            System.out.println("Calorie goal updated successfully!");
        } else {
            System.out.println("Failed to update calorie goal. Please try again.");
        }
    }
    
    /**
     * Displays user statistics.
     */
    private void viewStatistics() {
        System.out.println("\n=== Statistics ===");
        
        System.out.println("Select time period:");
        System.out.println("1. Last 7 days");
        System.out.println("2. Last 30 days");
        System.out.println("3. All time");
        
        int choice = ValidationUtil.getValidIntegerInRangeInput("Enter your choice (1-3): ", 1, 3);
        int days = choice == 1 ? 7 : choice == 2 ? 30 : 365; // All time approximated as 365 days
        
        double[] stats = mealService.getMealStatistics(currentUser.getUsername(), days);
        
        System.out.println("\nStatistics for the last " + days + " days:");
        System.out.println("Total meals logged: " + (int) stats[0]);
        System.out.println("Total calories consumed: " + (int) stats[1]);
        System.out.printf("Average calories per day: %.1f\n", stats[2]);
        
        if (stats[2] > 0) {
            double goalPercentage = (stats[2] / currentUser.getDailyCalorieGoal()) * 100;
            System.out.printf("Average daily goal achievement: %.1f%%\n", goalPercentage);
        }
    }
    
    /**
     * Handles user logout.
     */
    private void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
    }
}
