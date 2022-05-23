package com.katatest.accountmanagement.service.impl;

import com.katatest.accountmanagement.config.ConfigConstants;
import com.katatest.accountmanagement.domain.Account;
import com.katatest.accountmanagement.domain.Amount;
import com.katatest.accountmanagement.domain.Operation;
import com.katatest.accountmanagement.error.exception.BusinessException;
import com.katatest.accountmanagement.repository.AccountRepository;
import com.katatest.accountmanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.katatest.accountmanagement.config.ConfigConstants.DEFAULT_CURRENCY;
import static com.katatest.accountmanagement.domain.enumeration.DepositType.DEFERRED;
import static com.katatest.accountmanagement.domain.enumeration.DepositType.INSTANTANEOUS;
import static com.katatest.accountmanagement.domain.enumeration.OperationType.DEPOSIT;
import static com.katatest.accountmanagement.domain.enumeration.OperationType.WITHDRAWAL;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Boolean deposit(Long accountId, Amount amount) {
        final Account account = accountRepository.getAccountById(accountId);

        if (account == null) {
            log.error("the account {} does not exist", accountId);
            throw new BusinessException(ConfigConstants.ERROR_ACCOUNT_NOT_FOUND_KEY, "the account " + accountId + " does not exist");
        }

        if (DEFERRED.equals(amount.getType())) {
            account.addDeferred(amount.getValue());
        }

        if (INSTANTANEOUS.equals(amount.getType())) {
            account.addInstantaneously(amount.getValue());
        }

        account.addOperation(new Operation(DEPOSIT, amount.getType(), LocalDate.now(), null, amount.getValue(), DEFAULT_CURRENCY));

        return accountRepository.updateAccount(account);
    }

    @Override
    public Boolean withdraw(Long accountId, Amount amount) {
        final Account account = accountRepository.getAccountById(accountId);

        if (account == null) {
            log.error("the account {} does not exist", accountId);
            throw new BusinessException(ConfigConstants.ERROR_ACCOUNT_NOT_FOUND_KEY, "the account " + accountId + " does not exist");
        }

        if(account.getBalance() < amount.getValue()) {
            log.error("Operation rejected due to insufficient balance");
            throw new BusinessException(ConfigConstants.ERROR_INSUFFICIENT_BALANCE_KEY, "Operation rejected due to insufficient balance");
        }

        if (DEFERRED.equals(amount.getType())) {
            account.addDeferred(-amount.getValue());
        }

        if (INSTANTANEOUS.equals(amount.getType())) {
            account.addInstantaneously(-amount.getValue());
        }

        account.addOperation(new Operation(WITHDRAWAL, amount.getType(), LocalDate.now(), null, amount.getValue(), DEFAULT_CURRENCY));

        return accountRepository.updateAccount(account);
    }

    @Override
    public String printStatement(Long accountId) {
        final Account account = accountRepository.getAccountById(accountId);

        if (account == null) {
            log.error("the account {} does not exist", accountId);
            throw new BusinessException(ConfigConstants.ERROR_ACCOUNT_NOT_FOUND_KEY, "the account " + accountId + " does not exist");
        }

        final StringBuilder sb = new StringBuilder();
        sb.append("Account statement : \n")
                .append("ID : ").append(account.getId()).append("\n")
                .append("Title : ").append(account.getTitle()).append("\n")
                .append("Amount : ").append(account.getAmount()).append("\n")
                .append("Balance : ").append(account.getBalance()).append("\n")
                .append("Creation Date : ").append(account.getCreationDate()).append("\n");

        if (account.getOwners() != null && !account.getOwners().isEmpty()) {
            sb.append("Owners : \n");
            account.getOwners().forEach(owner -> sb.append(owner.getFullName()).append("\n"));
        }

        if (account.getOperationHistory() != null && !account.getOperationHistory().isEmpty()) {
            sb.append("Operation : \n");
            account.getOperationHistory().forEach(operation -> sb.append(operation.toString()).append("\n"));
        }

        return sb.toString();
    }
}
