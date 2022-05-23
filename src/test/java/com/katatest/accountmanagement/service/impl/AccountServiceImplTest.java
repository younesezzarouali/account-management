package com.katatest.accountmanagement.service.impl;

import com.katatest.accountmanagement.domain.enumeration.DepositType;
import com.katatest.accountmanagement.domain.Account;
import com.katatest.accountmanagement.domain.Amount;
import com.katatest.accountmanagement.error.exception.BusinessException;
import com.katatest.accountmanagement.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    void testDepositMoney_casDeferrefOperation() {
        // Prepare
        final Amount amountMock = mock(Amount.class);
        final Account accountMock = mock(Account.class);

        when(amountMock.getType()).thenReturn(DepositType.DEFERRED);
        when(amountMock.getValue()).thenReturn(1000d);
        when(accountRepository.getAccountById(1001L)).thenReturn(accountMock);
        when(accountRepository.updateAccount(accountMock)).thenReturn(true);

        // Act
        final Boolean result = accountService.deposit(1001L, amountMock);

        // Assert
        verify(accountMock, times(1)).addDeferred(1000d);
        verify(accountMock, times(0)).addInstantaneously(anyDouble());
        verify(accountMock, times(1)).addOperation(any());
        Assertions.assertTrue(result);
    }

    @Test
    void testDepositMoney_casInstantaneousOperation() {
        // Prepare
        final Amount amountMock = mock(Amount.class);
        final Account accountMock = mock(Account.class);

        when(amountMock.getType()).thenReturn(DepositType.INSTANTANEOUS);
        when(amountMock.getValue()).thenReturn(1000d);
        when(accountRepository.getAccountById(1001L)).thenReturn(accountMock);
        when(accountRepository.updateAccount(accountMock)).thenReturn(true);

        // Act
        final Boolean result = accountService.deposit(1001L, amountMock);

        // Assert
        verify(accountMock, times(0)).addDeferred(anyDouble());
        verify(accountMock, times(1)).addInstantaneously(1000d);
        verify(accountMock, times(1)).addOperation(any());
        Assertions.assertTrue(result);
    }

    @Test
    void testDepositMoney_casAccountNotFound() {
        // Prepare
        final Amount amountMock = mock(Amount.class);
        final Account accountMock = mock(Account.class);

        when(amountMock.getType()).thenReturn(DepositType.INSTANTANEOUS);
        when(amountMock.getValue()).thenReturn(1000d);
        when(accountRepository.getAccountById(1001L)).thenReturn(null);

        // Act
        Assertions.assertThrows(BusinessException.class, () -> accountService.deposit(1001L, amountMock));

        // Assert
        verify(accountMock, times(0)).addDeferred(anyDouble());
        verify(accountMock, times(0)).addInstantaneously(1000d);
        verify(accountMock, times(0)).addOperation(any());
    }

    @Test
    void testWithdrawalMoney_casDeferrefOperation() {
        // Prepare
        final Amount amountMock = mock(Amount.class);
        final Account accountMock = mock(Account.class);

        when(amountMock.getType()).thenReturn(DepositType.DEFERRED);
        when(amountMock.getValue()).thenReturn(1000d);
        when(accountMock.getBalance()).thenReturn(10000d);
        when(accountRepository.getAccountById(1001L)).thenReturn(accountMock);
        when(accountRepository.updateAccount(accountMock)).thenReturn(true);

        // Act
        final Boolean result = accountService.withdraw(1001L, amountMock);

        // Assert
        verify(accountMock, times(1)).addDeferred(-1000d);
        verify(accountMock, times(0)).addInstantaneously(anyDouble());
        verify(accountMock, times(1)).addOperation(any());
        Assertions.assertTrue(result);
    }

    @Test
    void testWithdrawalMoney_casInsufficientBalance() {
        // Prepare
        final Amount amountMock = mock(Amount.class);
        final Account accountMock = mock(Account.class);

        when(amountMock.getType()).thenReturn(DepositType.DEFERRED);
        when(amountMock.getValue()).thenReturn(110000d);
        when(accountMock.getBalance()).thenReturn(10000d);
        when(accountRepository.getAccountById(1001L)).thenReturn(accountMock);
        when(accountRepository.updateAccount(accountMock)).thenReturn(true);

        // Act
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> accountService.withdraw(1001L, amountMock));
        Assertions.assertEquals("insufficientBalance", businessException.getErrorKey());
    }
}