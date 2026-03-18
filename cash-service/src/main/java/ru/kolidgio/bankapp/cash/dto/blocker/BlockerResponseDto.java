package ru.kolidgio.bankapp.cash.dto.blocker;

public record BlockerResponseDto(
        boolean blocked,
        String reason
) {
}