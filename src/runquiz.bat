@echo off
rem compile
cd "%~dp0src"
javac --module-path "C:\Users\subba rao kg\Downloads\openjfx-21.0.9_windows-x64_bin-sdk\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml *.java
if errorlevel 1 pause & exit /b 1
rem run from project root so resources/ is found
cd ..
java --module-path "C:\Users\subba rao kg\Downloads\openjfx-21.0.9_windows-x64_bin-sdk\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.fxml -cp src MainApp
pause
