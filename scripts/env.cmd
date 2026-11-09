@echo off
rem Shared environment for the existing local setup. Nothing is downloaded here.
if not defined JAVA_HOME if exist "%USERPROFILE%\.jdks\ms-17.0.18\bin\java.exe" set "JAVA_HOME=%USERPROFILE%\.jdks\ms-17.0.18"
if defined JAVA_HOME set "PATH=%JAVA_HOME%\bin;%PATH%"
if exist "%~dp0..\.tools\node-v16.20.2-win-x64\node.exe" set "PATH=%~dp0..\.tools\node-v16.20.2-win-x64;%PATH%"
if exist "%~dp0..\..\.tools\node-v16.20.2-win-x64\node.exe" set "PATH=%~dp0..\..\.tools\node-v16.20.2-win-x64;%PATH%"
set "NG_CLI_ANALYTICS=false"
