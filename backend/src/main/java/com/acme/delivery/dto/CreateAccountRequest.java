package com.acme.delivery.dto;

import jakarta.validation.constraints.*;

public record CreateAccountRequest(

        @NotBlank
        @Size(min = 3, max = 15)
        String username,

        @NotBlank
        @Size(min = 8, max = 30)
        String password,

        @NotBlank
        @Email
        @Size(max = 30)
        String email,

        @NotBlank
        @Size(max = 13)
        String phoneNumber,

        @NotNull
        @Min(18)
        @Max(100)
        Integer age,

        @NotBlank
        @Size(max = 30)
        String address,

        @NotBlank
        @Size(max = 30)
        String city
) {

    @Override
    public String toString() {
        return "CreateAccountRequest[password=<redacted>]";
    }
}