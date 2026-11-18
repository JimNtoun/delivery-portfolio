package com.acme.delivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateAccountsRequest(

        @NotEmpty
        @Size(max = 100)
        List<@NotNull @Valid CreateAccountRequest> accounts
) {
}