@echo off

set APK=TeamCode\build\outputs\apk\debug\TeamCode-debug.apk

echo.
echo ===== FTC DEPLOY =====
echo.

cd /d %~dp0

echo Building TeamCode...
call gradlew.bat :TeamCode:assembleDebug

if %errorlevel% neq 0 (
    exit /b %errorlevel%
)