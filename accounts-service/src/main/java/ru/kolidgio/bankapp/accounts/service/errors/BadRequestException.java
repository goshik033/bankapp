package ru.kolidgio.bankapp.accounts.service.errors;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
