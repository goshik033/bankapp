package ru.kolidgio.bankapp.frontui.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.frontui.dto.TransferBetweenOwnAccountsRequestDto;

import java.math.BigDecimal;

@Component
public class TransferClient {

    @Value("${services.transfer-service.url}")
    private String transferServiceUrl;

    private final RestClient restClient = RestClient.builder().build();

    public void transferBetweenOwnAccounts(Long userId, TransferBetweenOwnAccountsRequestDto dto) {
        restClient.post()
                .uri(transferServiceUrl + "/api/users/{userId}/accounts/{fromAccountId}/transfer/own",
                        userId, dto.fromAccountId())
                .body(new TransferOwnRequestBody(dto.toAccountId(), dto.amount()))
                .retrieve()
                .toBodilessEntity();
    }

    private record TransferOwnRequestBody(
            Long toAccountId,
            BigDecimal amount
    ) {
    }
}