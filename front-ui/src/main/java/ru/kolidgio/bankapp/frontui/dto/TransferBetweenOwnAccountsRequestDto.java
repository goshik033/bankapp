package ru.kolidgio.bankapp.frontui.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferBetweenOwnAccountsRequestDto(
        @NotNull
        Long fromAccountId,

        @NotNull
        Long toAccountId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount
) {
}