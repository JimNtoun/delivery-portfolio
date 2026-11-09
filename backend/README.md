# Delivery App — local setup

Java 17 / Spring Boot 4.0.9 REST backend for stores, accounts and delivery orders.
The original archive contains no frontend. The root URL `/` does not have a web page.

## Run on this computer

1. Run `build-local.cmd` (first build needs internet for Maven dependencies).
2. Run `start-local.cmd`.
3. Open http://localhost:8080/stores in a browser.
4. Stop the server with Ctrl+C in its terminal.

The scripts select the installed JDK at `%USERPROFILE%\.jdks\ms-17.0.18`.
On another computer, set JAVA_HOME to a Java 17 JDK.
Maven is already installed on this computer; the build script falls back to the included Maven wrapper if needed.

## Local database

The `local` Spring profile uses an in-memory H2 database in Oracle compatibility mode.
Sample data is loaded on startup. Changes are lost when the server stops.
The original Oracle configuration remains in application.yml; start-local.cmd always selects local.
Do not use this demo configuration for production or enter real passwords/personal data.

## Example API requests

- GET http://localhost:8080/stores
- GET http://localhost:8080/stores?storeId=1
- GET http://localhost:8080/accounts
- GET http://localhost:8080/orders
- GET http://localhost:8080/orders/retrieveAllOrders/dimitris@gmail.com
- GET http://localhost:8080/stores/reportFamousStores
- GET http://localhost:8080/stores/reportTop10Products

## Initial changes

- Removed the floating RELEASE version from the JPA starter so Spring Boot manages the compatible version.
- Added H2 as a runtime dependency and application-local.yml for a self-contained demo.
- Added Windows scripts for building and running with Java 17.

This is a starting point for a portfolio project. Authentication, input validation, error handling,
DTOs and automated tests should be reviewed before publishing it as a complete application.

## Verified locally

Build completed successfully with Java 17.0.18. The original archive had no automated tests. The migrated backend includes BackendMigrationTest; see ../docs/BACKEND-UPGRADE.md.
The following HTTP smoke checks returned 200 with the seeded demo data:

| Endpoint | Result |
| --- | --- |
| /stores | 16 stores |
| /stores?storeId=1 | 1 store with products |
| /accounts | 3 accounts |
| /orders | 12 orders |
| /orders/retrieveAllOrders/dimitris@gmail.com | 4 orders |
| /stores/reportFamousStores | 8 report rows |
| /stores/reportTop10Products | 10 report rows |

The local startup script binds to 127.0.0.1. If port 8080 is already occupied,
stop the previous instance before starting another one.
