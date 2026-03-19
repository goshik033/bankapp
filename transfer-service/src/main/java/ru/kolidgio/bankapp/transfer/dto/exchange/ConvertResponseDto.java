package ru.kolidgio.bankapp.transfer.dto.exchange;

import java.math.BigDecimal;

public record ConvertResponseDto(
        String fromCurrency,
        String toCurrency,
        BigDecimal sourceAmount,
        BigDecimal convertedAmount
) {
}