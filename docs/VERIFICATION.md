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
