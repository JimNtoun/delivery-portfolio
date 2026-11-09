@echo off
setlocal
cd /d "%~dp0"
call ..\scripts\env.cmd
where mvn >nul 2>nul
if errorlevel 1 (
  call mvnw.cmd -B package
) else (
  call mvn -B package
)
exit /b %ERRORLEVEL%
