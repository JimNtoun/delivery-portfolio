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

}
