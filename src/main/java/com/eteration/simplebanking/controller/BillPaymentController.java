package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.AccountResponse;
import com.eteration.simplebanking.dto.PayRequest;
import com.eteration.simplebanking.service.BillPaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bill-payment/v1")
public class BillPaymentController {

    private final BillPaymentTransactionService billPaymentTransactionService;

    @PostMapping("/pay")
    public ResponseEntity<AccountResponse> pay(@RequestBody PayRequest request) {
        return ResponseEntity.ok(billPaymentTransactionService.pay(request));
    }
}
