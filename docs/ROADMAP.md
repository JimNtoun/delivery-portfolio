# Roadmap

Each phase is reviewed before the next is started. Tests accompany implementation.

1. Repository baseline: separate sources, preserve attribution, document execution and read-path checks.
2. Backend upgrade: characterize behavior, migrate dependencies/imports in a separate change, verify builds and reads.
3. PostgreSQL and Flyway: persistent storage, demo-only repeatable initialization, external configuration, database Compose service.
4. API contracts: request/response DTOs, validation, exception mapping, restricted operations, pagination and loading strategy.
5. Security: password hashing, registration/login/logout, session/CSRF configuration, ownership tests and restricted CORS.
6. Checkout: database pricing, quantities, one-store rule, atomic persistence, idempotency and explicit order state rules.
7. Angular integration: upgrade separately, replace fixed demo user, complete forms/cart/history and browser flow checks.
8. Portfolio delivery: CI, OpenAPI, health/logging, architecture/ER diagrams, demo script and interview notes.

## First complete scope

Customer registration/login, public catalog/menu, cart, simulated payment, order placement and own history.
Initial order state PLACED; customer cancellation only if its rule is explicitly implemented and tested.
Staff fulfillment screens and real payments are outside the initial scope.

## Release acceptance

- Documented startup works from a clean checkout with declared prerequisites.
- Migrations initialize PostgreSQL and data survives restart.
- A new user completes the browser journey.
- Invalid orders cannot partially persist or use client-supplied prices.
- A repeated submission does not accidentally create another order.
- Passwords are never exposed; cross-account order access is denied.
- Critical unit/integration/security checks and CI pass.
- Known gaps and original/team versus individual contributions are documented.
