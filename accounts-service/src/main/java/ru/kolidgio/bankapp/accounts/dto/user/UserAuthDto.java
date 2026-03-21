package ru.kolidgio.bankapp.accounts.dto.user;

public record UserAuthDto(
        Long id,
        String login,
        String password
) {
}