echo off

:: Terminate processes running on specified ports
for /f "tokens=5" %%p in ('netstat -a -n -o ^| findstr :8082') do taskkill /f /pid %%p

:: Wait for all processes to finish
timeout /t 5