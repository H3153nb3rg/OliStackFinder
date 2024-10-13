@echo off
set foldername=%1%

echo start generating stack from : %foldername%

IF EXIST %foldername%.dng goto exists
IF NOT EXIST %foldername%.dng goto create
	
rem else
:create	
echo creating: %foldername%.dng
"C:\Program Files\Helicon Software\Helicon Focus 8\HeliconFocus.exe" -silent -mp:2 -im:2 -rp:8 -sp:3 -ba:1 -va:30 -ha:30 -ra:20 -ma:30 -dmf:1 -save:%foldername%.dng %foldername%
goto ende
:exists
echo exists: %foldername%.dng
:ende
