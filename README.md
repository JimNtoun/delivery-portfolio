# Delivery Portfolio

A backend-focused evolution of the original **DeliveryApp_team8** educational team project.
This repository starts from the locally runnable version; it is not yet a finished application.

## Attribution

The original Java and Angular implementations were supplied as `DeliveryApp_team8-master (2).zip`
and `DeliveryApp_team8-main.zip`. Original team work is preserved as the starting point.
The archive names do not establish a public repository URL, author list or license.
Those details must be verified before public distribution; no license has been invented.
See [docs/BASELINE.md](docs/BASELINE.md) for the initial state and subsequent contribution record.

## Structure

- `backend/`: Java 17, Spring Boot 2.7.3, Maven, JPA, local H2 profile.
- `frontend/`: Angular 14, Angular Material, Bootstrap.
- `scripts/`: environment selection and read-only baseline smoke checks.
- `docs/`: known limitations, validation and roadmap.

## Local build and run (Windows)

Prerequisites for this legacy baseline: JDK 17, Node.js 16.20.2 with npm, and internet for a first build.
These versions reproduce the existing project; framework/runtime upgrades are a separate planned step.
Maven is optional when the included backend wrapper can download its distribution.
Set JAVA_HOME to your JDK 17 directory and put Node/npm on PATH.
On this computer `scripts/env.cmd` also detects the existing JDK and the portable Node installation
in the parent workspace's `.tools` folder. Neither runtime is committed.

1. Run `backend\build-local.cmd`.
2. Run `frontend\build-local.cmd`.
3. In a terminal run `backend\start-local.cmd`.
4. In a second terminal run `frontend\start-local.cmd`.
5. Open http://localhost:4200/userhome.

If the original copies are already running on 8080 and 4200, stop those terminals first
with Ctrl+C, or use the alternate ports below. The baseline scripts do not stop other processes.
Both servers bind to 127.0.0.1. Stop each using Ctrl+C in its terminal.
Backend data resets on restart. The catalog uses seeded demo data.
The frontend uses a fixed demo account; authentication and checkout remain unfinished.

## Baseline verification

With both servers ready:

```powershell
powershell -NoProfile -File .\scripts\smoke-test.ps1
```

This verifies read-only catalog/report endpoints, a menu with products, and the Angular HTML shell.
It does **not** execute Angular in a browser, test checkout or certify security.
The project currently has no application unit/integration tests.

To check a newly built backend while the original is running:

```powershell
java -jar backend/target/demo1-0.0.1-SNAPSHOT.jar --spring.profiles.active=local --server.address=127.0.0.1 --server.port=8081
```

To use the corresponding frontend, change its API configuration in the later integration phase;
its original source currently points to localhost:8080.

## Development

Keep build outputs, dependencies, caches and local secrets out of Git.
Commits should describe actual contributions and retain attribution to the original team.
Use [docs/ROADMAP.md](docs/ROADMAP.md) for the agreed phase boundaries.
