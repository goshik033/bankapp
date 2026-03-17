package ru.kolidgio.bankapp.cash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolidgio.bankapp.cash.client.AccountsClient;
import ru.kolidgio.bankapp.cash.dto.account.AccountDto;
import ru.kolidgio.bankapp.cash.dto.account.CashOperationDto;
import ru.kolidgio.bankapp.cash.dto.account.CashOperationResultDto;

@Service
@RequiredArgsConstructor
public class CashService {

    private final AccountsClient accountsClient;

    public CashOperationResultDto deposit(Long userId, Long accountId, CashOperationDto dto) {
        AccountDto accountDto = accountsClient.deposit(userId, accountId, dto.amount());

        return new CashOperationResultDto(
                accountDto.id(),
                dto.amount(),
                "DEPOSIT",
                "SUCCESS"
        );
    }

    public CashOperationResultDto withdraw(Long userId, Long accountId, CashOperationDto dto) {
        AccountDto accountDto = accountsClient.withdraw(userId, accountId, dto.amount());

        return new CashOperationResultDto(
                accountDto.id(),
                dto.amount(),
                "WITHDRAW",
                "SUCCESS"
        );
    }
}