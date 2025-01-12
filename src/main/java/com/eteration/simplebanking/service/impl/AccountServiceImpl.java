package com.eteration.simplebanking.service.impl;

import com.eteration.simplebanking.dto.AccountCreateRequest;
import com.eteration.simplebanking.dto.AccountInfoResponse;
import com.eteration.simplebanking.dto.AccountResponse;
import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.exception.InvalidTransactionException;
import com.eteration.simplebanking.mapper.AccountMapper;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final WithdrawalTransactionServiceImpl withdrawalTransactionService;
    private final DepositTransactionServiceImpl depositTransactionService;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponse withdraw(Long accountId, Double amount) {
        AccountResponse response = new AccountResponse();
        try {

            Account account = decreaseBalance(accountId, amount);
            String approvalCode = withdrawalTransactionService.create(account, amount);

            response.setStatus("OK");
            response.setApprovalCode(approvalCode);
        } catch (Exception e) {
            response.setStatus("FAIL");
            log.error("An error occurred: ", e.getMessage(), e);
            throw new InvalidTransactionException("An error occurred while withdrawing transaction.");
        }
        return response;
    }

    @Override
    public AccountResponse deposit(Long accountId, Double amount) {
        AccountResponse response = new AccountResponse();
        try {
            Account account = increaseBalance(accountId, amount);
            String approvalCode = depositTransactionService.create(account, amount);

            response.setStatus("OK");
            response.setApprovalCode(approvalCode);
        } catch (Exception e) {
            response.setStatus("FAIL");
            log.error("An error occurred: ", e.getMessage(), e);
            throw new InvalidTransactionException("An error occurred while deposit transaction.");
        }
        return response;
    }

    @Override
    public void create(AccountCreateRequest request) {
        Account account = accountMapper.convertToAccount(request);
        accountRepository.save(account);
        log.info("Account created: {}", account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountInfoResponse get(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        return accountMapper.toAccountInfoResponse(account);
    }

    @Override
    public Account validateAccountForTransaction(Long accountId, Double amount, boolean checkSufficientBalance) {
        if (amount == null || amount <= 0) {
            throw new InvalidTransactionException("Amount must be greater than zero.");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        if (checkSufficientBalance && account.getBalance() < amount) {
            throw new InsufficientBalanceException();
        }

        return account;
    }

    @Override
    public Account increaseBalance(Long accountId, Double amount) {
        Account account = validateAccountForTransaction(accountId, amount, false);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account decreaseBalance(Long accountId, Double amount) {
        Account account = validateAccountForTransaction(accountId, amount, true);
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        return account;
    }
}
