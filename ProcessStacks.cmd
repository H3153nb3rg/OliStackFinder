@echo off
set /p input="Folder containing stacks: "
echo start processing stacks from folder: %input%
cd %input%
cd
for /D %%G IN (Stack-*) do "C:\Users\johannes\workspaces\jps\OliStackFinder\Stack.cmd" "%%~fG"


