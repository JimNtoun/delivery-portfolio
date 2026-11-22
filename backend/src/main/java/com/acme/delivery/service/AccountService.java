package com.acme.delivery.service;

import com.acme.delivery.domain.Account;
import com.acme.delivery.dto.UpdateAccountRequest;

public interface AccountService extends BaseService<Account>{
    Account findByEmail(String email);
    Account findByPhoneNumber(String phoneNumber);
    void updateProfile(Long id, UpdateAccountRequest request);
   // void addAddress(Account account, Address address);
}
