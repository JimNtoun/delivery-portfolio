package com.acme.delivery.service;

import com.acme.delivery.domain.Account;
import com.acme.delivery.dto.UpdateAccountRequest;
import com.acme.delivery.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


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

    @Override
    @Transactional
    public void updateProfile(Long id, UpdateAccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Account not found: " + id)
                );

        account.setUsername(request.username());
        account.setEmail(request.email());
        account.setPhoneNumber(request.phoneNumber());
        account.setAge(request.age());
        account.setAddress(request.address());
        account.setCity(request.city());
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
