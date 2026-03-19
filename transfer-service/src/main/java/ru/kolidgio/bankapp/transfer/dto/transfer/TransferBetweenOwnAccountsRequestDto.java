package ru.kolidgio.bankapp.transfer.dto.transfer;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferBetweenOwnAccountsRequestDto(
        @NotNull
        Long toAccountId,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal amount
) {
}