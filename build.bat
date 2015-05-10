@echo off 

if not exist %CD%\build mkdir %CD%\build

javac -encoding utf8 -sourcepath %CD%\src\main -d build src\main\*.java
 
if not "%1" == "run" goto norun

java -classpath %CD%\build Main
 
:norun 

if not "%1" == "jar" goto nojar

if exist %CD%\softlab4.jar del %CD%\softlab4.jar
cd build\
jar cfe ..\softlab4.jar Main *
cd ..

:nojar

