@echo off
setlocal
cd /d "%~dp0"
call ..\scripts\env.cmd
if not exist node_modules\@angular\cli\bin\ng.js (
  echo Run frontend\build-local.cmd first.
  exit /b 1
)
call npm.cmd start -- --host 127.0.0.1
