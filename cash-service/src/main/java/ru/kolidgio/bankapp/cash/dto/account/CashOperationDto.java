package ru.kolidgio.bankapp.cash.dto.account;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CashOperationDto(
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount
) {
}
