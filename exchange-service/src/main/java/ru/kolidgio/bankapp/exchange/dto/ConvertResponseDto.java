package ru.kolidgio.bankapp.exchange.dto;

import ru.kolidgio.bankapp.exchange.entity.Currency;

import java.math.BigDecimal;

public record ConvertResponseDto(
        Currency fromCurrency,
        Currency toCurrency,
        BigDecimal sourceAmount,
        BigDecimal convertedAmount
) {
}