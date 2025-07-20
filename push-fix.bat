@echo off
echo Pushing GitHub Actions fix...
git add .github/workflows/android.yml
git commit -m "Fix GitHub Actions: Update to latest action versions"
git push
echo Done! Check the Actions tab in 2-3 minutes for the APK.
pause 