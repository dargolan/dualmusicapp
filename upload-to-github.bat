@echo off
echo ========================================
echo Dual Music App - GitHub Upload Helper
echo ========================================
echo.

echo This script will help you upload your project to GitHub.
echo.
echo Please make sure you have:
echo 1. Git installed on your computer
echo 2. A GitHub account
echo 3. Created a repository named 'dualmusicapp'
echo.

set /p GITHUB_USERNAME="Enter your GitHub username: "
set /p REPO_NAME="Enter your repository name (default: dualmusicapp): "

if "%REPO_NAME%"=="" set REPO_NAME=dualmusicapp

echo.
echo Initializing Git repository...
git init

echo Adding all files...
git add .

echo Creating initial commit...
git commit -m "Initial commit: Dual Music App with Spotify and YouTube integration"

echo Setting up remote repository...
git branch -M main
git remote add origin https://github.com/%GITHUB_USERNAME%/%REPO_NAME%.git

echo Pushing to GitHub...
git push -u origin main

echo.
echo ========================================
echo Upload complete!
echo ========================================
echo.
echo Your repository is now available at:
echo https://github.com/%GITHUB_USERNAME%/%REPO_NAME%
echo.
echo The APK will be built automatically in the Actions tab.
echo Check back in 2-3 minutes for the download link.
echo.
pause 