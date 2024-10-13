@echo off
set /p input="Stack Images Folder: "

echo start generating image: %input%.png

"C:\Program Files\Helicon Software\Helicon Focus 8\HeliconFocus.exe" -silent -mp:2 -im:2 -rp:8 -sp:3 -ba:1 -va:30 -ha:30 -ra:20 -ma:30 -dmf:1 -save:%input%.dng -tiff:u %input%

