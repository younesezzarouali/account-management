package com.katatest.accountmanagement.service;

import com.katatest.accountmanagement.domain.Amount;

public interface AccountService {
    Boolean deposit(Long accountId, Amount amount);

    Boolean withdraw(Long accountId, Amount amount);

    String printStatement(Long accountId);
}
