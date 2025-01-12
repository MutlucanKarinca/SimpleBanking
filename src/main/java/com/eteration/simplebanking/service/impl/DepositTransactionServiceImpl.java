package com.eteration.simplebanking.service.impl;

import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.DepositTransaction;
import com.eteration.simplebanking.enums.EnumTransactionType;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.service.FundTransferTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DepositTransactionServiceImpl implements FundTransferTransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public String create(Account account, Double amount) {
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setAccount(account);
        transaction.setType(EnumTransactionType.DEPOSIT);
        transactionRepository.save(transaction);
        return transaction.getApprovalCode();
    }

}
