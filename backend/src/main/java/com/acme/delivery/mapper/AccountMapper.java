package com.acme.delivery.mapper;

import com.acme.delivery.domain.Account;
import com.acme.delivery.dto.AccountResponse;
import com.acme.delivery.dto.CreateAccountRequest;

public final class AccountMapper {

    private AccountMapper() {
    }

    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getUsername(),
                account.getEmail(),
                account.getPhoneNumber(),
                account.getAge(),
                account.getAddress(),
                account.getCity()
        );
    }

    public static Account toNewAccount(CreateAccountRequest request) {
        return Account.builder()
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .age(request.age())
                .address(request.address())
                .city(request.city())
                .build();
    }
}