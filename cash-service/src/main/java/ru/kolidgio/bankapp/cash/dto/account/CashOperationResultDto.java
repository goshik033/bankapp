package ru.kolidgio.bankapp.cash.dto.account;


import java.math.BigDecimal;

public record CashOperationResultDto(
        Long accountId,
        BigDecimal amount,
        String operation,
        String status
) {
}
