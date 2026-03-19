package ru.kolidgio.bankapp.exchange.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import ru.kolidgio.bankapp.exchange.entity.Currency;

import java.math.BigDecimal;

public record ConvertRequestDto(
        @NotNull
        Currency fromCurrency,

        @NotNull
        Currency toCurrency,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal amount
) {
}