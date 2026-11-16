package com.acme.delivery.mapper;

import com.acme.delivery.domain.Account;
import com.acme.delivery.dto.AccountResponse;

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
}