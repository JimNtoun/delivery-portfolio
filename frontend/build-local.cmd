@echo off
setlocal
cd /d "%~dp0"
call ..\scripts\env.cmd
call npm.cmd ci --no-audit --no-fund
if errorlevel 1 exit /b 1
call npm.cmd run build -- --configuration development
exit /b %ERRORLEVEL%
