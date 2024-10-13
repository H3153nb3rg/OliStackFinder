@echo off

if %1.==. (
    set /p input="Stack Images Folder: "
	set foldername=%input%
) else (
    set foldername=%1%
)
set foldername

echo start generating image: %foldername%.png

rem "C:\Program Files\Helicon Software\Helicon Focus 7\HeliconFocus.exe" -silent -mp:2 -im:2 -rp:8 -sp:3 -ba:1 -va:30 -ha:30 -ra:20 -ma:30 -dmf:1 -save:%input%.dng -tiff:u %input%

pause