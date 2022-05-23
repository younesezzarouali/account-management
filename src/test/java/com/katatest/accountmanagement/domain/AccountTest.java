package com.katatest.accountmanagement.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AccountTest {

    @Test
    void test_add_positif_amount_Deferred() {
        // Prepare
        final Account account = new Account();
        account.setAmount(11000d);
        account.setBalance(11000d);

        // ACT
        account.addDeferred(1000d);

        // Assert
        assertEquals(12000d, account.getAmount());
        assertEquals(11000d, account.getBalance());
    }

    @Test
    void test_add_positif_amount_Instantaneously() {
        // Prepare
        final Account account = new Account();
        account.setAmount(11000d);
        account.setBalance(11000d);

        // ACT
        account.addInstantaneously(1000d);

        // Assert
        assertEquals(12000d, account.getAmount());
        assertEquals(12000d, account.getBalance());
    }

    @Test
    void test_add_negative_amount_Deferred() {
        // Prepare
        final Account account = new Account();
        account.setAmount(11000d);
        account.setBalance(11000d);

        // ACT
        account.addDeferred(-1000d);

        // Assert
        assertEquals(10000d, account.getAmount());
        assertEquals(11000d, account.getBalance());
    }

    @Test
    void test_add_negative_amount_Instantaneously() {
        // Prepare
        final Account account = new Account();
        account.setAmount(11000d);
        account.setBalance(11000d);

        // ACT
        account.addInstantaneously(-1000d);

        // Assert
        assertEquals(10000d, account.getAmount());
        assertEquals(10000d, account.getBalance());
    }
}