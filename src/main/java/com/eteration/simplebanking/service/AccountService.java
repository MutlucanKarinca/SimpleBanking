package com.eteration.simplebanking.service;


import com.eteration.simplebanking.dto.AccountCreateRequest;
import com.eteration.simplebanking.dto.AccountInfoResponse;
import com.eteration.simplebanking.dto.AccountResponse;
import com.eteration.simplebanking.entity.Account;

public interface AccountService {

    AccountResponse withdraw(Long accountId, Double amount);

    AccountResponse deposit(Long accountId, Double amount);

    void create(AccountCreateRequest request);

    AccountInfoResponse get(Long accountId);

    Account increaseBalance(Long accountId, Double amount);

    Account decreaseBalance(Long accountId, Double amount);

    Account validateAccountForTransaction(Long accountId, Double amount, boolean checkSufficientBalance);
}
