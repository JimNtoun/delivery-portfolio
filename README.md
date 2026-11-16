# Delivery Portfolio

A backend-focused evolution of the original **DeliveryApp_team8** educational team project.
The application is under active development; authentication and checkout are not complete.

## Implemented

- Java 17 and Spring Boot 4.0.9 REST backend, upgraded from Spring Boot 2.7.3.
- PostgreSQL 17 through Docker Compose with a persistent named volume.
- Flyway schema migrations and optional demo data in a separate profile.
- Account response DTOs and an explicit entity-to-response mapper.
- Password fields excluded from JSON serialization and entity toString output.
- Seven backend tests covering reads, reports, response fields, password omission,
  single/batch account creation and mapper behavior.
- Existing Angular 14 frontend for browsing stores, menus and reports.

## Structure

- `backend/`: Spring Boot, JPA, migrations and backend tests.
- `frontend/`: original Angular frontend with local-run adaptations.
- `scripts/`: Windows environment selection and read-only baseline smoke checks.
- `docs/`: project history, migration notes, verification and roadmap.

## Prerequisites

- JDK 17. Set JAVA_HOME to your JDK installation.
- Maven, or use the included `backend/mvnw.cmd` wrapper.
- Docker Desktop with the Linux container engine running, and Docker Compose.
- For the legacy frontend: Node.js 16.20.2 with npm on PATH.

`scripts/env.cmd` detects an installed JDK under `%USERPROFILE%\.jdks\ms-17.0.18`
and a portable Node runtime in `.tools`. These machine-local runtimes are not committed.
The frontend runtime/framework upgrade is a separate planned step.

## PostgreSQL demo (Windows CMD)

Run these commands from the repository root. Copy the example only on first setup;
keep an existing `.env` if you have already configured the database.

```cmd
copy .env.example .env
```

Edit `.env` and replace `POSTGRES_PASSWORD` with your local demo database password.
The `.env` file is ignored by Git. Its DB and user defaults are both `delivery`.

```cmd
docker compose up -d
docker compose ps
cd backend
build-local.cmd
call ..\scripts\env.cmd
set "POSTGRES_DB=delivery"
set "POSTGRES_USER=delivery"
set "POSTGRES_PASSWORD=YOUR_LOCAL_DATABASE_PASSWORD"
java -jar target\demo1-0.0.1-SNAPSHOT.jar --spring.profiles.active=postgres,demo --server.address=127.0.0.1
```

Use the same password in the CMD environment as in `.env`. Spring Boot does not automatically
load Compose's `.env` into the host JVM. Stop the server with Ctrl+C.
From Git Bash, run CMD scripts using `cmd.exe //c build-local.cmd`.

PostgreSQL is available at `localhost:5433`; the backend uses port `8080`.
Avoid running another backend on the same port.

Flyway applies V1 (schema) and V2 (demo data). Hibernate uses `ddl-auto: validate`.
On a fresh demo database the seed creates one customer, one store, two products,
one order and two order items. IDs are sequence-generated, not hard-coded.
Demo migrations run once; data persists across app and container restarts.
Keep both `postgres,demo` profiles for this demo database. A database initialized with V2
must retain its demo migration location for Flyway validation. A database without demo
fixtures should be a separate database using only the `postgres` profile.

From another terminal at the repository root:

```cmd
docker compose exec postgres psql -U delivery -d delivery -c "SELECT version, description, success FROM flyway_schema_history ORDER BY installed_rank;"
```

The current `.env` variables initialize an empty PostgreSQL volume. Changing them later
does not automatically rename an existing database/user or change its password.

## H2 demo and tests

`backend\start-local.cmd` explicitly uses the `local` profile: an in-memory H2 demo with
legacy seed data. It resets on restart. It is separate from the persistent PostgreSQL demo.
Flyway is disabled for this profile. The legacy Java seeders run only with `local`.

```cmd
cd backend
build-local.cmd
```

The build runs `BackendMigrationTest` against a real random-port server using H2,
and `AccountMapperTest` without Spring. PostgreSQL migrations/persistence were checked
manually during the guided setup; they do not yet have automated PostgreSQL integration tests.
See `docs/VERIFICATION.md` for evidence and scope.

## Frontend

In another terminal:

```cmd
cd frontend
build-local.cmd
start-local.cmd
```

Open http://localhost:4200/userhome. The frontend calls localhost:8080 and currently
uses the fixed demo account `spys@gmail.com`. The PostgreSQL demo includes that account.
Registration, login and order submission are unfinished. This step did not upgrade Angular.

## Example API calls

- GET http://localhost:8080/stores
- GET http://localhost:8080/stores?name=Demo%20Souvlaki
- GET http://localhost:8080/orders
- GET http://localhost:8080/accounts?email=spys@gmail.com

Account endpoints return `AccountResponse` inside the existing `ApiResponse` envelope.
The existing batch account-creation route is selected with the custom header `content: list`.
Its request/response behavior is covered by a regression test. Request DTOs are planned next.

## Current limitations

- Password storage is still plaintext; omitting passwords from JSON is not password hashing.
- Authentication, ownership enforcement, restricted CORS and consistent error mapping remain pending.
- Incoming account writes still accept entities; nested accounts in order responses still use entities.
- Checkout validation, authoritative database pricing and idempotency need implementation.
- The baseline smoke script assumes the legacy seeded H2 catalog; it is not a general PostgreSQL or browser test.

## Attribution and history

The original Java and Angular code came from the supplied team-project archives
`DeliveryApp_team8-master (2).zip` and `DeliveryApp_team8-main.zip`.
Original team work is the starting point; subsequent commits document the portfolio changes.
Author names, upstream URL and original licensing have not yet been confirmed; no license is invented.
See `docs/BASELINE.md`, `docs/BACKEND-UPGRADE.md` and `docs/ROADMAP.md`.
