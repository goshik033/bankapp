package ru.kolidgio.bankapp.frontui.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountDto(
        @NotBlank
        String currency
) {
}