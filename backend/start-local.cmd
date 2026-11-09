@echo off
setlocal
cd /d "%~dp0"
call ..\scripts\env.cmd
if not exist "target\demo1-0.0.1-SNAPSHOT.jar" (
  echo Run backend\build-local.cmd first.
  exit /b 1
)
java -jar target\demo1-0.0.1-SNAPSHOT.jar --spring.profiles.active=local --server.address=127.0.0.1
