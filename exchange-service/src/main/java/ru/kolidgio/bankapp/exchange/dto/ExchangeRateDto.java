package ru.kolidgio.bankapp.exchange.dto;

import ru.kolidgio.bankapp.exchange.entity.Currency;

import java.math.BigDecimal;

public record ExchangeRateDto(
        Currency currency,
        BigDecimal buyRate,
        BigDecimal sellRate
) {
}