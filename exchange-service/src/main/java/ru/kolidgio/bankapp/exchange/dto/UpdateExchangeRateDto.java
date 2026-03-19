package ru.kolidgio.bankapp.exchange.dto;

import jakarta.validation.constraints.NotNull;
import ru.kolidgio.bankapp.exchange.entity.Currency;

import java.math.BigDecimal;

public record UpdateExchangeRateDto(
        @NotNull
        Currency currency,

        @NotNull
        BigDecimal buyRate,

        @NotNull
        BigDecimal sellRate
) {
}