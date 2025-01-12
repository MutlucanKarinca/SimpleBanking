package com.eteration.simplebanking.service;

import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.DepositTransaction;
import com.eteration.simplebanking.enums.EnumTransactionType;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.service.impl.DepositTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepositTransactionServiceImplTest {

    @InjectMocks
    private DepositTransactionServiceImpl depositTransactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldSaveDepositTransactionAndReturnApprovalCode() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(200.0);
        transaction.setAccount(account);
        transaction.setType(EnumTransactionType.DEPOSIT);
        transaction.setApprovalCode("APPROVAL456");

        when(transactionRepository.save(any(DepositTransaction.class))).thenAnswer(invocation -> {
            DepositTransaction savedTransaction = invocation.getArgument(0);
            savedTransaction.setApprovalCode("APPROVAL456"); // Simulate database-generated approval code
            return savedTransaction;
        });

        // Act
        String approvalCode = depositTransactionService.create(account, 200.0);

        // Assert
        assertNotNull(approvalCode);
        assertEquals("APPROVAL456", approvalCode);

        verify(transactionRepository, times(1)).save(any(DepositTransaction.class));
    }

    @Test
    void create_ShouldThrowException_WhenTransactionRepositoryFails() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        when(transactionRepository.save(any(DepositTransaction.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> depositTransactionService.create(account, 200.0));
        assertEquals("Database error", exception.getMessage());

        verify(transactionRepository, times(1)).save(any(DepositTransaction.class));
    }
}
