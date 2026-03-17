package ru.kolidgio.bankapp.cash.dto.account;


import ru.kolidgio.bankapp.cash.entity.Currency;

import java.math.BigDecimal;

public record AccountDto(
        Long id,
        Currency currency,
        BigDecimal balance,
        Long userId
) {
}
