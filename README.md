# Diet Planner & Nutrition Tracker

A Java-based application designed to help users monitor their daily food intake and manage their dietary goals. The system allows users to register, set personal calorie goals, and log meals throughout the day under categories such as breakfast, lunch, dinner, and snacks.

## Features

- **User Registration & Management**: Create and manage user profiles
- **Calorie Goal Setting**: Set personalized daily calorie targets
- **Meal Logging**: Log meals under different categories (breakfast, lunch, dinner, snacks)
- **Food Item Tracking**: Record food items with calorie values and quantities
- **Daily Progress Tracking**: Monitor daily calorie consumption vs. goals
- **Data Persistence**: All data stored using file handling for session persistence
- **Simple Console Interface**: Easy-to-use command-line interface

## Project Structure

```
src/
├── main/
│   └── java/
│       └── com/
│           └── diettracker/
│               ├── model/
│               │   ├── User.java
│               │   ├── FoodItem.java
│               │   ├── Meal.java
│               │   └── DailyLog.java
│               ├── service/
│               │   ├── UserService.java
│               │   ├── MealService.java
│               │   └── FileService.java
│               ├── util/
│               │   └── ValidationUtil.java
│               └── DietTrackerApp.java
data/
├── users.txt
├── meals.txt
└── daily_logs.txt
```

## How to Run

1. Compile the Java files:
   ```bash
   javac -d bin src/main/java/com/diettracker/*.java src/main/java/com/diettracker/*/*.java
   ```

2. Run the application:
   ```bash
   java -cp bin com.diettracker.DietTrackerApp
   ```

## Usage

1. **Register a new user** or **login** with existing credentials
2. **Set your daily calorie goal** (if not already set)
3. **Log meals** by selecting meal type and adding food items
4. **View daily progress** to see calorie consumption vs. goal
5. **Exit** to save your data

## Technical Details

- **Language**: Java (Core Java)
- **Design Pattern**: Object-Oriented Programming
- **Data Storage**: File-based persistence using text files
- **Key Concepts Demonstrated**:
  - Class design and encapsulation
  - Data abstraction
  - File I/O operations
  - Exception handling
  - Input validation
  - Service layer architecture

## Data Files

- `users.txt`: Stores user information and calorie goals
- `meals.txt`: Stores meal entries with food items
- `daily_logs.txt`: Stores daily calorie summaries

## Future Enhancements

- GUI interface using Swing or JavaFX
- Database integration
- Nutritional information (protein, carbs, fats)
- Meal planning and recipes
- Progress charts and reports
- Mobile app version