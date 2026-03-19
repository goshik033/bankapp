package ru.kolidgio.bankapp.exchangegenerator.dto;

import ru.kolidgio.bankapp.exchangegenerator.entity.Currency;

import java.math.BigDecimal;

public record UpdateExchangeRateDto(
        Currency currency,
        BigDecimal buyRate,
        BigDecimal sellRate
) {
}