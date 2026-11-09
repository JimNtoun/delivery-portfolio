# Baseline and contribution record

## Starting point

The source was copied from the two extracted team-project archives, including the local-run
changes already made in this workspace. Original folders and running processes were preserved.
This snapshot does not claim the original implementation as an individual contribution.

Existing local-run adaptations before this repository:
- Spring Boot-managed JPA dependency version, H2 runtime and local profile.
- Windows build/start scripts selecting an installed Java 17 runtime.
- Angular response-container initialization to render before HTTP completion.
- Portable Node runtime used outside version control.

Repository setup contributions:
- Separated backend/frontend from interview documents and generated files.
- Added an independent Git repository, ignore rules and line-ending rules.
- Adjusted local scripts for the new directory layout and environment detection.
- Added read-only baseline smoke checks and explicit limitations.
- Added attribution, setup instructions and phased roadmap.

## Confirmed behavior before this phase

The original running local backend returned 16 stores, 3 accounts and 12 seeded orders.
The Angular page displayed the catalog, reports and a store menu.

## Known limitations (not fixed in this phase)

- Plaintext passwords, including password fields in API responses.
- No implemented authentication or order ownership enforcement.
- Login posts to account creation and expects a nonexistent token; registration is a placeholder.
- Checkout request and endpoint disagree between frontend and backend.
- Incoming order-item prices are trusted rather than fetched from the database.
- Positive quantities, store membership and null handling are insufficiently enforced.
- Missing resources can produce HTTP 500 rather than 404.
- Memory-only H2, create-drop and bootstrap seeders tied to fixed IDs.
- Broad generic entity CRUD endpoints and wildcard CORS.
- No application unit/integration tests or CI.
- Legacy framework/runtime versions; no dependency vulnerability audit performed here.
- Package/application identifiers still use demo1 and com.example/com.acme inconsistently.

The baseline smoke checks characterize only working read paths. They must evolve as API contracts change.
Do not add tests that enshrine password leakage or other known defects as desired behavior.

## Original archive fingerprints (SHA-256)

- Backend: 0732ADA64A044DADA39BC3C119DAD8F1F808A7B355EE5E4198E7282F43786EC8
- Frontend: 66EEF7B207EE372AB01ABD018F38D3225DA75D5D3F3F4494DBEFC2FE23C6CC12

These identify the supplied archives; the repository also includes the documented local adaptations.
