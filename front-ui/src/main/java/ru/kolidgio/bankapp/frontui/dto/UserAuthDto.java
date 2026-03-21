package ru.kolidgio.bankapp.frontui.dto;

public record UserAuthDto(
        Long id,
        String login,
        String password
) {
}