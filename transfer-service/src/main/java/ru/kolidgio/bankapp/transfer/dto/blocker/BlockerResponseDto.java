package ru.kolidgio.bankapp.transfer.dto.blocker;

public record BlockerResponseDto(
        boolean blocked,
        String reason
) {
}