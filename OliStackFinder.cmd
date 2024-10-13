@echo off
set /p input="Olympus RAW Images Folder: "
echo start checking: %input%
java -jar C:\Users\johannes\workspaces\jps\OliStackFinder\OliStackFinder.jar %input%