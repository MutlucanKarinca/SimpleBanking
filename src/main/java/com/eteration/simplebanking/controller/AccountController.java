package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.AccountCreateRequest;
import com.eteration.simplebanking.dto.AccountInfoResponse;
import com.eteration.simplebanking.dto.AccountResponse;
import com.eteration.simplebanking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/v1")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<AccountResponse> deposit(@RequestParam Double amount, @PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.deposit(accountId, amount));
    }

    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<AccountResponse> withdraw(@RequestParam Double amount, @PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.withdraw(accountId, amount));
    }

    @GetMapping({"/{accountId}"})
    public ResponseEntity<AccountInfoResponse> get(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.get(accountId));
    }

    @PostMapping
    public void create(@RequestBody AccountCreateRequest request) {
        accountService.create(request);
        ResponseEntity.ok().build();
    }

}