package ru.kolidgio.bankapp.accounts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordDto(
        @NotBlank @Size(min = 8, max = 100) String password

) {
}
