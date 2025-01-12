package com.eteration.simplebanking.service;

import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.WithdrawalTransaction;
import com.eteration.simplebanking.enums.EnumTransactionType;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.service.impl.WithdrawalTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WithdrawalTransactionServiceImplTest {

    @InjectMocks
    private WithdrawalTransactionServiceImpl withdrawalTransactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldSaveWithdrawalTransactionAndReturnApprovalCode() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        WithdrawalTransaction transaction = new WithdrawalTransaction();
        transaction.setAmount(100.0);
        transaction.setAccount(account);
        transaction.setType(EnumTransactionType.WITHDRAWAL);
        transaction.setApprovalCode("APPROVAL123");

        when(transactionRepository.save(any(WithdrawalTransaction.class))).thenAnswer(invocation -> {
            WithdrawalTransaction savedTransaction = invocation.getArgument(0);
            savedTransaction.setApprovalCode("APPROVAL123"); // Simulate database-generated approval code
            return savedTransaction;
        });

        // Act
        String approvalCode = withdrawalTransactionService.create(account, 100.0);

        // Assert
        assertNotNull(approvalCode);
        assertEquals("APPROVAL123", approvalCode);

        verify(transactionRepository, times(1)).save(any(WithdrawalTransaction.class));
    }

    @Test
    void create_ShouldThrowException_WhenTransactionRepositoryFails() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        when(transactionRepository.save(any(WithdrawalTransaction.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> withdrawalTransactionService.create(account, 100.0));
        assertEquals("Database error", exception.getMessage());

        verify(transactionRepository, times(1)).save(any(WithdrawalTransaction.class));
    }
}
