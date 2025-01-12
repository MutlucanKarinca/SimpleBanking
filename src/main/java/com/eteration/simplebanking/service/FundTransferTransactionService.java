package com.eteration.simplebanking.service;

import com.eteration.simplebanking.entity.Account;

public interface FundTransferTransactionService extends TransactionService {
    String create(Account account, Double amount);
}
