package ru.kolidgio.bankapp.frontui.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.frontui.dto.CashOperationDto;

@Component
public class CashClient {

    @Value("${services.cash-service.url}")
    private String cashServiceUrl;

    private final RestClient restClient = RestClient.builder().build();

    public void deposit(Long userId, CashOperationDto dto) {
        restClient.post()
                .uri(cashServiceUrl + "/api/users/{userId}/accounts/{accountId}/cash/deposit",
                        userId, dto.accountId())
                .body(java.util.Map.of("amount", dto.amount()))
                .retrieve()
                .toBodilessEntity();
    }

    public void withdraw(Long userId, CashOperationDto dto) {
        restClient.post()
                .uri(cashServiceUrl + "/api/users/{userId}/accounts/{accountId}/cash/withdraw",
                        userId, dto.accountId())
                .body(java.util.Map.of("amount", dto.amount()))
                .retrieve()
                .toBodilessEntity();
    }

}

