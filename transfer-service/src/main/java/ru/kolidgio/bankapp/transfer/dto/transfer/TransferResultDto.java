package ru.kolidgio.bankapp.transfer.dto.transfer;

import java.math.BigDecimal;

public record TransferResultDto(
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount,
        String status
) {
}
