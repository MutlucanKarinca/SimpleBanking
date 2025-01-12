package com.eteration.simplebanking.service.impl;

import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.WithdrawalTransaction;
import com.eteration.simplebanking.enums.EnumTransactionType;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.service.FundTransferTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WithdrawalTransactionServiceImpl implements FundTransferTransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public String create(Account account, Double amount) {
        WithdrawalTransaction transaction = new WithdrawalTransaction();
        transaction.setAmount(amount);
        transaction.setAccount(account);
        transaction.setType(EnumTransactionType.WITHDRAWAL);
        transactionRepository.save(transaction);
        return transaction.getApprovalCode();
    }

}
