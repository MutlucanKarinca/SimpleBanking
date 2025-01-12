package com.eteration.simplebanking.service;

import com.eteration.simplebanking.dto.AccountResponse;
import com.eteration.simplebanking.dto.PayRequest;

public interface BillPaymentTransactionService extends TransactionService {
    AccountResponse pay(PayRequest request);

}
