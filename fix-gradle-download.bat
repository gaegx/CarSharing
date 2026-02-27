@echo off
echo Stopping Gradle daemons...
call gradlew --stop 2>nul
if exist gradlew.bat (call gradlew --stop 2>nul)

echo.
echo Close Android Studio / Cursor if the project is open there.
echo Deleting locked Gradle 8.4 cache folder...
set CACHE=%~dp0
set GRADLE_USER=%USERPROFILE%\.gradle\wrapper\dists\gradle-8.4-bin
if exist "%GRADLE_USER%" (
    rd /s /q "%GRADLE_USER%"
    echo Deleted: %GRADLE_USER%
) else (
    echo Folder not found: %GRADLE_USER%
)

echo.
echo Now run: gradlew assembleDebug
echo Or open the project in Android Studio and sync.
pause
