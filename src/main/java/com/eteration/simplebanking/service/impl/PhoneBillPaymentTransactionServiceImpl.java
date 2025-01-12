package com.eteration.simplebanking.service.impl;

import com.eteration.simplebanking.dto.AccountResponse;
import com.eteration.simplebanking.dto.PayRequest;
import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.BillPaymentTransaction;
import com.eteration.simplebanking.enums.EnumTransactionType;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.exception.InvalidTransactionException;
import com.eteration.simplebanking.exception.RequiredFieldException;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.service.AccountService;
import com.eteration.simplebanking.service.BillPaymentTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PhoneBillPaymentTransactionServiceImpl implements BillPaymentTransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Override
    public AccountResponse pay(PayRequest request) {
        AccountResponse response = new AccountResponse();
        try {
            Account account = accountService.validateAccountForTransaction(request.getAccountId(), request.getAmount(), true);
            validate(request, account);

            BillPaymentTransaction transaction = new BillPaymentTransaction();
            transaction.setAccount(account);
            transaction.setAmount(request.getAmount());
            transaction.setPayee(request.getPayee());
            transaction.setType(EnumTransactionType.BILL_PAYMENT);
            transaction.setSubscriberNumber(request.getSubscriber());

            accountService.decreaseBalance(account.getId(), request.getAmount());
            transactionRepository.save(transaction);

            response.setStatus("OK");
            response.setApprovalCode(transaction.getApprovalCode());
        } catch (InsufficientBalanceException e) {
            response.setStatus("FAIL");
            log.error("Insufficient balance: ", e.getMessage(), e);
            throw new InsufficientBalanceException();
        } catch (RequiredFieldException e) {
            response.setStatus("FAIL");
            log.error("Required field missing: ", e.getMessage(), e);
            throw new RequiredFieldException(e.getMessage());
        } catch (Exception e) {
            response.setStatus("FAIL");
            log.error("An error occured: ", e.getMessage(), e);
            throw new InvalidTransactionException("An error occurred while paying transaction.");
        }
        return response;
    }

    private void validate(PayRequest request, Account account) {
        if (account == null) {
            throw new RequiredFieldException("Account cannot be null.");
        }
        if (request.getAmount() <= 0) {
            throw new RequiredFieldException("Payment amount must be greater than zero.");
        }
        if (account.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException();
        }
        if (!StringUtils.hasText(request.getPayee())) {
            throw new RequiredFieldException("Payee is missing.");
        }
        if (!StringUtils.hasText(request.getSubscriber())) {
            throw new RequiredFieldException("Subscriber number is missing.");
        }
    }
}
