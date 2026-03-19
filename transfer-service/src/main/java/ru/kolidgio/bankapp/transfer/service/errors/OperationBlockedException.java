package ru.kolidgio.bankapp.transfer.service.errors;

public class OperationBlockedException extends RuntimeException {
    public OperationBlockedException(String message) {
        super(message);
    }
}