package ru.kolidgio.bankapp.accounts.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(
        @NotBlank String login,
        @NotBlank String password
) {
}
