package com.katatest.accountmanagement.domain;

import com.katatest.accountmanagement.config.ConfigConstants;
import com.katatest.accountmanagement.domain.enumeration.DepositType;
import com.katatest.accountmanagement.domain.enumeration.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Currency;

import static java.time.format.DateTimeFormatter.ofPattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    @NotNull
    private OperationType type;

    private DepositType depositType;

    @NotNull
    private LocalDate executionDate;

    private Client executor;

    private Double amount;

    private Currency currency = ConfigConstants.DEFAULT_CURRENCY;

    @Override
    public String toString() {
        return this.type + " " + amount + " " + currency.getCurrencyCode() + " at " + executionDate.format(ofPattern("dd/MM/yyyy"))
                + " by " + executor.getFullName();
    }
}
