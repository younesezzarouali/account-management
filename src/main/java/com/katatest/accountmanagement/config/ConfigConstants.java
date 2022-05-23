package com.katatest.accountmanagement.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Currency;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigConstants {
    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");
    public static final String ERROR_ACCOUNT_NOT_FOUND_KEY = "accountNotFound";
    public static final String ERROR_INSUFFICIENT_BALANCE_KEY = "insufficientBalance";
}
