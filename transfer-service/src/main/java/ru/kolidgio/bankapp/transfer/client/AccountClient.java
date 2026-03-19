package ru.kolidgio.bankapp.transfer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.transfer.dto.account.AccountDto;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class AccountClient {

    @Value("${services.accounts-service.url}")
    private String accountServiceUrl;

    private final RestClient restClient = RestClient.builder().build();

    public AccountDto withdraw(Long userId, Long accountId, BigDecimal amount) {
        return restClient.patch()
                .uri(accountServiceUrl + "/api/users/{userId}/accounts/{accountId}/withdraw", userId, accountId)
                .body(Map.of("amount", amount))
                .retrieve()
                .body(AccountDto.class);
    }

    public AccountDto deposit(Long userId, Long accountId, BigDecimal amount) {
        return restClient.patch()
                .uri(accountServiceUrl + "/api/users/{userId}/accounts/{accountId}/deposit", userId, accountId)
                .body(Map.of("amount", amount))
                .retrieve()
                .body(AccountDto.class);
    }

    public AccountDto findById(Long userId, Long accountId) {
        return restClient.get()
                .uri(accountServiceUrl + "/api/users/{userId}/accounts/{accountId}", userId, accountId)
                .retrieve()
                .body(AccountDto.class);
    }
}
