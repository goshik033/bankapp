package ru.kolidgio.bankapp.frontui.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.frontui.dto.ExchangeRateDto;

import java.util.List;

@Component
public class ExchangeClient {

    @Value("${services.exchange-service.url}")
    private String exchangeServiceUrl;
    private final RestClient restClient = RestClient.builder().build();


    public List<ExchangeRateDto> getRates() {
        return restClient.get()
                .uri(exchangeServiceUrl + "/api/exchange/rates")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ExchangeRateDto>>() {
                });
    }
}
