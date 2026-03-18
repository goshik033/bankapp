package ru.kolidgio.bankapp.notifications.dto;

import java.math.BigDecimal;

public record NotificationRequestDto(
        Long userId,
        Long accountId,
        String operation,
        BigDecimal amount,
        String status
) {
}