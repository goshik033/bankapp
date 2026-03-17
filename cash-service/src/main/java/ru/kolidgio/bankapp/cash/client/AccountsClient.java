package ru.kolidgio.bankapp.cash.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.cash.dto.account.AccountDto;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class AccountsClient {

    private final RestClient restClient = RestClient.builder().build();

    @Value("${services.accounts-service.url}")
    private String accountsServiceUrl;

    public AccountDto deposit(Long userId, Long accountId, BigDecimal amount) {
        return restClient.patch()
                .uri(accountsServiceUrl + "/api/users/{userId}/accounts/{accountId}/deposit", userId, accountId)
                .body(Map.of("amount", amount))
                .retrieve()
                .body(AccountDto.class);
    }

    public AccountDto withdraw(Long userId, Long accountId, BigDecimal amount) {
        return restClient.patch()
                .uri(accountsServiceUrl + "/api/users/{userId}/accounts/{accountId}/withdraw", userId, accountId)
                .body(Map.of("amount", amount))
                .retrieve()
                .body(AccountDto.class);
    }
}