package com.acme.delivery.controller;

import com.acme.delivery.domain.Account;
import com.acme.delivery.service.AccountService;
import com.acme.delivery.service.BaseService;
import com.acme.delivery.transfer.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.util.NoSuchElementException;


@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
@CrossOrigin("*")
public class AccountController extends BaseController<Account> {
    private final AccountService accountService;

    @Override
    public BaseService<Account> getBaseService() {
        return accountService;
    }

    @GetMapping(params = "email")
    public ResponseEntity<ApiResponse<Account>> findByEmail(@Email @RequestParam String email) {
        final Account byEmail = accountService.findByEmail(email);
        if (byEmail == null) {
            throw new NoSuchElementException("Element not found");
        }
        return ResponseEntity.ok(ApiResponse.<Account>builder().data(byEmail).build());
    }

    @GetMapping(params = "phoneNumber")
    public ResponseEntity<ApiResponse<Account>> findByPhoneNumber(@RequestParam String phoneNumber) {
        final Account byPhoneNumber = accountService.findByPhoneNumber(phoneNumber);
        if (byPhoneNumber == null) {
            throw new NoSuchElementException("Element not found");
        }
        return ResponseEntity.ok(ApiResponse.<Account>builder().data(byPhoneNumber).build());
    }
}
