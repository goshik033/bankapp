package ru.kolidgio.bankapp.transfer.dto.notification;

import java.math.BigDecimal;

public record NotificationRequestDto(
        Long userId,
        Long accountId,
        String operation,
        BigDecimal amount,
        String status
) {
}