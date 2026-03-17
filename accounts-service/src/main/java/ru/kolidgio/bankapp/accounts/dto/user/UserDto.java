package ru.kolidgio.bankapp.accounts.dto.user;

import java.time.LocalDate;

public record UserDto(
        Long id,
        String login,
        String firstName,
        String lastName,
        String email,
        LocalDate birthDate
) {
}

