package ru.kolidgio.bankapp.accounts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kolidgio.bankapp.accounts.dto.account.AccountDto;
import ru.kolidgio.bankapp.accounts.dto.account.CreateAccountDto;
import ru.kolidgio.bankapp.accounts.service.AccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping()
    public AccountDto create(@PathVariable("userId") Long userId, @RequestBody @Valid CreateAccountDto createAccountDto) {
        return accountService.create(userId, createAccountDto);
    }

    @GetMapping
    public List<AccountDto> findAllByUserId(@PathVariable("userId") Long userId) {
        return accountService.findAllByUserId(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{accountId}")
    public void deleteById(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) {
        accountService.delete(userId, accountId);
    }

    @GetMapping("/{accountId}")
    public AccountDto findById(@PathVariable("userId") Long userId, @PathVariable("accountId") Long accountId) {
        return accountService.findById(userId, accountId);
    }

}
