@echo off

set HUB=192.168.43.1:5555
set APK=TeamCode\build\outputs\apk\debug\TeamCode-debug.apk

echo.
echo Connecting to Control Hub...
adb connect %HUB% >nul

echo.
echo Installing APK...
adb install -r "%APK%"

if %errorlevel% neq 0 (
    echo.
    echo Install failed.
    exit /b %errorlevel%
)

echo.
echo Restarting Robot Controller...
adb shell am force-stop com.qualcomm.ftcrobotcontroller
adb shell monkey -p com.qualcomm.ftcrobotcontroller -c android.intent.category.LAUNCHER 1 >nul