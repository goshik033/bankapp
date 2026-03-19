package ru.kolidgio.bankapp.transfer.dto.exchange;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ConvertRequestDto(
        @NotNull
        String fromCurrency,

        @NotNull
        String toCurrency,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal amount
) {
}