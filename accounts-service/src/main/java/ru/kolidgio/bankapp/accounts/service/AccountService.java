package ru.kolidgio.bankapp.accounts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolidgio.bankapp.accounts.dto.account.AccountDto;
import ru.kolidgio.bankapp.accounts.dto.account.CreateAccountDto;
import ru.kolidgio.bankapp.accounts.entity.Account;
import ru.kolidgio.bankapp.accounts.entity.User;
import ru.kolidgio.bankapp.accounts.repository.AccountRepository;
import ru.kolidgio.bankapp.accounts.repository.UserRepository;
import ru.kolidgio.bankapp.accounts.service.errors.BadRequestException;
import ru.kolidgio.bankapp.accounts.service.errors.ConflictException;
import ru.kolidgio.bankapp.accounts.service.errors.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    public AccountDto create(Long userId, CreateAccountDto createAccountDto) {
        requireId(userId, "userId");

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь с id " + userId + " не найден"));

        if (accountRepository.existsByUserIdAndCurrency(userId, createAccountDto.currency())) {
            throw new ConflictException("Счёт в валюте " + createAccountDto.currency() + " уже существует");
        }

        Account account = Account.builder()
                .user(user)
                .currency(createAccountDto.currency())
                .balance(BigDecimal.ZERO)
                .build();

        try {
            return toDto(accountRepository.save(account));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Не удалось создать счёт из-за ограничения БД", e);
        }
    }

    @Transactional(readOnly = true)
    public List<AccountDto> findAllByUserId(Long userId) {
        requireId(userId, "userId");

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }

        return accountRepository.findAllByUserId(userId).stream().map(this::toDto).toList();
    }

    @Transactional
    public void delete(Long userId, Long accountId) {
        requireId(userId, "userId");
        requireId(accountId, "accountId");

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Счёт с id " + accountId + " не найден"));

        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("Счёт не принадлежит пользователю с id " + userId);
        }

        if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BadRequestException("Нельзя удалить счёт с ненулевым балансом");
        }

        accountRepository.delete(account);
    }

    @Transactional(readOnly = true)
    public AccountDto findById(Long userId, Long accountId) {
        requireId(accountId, "accountId");
        requireId(userId, "userId");

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Счёт с id " + accountId + " не найден"));

        if (!account.getUser().getId().equals(userId)) {
            throw new BadRequestException("Счёт не принадлежит пользователю с id " + userId);
        }

        return toDto(account);
    }


    private AccountDto toDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getCurrency(),
                account.getBalance(),
                account.getUser().getId());
    }

    private void requireId(Long id, String field) {
        if (id == null) {
            throw new BadRequestException(field + " не должен быть null");
        }
        if (id < 1) {
            throw new BadRequestException(field + " не должен быть меньше 1");
        }
    }
}