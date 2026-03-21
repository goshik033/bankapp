package ru.kolidgio.bankapp.cash.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.cash.dto.account.AccountDto;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AccountsClient {

    private final RestClient oauth2RestClient;

    @Value("${services.accounts-service.url}")
    private String accountsServiceUrl;

    public AccountDto deposit(Long userId, Long accountId, BigDecimal amount) {
        return oauth2RestClient.patch()
                .uri(accountsServiceUrl + "/api/users/{userId}/accounts/{accountId}/deposit", userId, accountId)
                .body(Map.of("amount", amount))
                .retrieve()
                .body(AccountDto.class);
    }

    public AccountDto withdraw(Long userId, Long accountId, BigDecimal amount) {
        return oauth2RestClient.patch()
                .uri(accountsServiceUrl + "/api/users/{userId}/accounts/{accountId}/withdraw", userId, accountId)
                .body(Map.of("amount", amount))
                .retrieve()
                .body(AccountDto.class);
    }
}