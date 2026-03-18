package ru.kolidgio.bankapp.cash.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.cash.dto.blocker.BlockerResponseDto;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class BlockerClient {

    private final RestClient restClient = RestClient.builder().build();

    @Value("${services.blocker-service.url}")
    private String blockerServiceUrl;

    public BlockerResponseDto check(BigDecimal amount) {
        return restClient.post()
                .uri(blockerServiceUrl + "/api/blocker/check")
                .body(Map.of("amount", amount))
                .retrieve()
                .body(BlockerResponseDto.class);
    }
}