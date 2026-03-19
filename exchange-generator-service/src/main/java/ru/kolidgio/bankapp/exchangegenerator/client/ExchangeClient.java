package ru.kolidgio.bankapp.exchangegenerator.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.exchangegenerator.dto.UpdateExchangeRateDto;

@Component
public class ExchangeClient {

    private final RestClient restClient = RestClient.builder().build();

    @Value("${services.exchange-service.url}")
    private String exchangeServiceUrl;

    public void updateRate(UpdateExchangeRateDto dto) {
        restClient.post()
                .uri(exchangeServiceUrl + "/api/exchange/rates")
                .body(dto)
                .retrieve()
                .toBodilessEntity();
    }
}