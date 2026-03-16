package ru.kolidgio.bankapp.accounts.service.errors;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
