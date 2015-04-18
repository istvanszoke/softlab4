@echo off 

if not exist %CD%\build mkdir %CD%\build

javac -sourcepath %CD%\src\main -d build src\main\*.java
 
if not "%1" == "run" goto norun
 
java -classpath %CD%\build Main
 
:norun 
