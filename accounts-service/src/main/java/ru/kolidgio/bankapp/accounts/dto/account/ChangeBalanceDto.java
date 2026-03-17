package ru.kolidgio.bankapp.accounts.dto.account;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ChangeBalanceDto(
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount
) {
}
