package com.acme.delivery;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/** Real HTTP regression checks for the seeded local demo during framework migration. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class BackendMigrationTest {
    @Autowired
    private Environment environment;
    private final HttpClient client = HttpClient.newHttpClient();

    private String get(String path) throws Exception {
        String port = environment.getRequiredProperty("local.server.port");
        var request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path)).GET().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).as("GET %s", path).isEqualTo(200);
        return response.body();
    }

    @Test
    void seededCatalogRetainsIndependentIdsAndMenuPrices() throws Exception {
        List<Map<String, Object>> stores = JsonPath.read(get("/stores"), "$.data");
        assertThat(stores).hasSize(16);
        assertThat(stores.stream().map(s -> s.get("id")).distinct().count()).isEqualTo(16);
        String store = get("/stores?storeId=1");
        assertThat(JsonPath.<String>read(store, "$.data.name")).isEqualTo("Souvlaki1");
        List<Map<String, Object>> products = JsonPath.read(store, "$.data.products");
        assertThat(products).hasSize(4);
        assertThat(products).allSatisfy(p ->
                assertThat(((Number) p.get("price")).doubleValue()).isPositive());
        assertThat(JsonPath.<Integer>read(get("/stores?name=Souvlaki1"), "$.data.id")).isEqualTo(1);
    }

    @Test
    void seededOrdersAndCustomerHistoryRemainReadable() throws Exception {
        List<Map<String, Object>> orders = JsonPath.read(get("/orders"), "$.data");
        assertThat(orders).hasSize(12);
        assertThat(orders).allSatisfy(o -> {
            assertThat(((Number) o.get("cost")).doubleValue()).isPositive();
            assertThat((List<?>) o.get("orderItems")).isNotEmpty();
        });
        List<Map<String, Object>> history = JsonPath.read(
                get("/orders/retrieveAllOrders/dimitris@gmail.com"), "$.data");
        assertThat(history).hasSize(4);
        assertThat(history).allSatisfy(o -> assertThat(
                ((Map<?, ?>) o.get("account")).get("email")).isEqualTo("dimitris@gmail.com"));
    }

    @Test
    void reportQueriesAndResultMappingsStillWork() throws Exception {
        assertThat(JsonPath.<List<?>>read(get("/stores/reportFamousStores"), "$.data")).hasSize(8);
        List<Map<String, Object>> top = JsonPath.read(get("/stores/reportTop10Products"), "$.data");
        assertThat(top).hasSize(10);
        assertThat(top).allSatisfy(row -> {
            assertThat(row.get("firstValue")).isNotNull();
            assertThat(row.get("secondValue")).isNotNull();
        });
        assertThat(JsonPath.<List<?>>read(get("/stores/reportFamousStoresPerCategory/1"), "$.data"))
                .isNotEmpty();
    }

    @Test
    void passwordsAreExcludedFromAccountsAndOrders() throws Exception {
        String accounts = get("/accounts");

        assertThat(
                JsonPath.<List<?>>read(accounts, "$.data")
        ).isNotEmpty();

        assertThat(
                JsonPath.<List<?>>read(accounts, "$..password")
        ).isEmpty();

        String orders = get("/orders");

        assertThat(
                JsonPath.<List<?>>read(orders, "$.data")
        ).isNotEmpty();

        assertThat(
                JsonPath.<List<?>>read(orders, "$..password")
        ).isEmpty();

        String account = get("/accounts?email=dimitris@gmail.com");

        assertThat(
                JsonPath.<String>read(account, "$.data.email")
        ).isEqualTo("dimitris@gmail.com");

        assertThat(
                JsonPath.<List<?>>read(account, "$..password")
        ).isEmpty();
    }

    @Test
    void accountReadEndpointsReturnTheExpectedResponseFields() throws Exception {
        List<Map<String, Object>> accounts =
                JsonPath.read(get("/accounts"), "$.data");

        assertThat(accounts).isNotEmpty();

        for (Map<String, Object> account : accounts) {
            assertThat(account.keySet()).containsExactlyInAnyOrder(
                    "id",
                    "username",
                    "email",
                    "phoneNumber",
                    "age",
                    "address",
                    "city"
            );
        }

        Map<String, Object> first = accounts.get(0);

        List<String> paths = List.of(
                "/accounts/" + first.get("id"),
                "/accounts?email=" + first.get("email"),
                "/accounts?phoneNumber=" + first.get("phoneNumber")
        );

        for (String path : paths) {
            Map<String, Object> detail =
                    JsonPath.read(get(path), "$.data");

            assertThat(detail)
                    .as("Account response from %s", path)
                    .isEqualTo(first);
        }
    }
    @Test
    void accountCreationSupportsSingleAndListRequests() throws Exception {
        String singleBody = """
                {"username":"SingleUser","password":"test-only-secret",
                 "email":"single@example.test","phoneNumber":"6900000001",
                 "age":30,"address":"Demo Street 1","city":"Athens"}
                """;
        String single = postAccount(singleBody, false);
        assertThat(JsonPath.<String>read(single, "$.data.email")).isEqualTo("single@example.test");
        assertThat(JsonPath.<List<?>>read(single, "$..password")).isEmpty();
        Number id = JsonPath.read(single, "$.data.id");
        assertThat(JsonPath.<String>read(get("/accounts/" + id), "$.data.email"))
                .isEqualTo("single@example.test");

        String batchBody = """
        {
          "accounts": [
            {
              "username":"BatchUserOne",
              "password":"test-only-secret",
              "email":"batch.one@example.test",
              "phoneNumber":"6900000002",
              "age":30,
              "address":"Demo Street 2",
              "city":"Athens"
            },
            {
              "username":"BatchUserTwo",
              "password":"test-only-secret",
              "email":"batch.two@example.test",
              "phoneNumber":"6900000003",
              "age":31,
              "address":"Demo Street 3",
              "city":"Athens"
            }
          ]
        }
        """;

        String batch = postAccount(batchBody, true);
        assertThat(JsonPath.<List<?>>read(batch, "$.data")).hasSize(2);
        assertThat(JsonPath.<List<String>>read(batch, "$.data[*].email"))
                .containsExactlyInAnyOrder("batch.one@example.test", "batch.two@example.test");
        assertThat(JsonPath.<List<?>>read(batch, "$..password")).isEmpty();
    }

    private String postAccount(String body, boolean batch) throws Exception {
        String port = environment.getRequiredProperty("local.server.port");
        var builder = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + "/accounts"))
                .header("Content-Type", "application/json")
                .timeout(java.time.Duration.ofSeconds(15));
        if (batch) {
            builder.header("content", "list");
        }
        var request = builder.POST(HttpRequest.BodyPublishers.ofString(body)).build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).as("POST /accounts (batch=%s): %s", batch, response.body())
                .isEqualTo(201);
        return response.body();
    }

    @Test
    void invalidAccountRequestsAreRejectedWithoutSaving() throws Exception {
        String validAccount = """
            {
              "username":"BatchValid",
              "password":"test-only-secret",
              "email":"batch.valid@example.test",
              "phoneNumber":"6900000011",
              "age":30,
              "address":"Demo Street 11",
              "city":"Athens"
            }
            """;

        String invalidAccount = validAccount
                .replace("\"BatchValid\"", "\" \"")
                .replace(
                        "\"batch.valid@example.test\"",
                        "\"batch.invalid@example.test\""
                )
                .replace("6900000011", "6900000012");

        List<String> invalidBodies = List.of(
                "{}",
                "{\"accounts\":[]}",
                "{\"accounts\":[null]}",
                "{\"accounts\":[" + validAccount + "," + invalidAccount + "]}"
        );

        int initialCount = JsonPath.<List<?>>read(
                get("/accounts"), "$.data"
        ).size();

        String port = environment.getRequiredProperty("local.server.port");

        for (String body : invalidBodies) {
            var request = HttpRequest.newBuilder(
                            URI.create("http://127.0.0.1:" + port + "/accounts")
                    )
                    .header("Content-Type", "application/json")
                    .header("content", "list")
                    .timeout(java.time.Duration.ofSeconds(15))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            var response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            assertThat(response.statusCode())
                    .as("Invalid batch must be rejected: %s", response.body())
                    .isEqualTo(400);

            assertThat(
                    JsonPath.<List<?>>read(get("/accounts"), "$.data")
            ).hasSize(initialCount);
        }
    }

    @Test
    void invalidBatchRequestsAreRejectedWithoutSaving() throws Exception {
        String validAccount = """
            {
              "username":"BatchValid",
              "password":"test-only-secret",
              "email":"batch.valid@example.test",
              "phoneNumber":"6900000011",
              "age":30,
              "address":"Demo Street 11",
              "city":"Athens"
            }
            """;

        String invalidAccount = validAccount
                .replace("\"BatchValid\"", "\" \"")
                .replace(
                        "\"batch.valid@example.test\"",
                        "\"batch.invalid@example.test\""
                )
                .replace("6900000011", "6900000012");

        List<String> invalidBodies = List.of(
                "{}",
                "{\"accounts\":[]}",
                "{\"accounts\":[null]}",
                "{\"accounts\":[" + validAccount + "," + invalidAccount + "]}"
        );

        int initialCount = JsonPath.<List<?>>read(
                get("/accounts"), "$.data"
        ).size();

        String port = environment.getRequiredProperty("local.server.port");

        for (String body : invalidBodies) {
            var request = HttpRequest.newBuilder(
                            URI.create("http://127.0.0.1:" + port + "/accounts")
                    )
                    .header("Content-Type", "application/json")
                    .header("content", "list")
                    .timeout(java.time.Duration.ofSeconds(15))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            var response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            assertThat(response.statusCode())
                    .as("Invalid batch must be rejected: %s", response.body())
                    .isEqualTo(400);

            assertThat(
                    JsonPath.<List<?>>read(get("/accounts"), "$.data")
            ).hasSize(initialCount);
        }
    }
}
