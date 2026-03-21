package ru.kolidgio.bankapp.frontui.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.frontui.dto.TransferBetweenOwnAccountsRequestDto;
import ru.kolidgio.bankapp.frontui.dto.TransferToAnotherUserRequestDto;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransferClient {

    @Value("${services.transfer-service.url}")
    private String transferServiceUrl;

    private final RestClient oauth2RestClient;

    public void transferBetweenOwnAccounts(Long userId, TransferBetweenOwnAccountsRequestDto dto) {
        oauth2RestClient.post()
                .uri(transferServiceUrl + "/api/users/{userId}/accounts/{fromAccountId}/transfer/own",
                        userId, dto.fromAccountId())
                .body(new TransferOwnRequestBody(dto.toAccountId(), dto.amount()))
                .retrieve()
                .toBodilessEntity();
    }

    public void transferToAnotherUser(Long fromUserId,
                                      Long toUserId,
                                      TransferToAnotherUserRequestDto dto) {
        oauth2RestClient.post()
                .uri(transferServiceUrl + "/api/users/{userId}/accounts/{fromAccountId}/transfer/other",
                        fromUserId, dto.fromAccountId())
                .body(new TransferOtherRequestBody(toUserId, dto.toAccountId(), dto.amount()))
                .retrieve()
                .toBodilessEntity();
    }

    private record TransferOtherRequestBody(
            Long toUserId,
            Long toAccountId,
            java.math.BigDecimal amount
    ) {
    }

    private record TransferOwnRequestBody(
            Long toAccountId,
            BigDecimal amount
    ) {
    }
}