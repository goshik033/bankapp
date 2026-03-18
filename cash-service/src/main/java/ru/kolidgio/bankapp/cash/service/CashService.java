package ru.kolidgio.bankapp.cash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kolidgio.bankapp.cash.client.AccountsClient;
import ru.kolidgio.bankapp.cash.client.BlockerClient;
import ru.kolidgio.bankapp.cash.dto.account.AccountDto;
import ru.kolidgio.bankapp.cash.dto.account.CashOperationDto;
import ru.kolidgio.bankapp.cash.dto.account.CashOperationResultDto;
import ru.kolidgio.bankapp.cash.dto.blocker.BlockerResponseDto;
import ru.kolidgio.bankapp.cash.service.errors.OperationBlockedException;

@Service
@RequiredArgsConstructor
public class CashService {

    private final AccountsClient accountsClient;
    private final BlockerClient blockerClient;

    public CashOperationResultDto deposit(Long userId, Long accountId, CashOperationDto dto) {
        BlockerResponseDto blockerResponse = blockerClient.check(dto.amount());
        if (blockerResponse.blocked()) {
            throw new OperationBlockedException(blockerResponse.reason());
        }

        AccountDto accountDto = accountsClient.deposit(userId, accountId, dto.amount());

        return new CashOperationResultDto(
                accountDto.id(),
                dto.amount(),
                "DEPOSIT",
                "SUCCESS"
        );
    }

    public CashOperationResultDto withdraw(Long userId, Long accountId, CashOperationDto dto) {
        BlockerResponseDto blockerResponse = blockerClient.check(dto.amount());
        if (blockerResponse.blocked()) {
            throw new OperationBlockedException(blockerResponse.reason());
        }

        AccountDto accountDto = accountsClient.withdraw(userId, accountId, dto.amount());

        return new CashOperationResultDto(
                accountDto.id(),
                dto.amount(),
                "WITHDRAW",
                "SUCCESS"
        );
    }
}