package ru.kolidgio.bankapp.transfer.dto.account;



import java.math.BigDecimal;

public record AccountDto(
        Long id,
        String currency,
        BigDecimal balance,
        Long userId
) {
}
