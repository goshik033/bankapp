package ru.kolidgio.bankapp.frontui.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.frontui.dto.ExchangeRateDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExchangeClient {

    @Value("${services.exchange-service.url}")
    private String exchangeServiceUrl;
    private final RestClient oauth2RestClient;


    public List<ExchangeRateDto> getRates() {
        return oauth2RestClient.get()
                .uri(exchangeServiceUrl + "/api/exchange/rates")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ExchangeRateDto>>() {
                });
    }
}
