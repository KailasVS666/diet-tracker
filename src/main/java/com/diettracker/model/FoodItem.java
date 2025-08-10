package com.diettracker.model;

import java.io.Serializable;

/**
 * Represents a food item with its nutritional information.
 * Contains food name, calories per unit, and quantity consumed.
 */
public class FoodItem implements Serializable {
    private String name;
    private int caloriesPerUnit;
    private double quantity;
    private String unit; // e.g., "grams", "pieces", "cups"
    
    public FoodItem(String name, int caloriesPerUnit, double quantity, String unit) {
        this.name = name;
        this.caloriesPerUnit = caloriesPerUnit;
        this.quantity = quantity;
        this.unit = unit;
    }
    
    public FoodItem(String name, int caloriesPerUnit, double quantity) {
        this(name, caloriesPerUnit, quantity, "grams");
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCaloriesPerUnit() {
        return caloriesPerUnit;
    }
    
    public void setCaloriesPerUnit(int caloriesPerUnit) {
        this.caloriesPerUnit = caloriesPerUnit;
    }
    
    public double getQuantity() {
        return quantity;
    }
    
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    /**
     * Calculates the total calories for this food item.
     * @return Total calories based on calories per unit and quantity
     */
    public int getTotalCalories() {
        return (int) (caloriesPerUnit * quantity);
    }
    
    @Override
    public String toString() {
        return name + "," + caloriesPerUnit + "," + quantity + "," + unit;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FoodItem foodItem = (FoodItem) obj;
        return name.equals(foodItem.name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
