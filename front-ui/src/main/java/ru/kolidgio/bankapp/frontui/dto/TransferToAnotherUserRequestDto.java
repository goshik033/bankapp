package ru.kolidgio.bankapp.frontui.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferToAnotherUserRequestDto(
        @NotNull
        Long fromAccountId,

        @NotBlank
        String toUserLogin,

        @NotNull
        Long toAccountId,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount
) {
}