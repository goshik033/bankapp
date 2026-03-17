package ru.kolidgio.bankapp.accounts.dto.user;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateUserDto(
        @NotBlank @Size(min = 3, max = 50) String login,
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Size(min = 8, max = 100) String password,
        @NotBlank @Email @Size(max = 255) String email,
        @NotNull @Past LocalDate birthDate
) {
}
