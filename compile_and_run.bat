@echo off
echo === Diet Planner & Nutrition Tracker ===
echo.

echo Compiling Java files...
javac -d bin src\main\java\com\diettracker\*.java src\main\java\com\diettracker\model\*.java src\main\java\com\diettracker\service\*.java src\main\java\com\diettracker\util\*.java

if %errorlevel% neq 0 (
    echo Compilation failed! Please check for errors.
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Starting the application...
echo.

java -cp bin com.diettracker.DietTrackerApp

pause
