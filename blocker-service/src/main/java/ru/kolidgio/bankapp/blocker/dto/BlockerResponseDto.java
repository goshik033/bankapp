package ru.kolidgio.bankapp.blocker.dto;

public record BlockerResponseDto(
        boolean blocked,
        String reason
) {
}