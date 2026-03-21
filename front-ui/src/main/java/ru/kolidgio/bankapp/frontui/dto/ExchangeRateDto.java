package ru.kolidgio.bankapp.frontui.dto;

import java.math.BigDecimal;

public record ExchangeRateDto(
        String currency,
        BigDecimal buyRate,
        BigDecimal sellRate
) {
}