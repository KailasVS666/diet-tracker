#!/bin/bash

echo "=== Diet Planner & Nutrition Tracker ==="
echo

echo "Compiling Java files..."
javac -d bin src/main/java/com/diettracker/*.java src/main/java/com/diettracker/*/*.java

if [ $? -ne 0 ]; then
    echo "Compilation failed! Please check for errors."
    exit 1
fi

echo "Compilation successful!"
echo
echo "Starting the application..."
echo

java -cp bin com.diettracker.DietTrackerApp
