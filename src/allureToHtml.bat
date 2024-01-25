@echo off

set ALLURE_HOME=C:\Users\HP\scoop\apps\allure\current\bin
set REPORT_DIR=C:\SeleniumWithSauceLabsBrowserStackLambdaTest-master\allure-results

echo Starting Allure server...

"%ALLURE_HOME%\allure.bat" serve "%REPORT_DIR%"

echo Allure server has stopped.
