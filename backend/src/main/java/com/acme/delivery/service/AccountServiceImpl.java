package com.acme.delivery.service;

import com.acme.delivery.domain.Account;
import com.acme.delivery.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends BaseServiceImpl<Account> implements AccountService {
    private final AccountRepository accountRepository;


    @Override
    public JpaRepository<Account, Long> getRepository() {
        return accountRepository;
    }


    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account findByPhoneNumber(String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber);
    }
/*
    @Override
    public void addAddress(final Account account, final Address address){
        if(Objects.isNull(account.getAddress())){
            account.setAddress(new Address());
        }
        account.getAddress();
    }

 */
}
