package com.acme.delivery.dto;

public record AccountResponse(
        Long id,
        String username,
        String email,
        String phoneNumber,
        int age,
        String address,
        String city
) {
}