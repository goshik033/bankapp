package ru.kolidgio.bankapp.frontui.dto;

import java.math.BigDecimal;

public record AccountDto(
        Long id,
        String currency,
        BigDecimal balance,
        Long userId
) {
}