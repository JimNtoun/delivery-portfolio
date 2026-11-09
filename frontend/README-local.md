# Delivery App frontend — local setup

Angular 14 frontend paired with the Java backend in ../DeliveryApp_team8-master.

## Start

1. Start the backend using its start-local.cmd (port 8080).
2. Run this folder's start-local.cmd (port 4200).
3. Open http://localhost:4200/userhome to browse stores with the original demo account.

The scripts use the portable Node.js 16.20.2 runtime in ../.tools, matching this legacy Angular project.
Run build-local.cmd to reinstall the locked dependencies and build the development bundle.
Stop the frontend using Ctrl+C in its terminal.

## Original limitations

The existing frontend includes unfinished login, registration and order submission flows.
The userhome page uses the original hard-coded demo account (spys@gmail.com).
This initial setup is intended to run and inspect the existing application locally.
The backend uses an in-memory demo database; restarting it resets the data.

## Local changes

- Added Windows build and start scripts using a local Node runtime.
- Initialized API response containers so templates can render before HTTP requests complete.
