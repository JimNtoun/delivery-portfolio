package com.acme.delivery.controller;

import com.acme.delivery.domain.Account;
import com.acme.delivery.dto.AccountResponse;
import com.acme.delivery.mapper.AccountMapper;
import com.acme.delivery.service.AccountService;
import com.acme.delivery.transfer.ApiResponse;
import com.acme.delivery.dto.CreateAccountRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Email;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
@CrossOrigin("*")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> findAll() {
        List<AccountResponse> responses = accountService.findAll()
                .stream()
                .map(AccountMapper::toResponse)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.<List<AccountResponse>>builder()
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> get(
            @PathVariable("id") Long id
    ) {
        Account account = accountService.get(id);
        return ResponseEntity.ok(toApiResponse(account));
    }

    @GetMapping(params = "email")
    public ResponseEntity<ApiResponse<AccountResponse>> findByEmail(
            @Email @RequestParam String email
    ) {
        Account account = accountService.findByEmail(email);

        if (account == null) {
            throw new NoSuchElementException("Account not found");
        }

        return ResponseEntity.ok(toApiResponse(account));
    }

    @GetMapping(params = "phoneNumber")
    public ResponseEntity<ApiResponse<AccountResponse>> findByPhoneNumber(
            @RequestParam String phoneNumber
    ) {
        Account account = accountService.findByPhoneNumber(phoneNumber);

        if (account == null) {
            throw new NoSuchElementException("Account not found");
        }

        return ResponseEntity.ok(toApiResponse(account));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> create(
            @Valid @RequestBody CreateAccountRequest request
    ) {
        Account newAccount = AccountMapper.toNewAccount(request);
        Account savedAccount = accountService.create(newAccount);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toApiResponse(savedAccount));
    }

    @PostMapping(headers = "content=list")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> createAll(
            @RequestBody List<Account> accounts
    ) {
        List<AccountResponse> responses = accountService.createAll(accounts)
                .stream()
                .map(AccountMapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<List<AccountResponse>>builder()
                                .data(responses)
                                .build()
                );
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Account account) {
        accountService.update(account);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        accountService.deleteById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @RequestBody Account account) {
        if (accountService.exists(account)) {
            accountService.delete(account);
        }
    }

    private ApiResponse<AccountResponse> toApiResponse(Account account) {
        return ApiResponse.<AccountResponse>builder()
                .data(AccountMapper.toResponse(account))
                .build();
    }
}
