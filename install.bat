@echo off

call terminate.bat
:: Set the path to the Java executable
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_291
set PATH=%JAVA_HOME%\bin;%PATH%

:: Set the path to the JAR files
start java -jar server/web-Ecommerce-smartvoucher/target/web-ecommerce-smartvoucher-1.0-SNAPSHOT.jar

:: Wait for all processes to finish
timeout /t 5