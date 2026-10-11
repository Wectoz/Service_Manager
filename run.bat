@echo off
echo Compiling Java files...
javac -d bin src\*.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Running Service Manager...
java -cp bin Main
pause
