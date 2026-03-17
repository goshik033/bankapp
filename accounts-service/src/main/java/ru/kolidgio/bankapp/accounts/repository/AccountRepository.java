package ru.kolidgio.bankapp.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolidgio.bankapp.accounts.entity.Account;
import ru.kolidgio.bankapp.accounts.entity.Currency;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserId(Long userId);

    Optional<Account> findByUserIdAndCurrency(Long userId, Currency currency);

    boolean existsByUserIdAndCurrency(Long userId, Currency currency);
}
