package ru.kolidgio.bankapp.cash.service.errors;

public class OperationBlockedException extends RuntimeException {
    public OperationBlockedException(String message) {
        super(message);
    }
}