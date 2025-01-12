package com.eteration.simplebanking.service;

import com.eteration.simplebanking.dto.AccountResponse;
import com.eteration.simplebanking.dto.PayRequest;
import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.entity.BillPaymentTransaction;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.exception.InvalidTransactionException;
import com.eteration.simplebanking.exception.RequiredFieldException;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.service.impl.PhoneBillPaymentTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PhoneBillPaymentTransactionServiceImplTest {

    @InjectMocks
    private PhoneBillPaymentTransactionServiceImpl phoneBillPaymentTransactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void pay_ShouldReturnOKResponse_WhenValidRequest() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        PayRequest request = new PayRequest();
        request.setAccountId(1L);
        request.setAmount(100.0);
        request.setPayee("Phone Company");
        request.setSubscriber("1234567890");

        BillPaymentTransaction transaction = new BillPaymentTransaction();
        transaction.setAccount(account);

        when(accountService.validateAccountForTransaction(1L, 100.0, true)).thenReturn(account);
        when(accountService.decreaseBalance(1L, 100.0)).thenReturn(account);
        when(transactionRepository.save(any(BillPaymentTransaction.class))).thenReturn(transaction);

        // Act
        AccountResponse response = phoneBillPaymentTransactionService.pay(request);

        // Assert
        assertNotNull(response);
        assertEquals("OK", response.getStatus());

        verify(accountService, times(1)).validateAccountForTransaction(1L, 100.0, true);
        verify(accountService, times(1)).decreaseBalance(1L, 100.0);
        verify(transactionRepository, times(1)).save(any(BillPaymentTransaction.class));
    }

    @Test
    void pay_ShouldThrowRequiredFieldException_WhenPayeeIsMissing() {
        // Arrange
        PayRequest request = new PayRequest();
        request.setAccountId(1L);
        request.setAmount(100.0);
        request.setSubscriber("1234567890");

        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        when(accountService.validateAccountForTransaction(1L, 100.0, true)).thenReturn(account);

        // Act & Assert
        RequiredFieldException exception = assertThrows(RequiredFieldException.class,
                () -> phoneBillPaymentTransactionService.pay(request));
        assertEquals("Payee is missing.", exception.getMessage());

        verify(accountService, times(1)).validateAccountForTransaction(1L, 100.0, true);
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void pay_ShouldThrowInsufficientBalanceException_WhenBalanceIsNotEnough() {
        // Arrange
        PayRequest request = new PayRequest();
        request.setAccountId(1L);
        request.setAmount(2000.0);
        request.setPayee("Phone Company");
        request.setSubscriber("1234567890");

        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        when(accountService.validateAccountForTransaction(1L, 2000.0, true)).thenReturn(account);

        // Act & Assert
        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class,
                () -> phoneBillPaymentTransactionService.pay(request));
        assertNotNull(exception);

        verify(accountService, times(1)).validateAccountForTransaction(1L, 2000.0, true);
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void pay_ShouldThrowInvalidTransactionException_WhenUnhandledExceptionOccurs() {
        // Arrange
        PayRequest request = new PayRequest();
        request.setAccountId(1L);
        request.setAmount(100.0);
        request.setPayee("Phone Company");
        request.setSubscriber("1234567890");

        when(accountService.validateAccountForTransaction(anyLong(), anyDouble(), anyBoolean()))
                .thenThrow(new RuntimeException("Unexpected Error"));

        // Act & Assert
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class,
                () -> phoneBillPaymentTransactionService.pay(request));
        assertEquals("An error occurred while paying transaction.", exception.getMessage());

        verify(accountService, times(1)).validateAccountForTransaction(anyLong(), anyDouble(), anyBoolean());
        verifyNoInteractions(transactionRepository);
    }
}

