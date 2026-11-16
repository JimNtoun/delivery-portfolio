package com.acme.delivery;

import com.acme.delivery.domain.Account;
import com.acme.delivery.dto.AccountResponse;
import com.acme.delivery.mapper.AccountMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountMapperTest {

    @Test
    void mapsAccountFieldsToResponse() {
        // Arrange: δημιουργούμε τα δεδομένα εισόδου.
        Account account = Account.builder()
                .id(42L)
                .username("DemoUser")
                .password("test-only-secret")
                .email("demo@example.test")
                .phoneNumber("6900000000")
                .age(30)
                .address("Demo Street 1")
                .city("Athens")
                .build();

        // Act: εκτελούμε τη μετατροπή.
        AccountResponse response = AccountMapper.toResponse(account);

        // Assert: ελέγχουμε τα αποτελέσματα.
        assertThat(response.id()).isEqualTo(42L);
        assertThat(response.username()).isEqualTo("DemoUser");
        assertThat(response.email()).isEqualTo("demo@example.test");
        assertThat(response.phoneNumber()).isEqualTo("6900000000");
        assertThat(response.age()).isEqualTo(30);
        assertThat(response.address()).isEqualTo("Demo Street 1");
        assertThat(response.city()).isEqualTo("Athens");
    }
}