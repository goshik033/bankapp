package ru.kolidgio.bankapp.transfer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.transfer.dto.blocker.BlockerResponseDto;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BlockerClient {

    @Value("${services.blocker-service.url}")
    private String blockerServiceUrl;
    private final RestClient oauth2RestClient;

    public BlockerResponseDto check(BigDecimal amount) {
        return oauth2RestClient.post()
                .uri(blockerServiceUrl + "/api/blocker/check")
                .body(Map.of("amount", amount))
                .retrieve()
                .body(BlockerResponseDto.class);

    }
}
