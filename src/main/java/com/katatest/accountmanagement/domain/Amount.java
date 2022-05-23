package com.katatest.accountmanagement.domain;

import com.katatest.accountmanagement.config.ConfigConstants;
import com.katatest.accountmanagement.domain.enumeration.DepositType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Currency;

@Getter
@Setter
public class Amount implements Serializable {
    @NonNull
    @Min(value = 0L, message = "The value must be positive")
    private Double value;

    @NotNull
    private DepositType type;

    private Currency currency = ConfigConstants.DEFAULT_CURRENCY;
}
