# Baseline verification

- Backend: offline Maven package succeeded with Java 17.0.18 using existing cached dependencies.
- A new JVM started the newly built backend on 127.0.0.1:8081.
- scripts/smoke-test.ps1 passed against that backend: store list, store details, orders,
  popularity report, top-products report and non-empty store menu.
- The frontend shell check passed against the pre-existing Angular development server on 4200.
  That is an HTML availability check, not an end-to-end browser test of the new source tree.
- No authentication, checkout or persistence-after-restart success is claimed.
- No application unit/integration tests existed at this baseline.
- Frontend: npm ci completed offline from the existing cache (921 packages); the new
  frontend source tree completed `ng build --configuration development` with exit code 0.
- The production/optimized Angular build was not part of this baseline verification.
- The temporary backend on 8081 was stopped after verification; original servers were preserved.

## Framework migration verification

The backend migration regression suite passed on all three checked versions:
- Spring Boot 2.7.3: 3 tests, 0 failures, 0 errors.
- Spring Boot 3.5.16: clean verify succeeded; 3 tests, 0 failures, 0 errors.
- Spring Boot 4.0.9: clean verify succeeded; 3 tests, 0 failures, 0 errors.

The tests start the real local-profile application on a random port and verify seeded
catalog IDs, menu contents and positive prices, orders/history, all three report queries
and JSON result mappings. They do not exercise authentication or order submission.
Java 17.0.18 was used for all builds. Oracle was not available and was not tested.

Known non-blocking warnings remain: explicit H2 dialect, legacy Temporal annotation and
Hikari keepalive/maxLifetime settings. They did not prevent startup, tests or shutdown.

Final executable-JAR check: the Spring Boot 4.0.9 JAR started on 127.0.0.1:8081 and
all seven baseline smoke checks passed after seed initialization. The frontend-shell
check used the existing server on 4200. The temporary JVM was stopped after verification.
