
@echo off
title Running QuizApp (safe compile)
cd "%~dp0src"
javac --module-path "C:\Users\subba rao kg\Downloads\openjfx-21.0.9_windows-x64_bin-sdk\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml MainApp.java QuizController.java ResultController.java Question.java QuizManager.java FileQuestionLoader.java InvalidQuestionFormatException.java TimerThread.java
if errorlevel 1 (
  echo Compilation failed. Check for errors.
  pause
  exit /b
)
cd ..
java --module-path "C:\Users\subba rao kg\Downloads\openjfx-21.0.9_windows-x64_bin-sdk\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml -cp src MainApp
pause
