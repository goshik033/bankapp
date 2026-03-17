package ru.kolidgio.bankapp.accounts.dto.account;

import jakarta.validation.constraints.NotNull;
import ru.kolidgio.bankapp.accounts.entity.Currency;

public record CreateAccountDto(
        @NotNull Currency currency
) {
}
