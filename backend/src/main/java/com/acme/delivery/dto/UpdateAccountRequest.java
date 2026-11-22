package com.acme.delivery.dto;

import jakarta.validation.constraints.*;

public record UpdateAccountRequest(

        @NotBlank
        @Size(min = 3, max = 15)
        String username,

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
}