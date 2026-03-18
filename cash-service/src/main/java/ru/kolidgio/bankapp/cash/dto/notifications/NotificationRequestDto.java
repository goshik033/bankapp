package ru.kolidgio.bankapp.cash.dto.notifications;

import java.math.BigDecimal;

public record NotificationRequestDto(
        Long userId,
        Long accountId,
        String operation,
        BigDecimal amount,
        String status
) {
}