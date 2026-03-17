package ru.kolidgio.bankapp.accounts.dto.account;

import ru.kolidgio.bankapp.accounts.entity.Currency;

import java.math.BigDecimal;

public record AccountDto(
        Long id,
        Currency currency,
        BigDecimal balance,
        Long userId
) {
}
