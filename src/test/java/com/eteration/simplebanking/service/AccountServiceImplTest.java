package com.eteration.simplebanking.service;

import com.eteration.simplebanking.dto.AccountCreateRequest;
import com.eteration.simplebanking.dto.AccountInfoResponse;
import com.eteration.simplebanking.dto.AccountResponse;
import com.eteration.simplebanking.entity.Account;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InvalidTransactionException;
import com.eteration.simplebanking.mapper.AccountMapper;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.service.impl.AccountServiceImpl;
import com.eteration.simplebanking.service.impl.DepositTransactionServiceImpl;
import com.eteration.simplebanking.service.impl.WithdrawalTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private WithdrawalTransactionServiceImpl withdrawalTransactionService;

    @Mock
    private DepositTransactionServiceImpl depositTransactionService;

    @Mock
    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void withdraw_ShouldReturnOKResponse_WhenValidRequest() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(withdrawalTransactionService.create(account, 100.0)).thenReturn("APPROVAL123");

        // Act
        AccountResponse response = accountService.withdraw(1L, 100.0);

        // Assert
        assertNotNull(response);
        assertEquals("OK", response.getStatus());
        assertEquals("APPROVAL123", response.getApprovalCode());

        verify(accountRepository, times(1)).save(account);
        verify(withdrawalTransactionService, times(1)).create(account, 100.0);
    }

    @Test
    void withdraw_ShouldThrowInvalidTransactionException_WhenAccountNotFound() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class,
                () -> accountService.withdraw(1L, 100.0));
        assertNotNull(exception);

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void deposit_ShouldReturnOKResponse_WhenValidRequest() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(depositTransactionService.create(account, 200.0)).thenReturn("APPROVAL456");

        // Act
        AccountResponse response = accountService.deposit(1L, 200.0);

        // Assert
        assertNotNull(response);
        assertEquals("OK", response.getStatus());
        assertEquals("APPROVAL456", response.getApprovalCode());

        verify(accountRepository, times(1)).save(account);
        verify(depositTransactionService, times(1)).create(account, 200.0);
    }

    @Test
    void get_ShouldReturnAccountInfoResponse_WhenAccountExists() {
        // Arrange
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        AccountInfoResponse accountInfoResponse = new AccountInfoResponse();
        accountInfoResponse.setBalance(1000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountMapper.toAccountInfoResponse(account)).thenReturn(accountInfoResponse);

        // Act
        AccountInfoResponse response = accountService.get(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1000.0, response.getBalance());

        verify(accountRepository, times(1)).findById(1L);
        verify(accountMapper, times(1)).toAccountInfoResponse(account);
    }

    @Test
    void get_ShouldThrowAccountNotFoundException_WhenAccountDoesNotExist() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class,
                () -> accountService.get(1L));
        assertNotNull(exception);

        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void create_ShouldSaveAccount_WhenValidRequest() {
        // Arrange
        AccountCreateRequest request = new AccountCreateRequest();
        request.setOwner("John Doe");

        Account account = new Account();
        account.setOwner("John Doe");

        when(accountMapper.convertToAccount(request)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        // Act
        accountService.create(request);

        // Assert
        verify(accountMapper, times(1)).convertToAccount(request);
        verify(accountRepository, times(1)).save(account);
    }
}

