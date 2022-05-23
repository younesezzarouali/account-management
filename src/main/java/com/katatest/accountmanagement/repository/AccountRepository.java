package com.katatest.accountmanagement.repository;

import com.katatest.accountmanagement.domain.Account;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Log4j2
@Service
public class AccountRepository {
    public Account getAccountById(Long id) {
        log.info("retrieving account {} info from database...", id);

        // Account not found (matter of testing);
        if (id < 0) {
            return null;
        }

        final Account account = new Account();
        account.setId(1001L);
        account.setAmount(11000d);
        account.setBalance(11000d);
        account.setCreationDate(LocalDate.of(2017, 5, 22));

        return account;
    }

    public Boolean updateAccount(Account account) {
        log.info("updating the account {}", account.getId());
        return true;
    }
}
