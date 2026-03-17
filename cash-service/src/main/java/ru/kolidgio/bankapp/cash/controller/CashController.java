package ru.kolidgio.bankapp.cash.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kolidgio.bankapp.cash.dto.account.CashOperationDto;
import ru.kolidgio.bankapp.cash.dto.account.CashOperationResultDto;
import ru.kolidgio.bankapp.cash.service.CashService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/accounts/{accountId}/cash")
public class CashController {

    private final CashService cashService;

    @PostMapping("/deposit")
    public CashOperationResultDto deposit(@PathVariable("userId") Long userId,
                                          @PathVariable("accountId") Long accountId,
                                          @RequestBody @Valid CashOperationDto dto) {
        return cashService.deposit(userId, accountId, dto);
    }

    @PostMapping("/withdraw")
    public CashOperationResultDto withdraw(@PathVariable("userId") Long userId,
                                           @PathVariable("accountId") Long accountId,
                                           @RequestBody @Valid CashOperationDto dto) {
        return cashService.withdraw(userId, accountId, dto);
    }
}