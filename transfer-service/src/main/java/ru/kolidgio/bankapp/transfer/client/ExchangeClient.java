package ru.kolidgio.bankapp.transfer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.transfer.dto.exchange.ConvertRequestDto;
import ru.kolidgio.bankapp.transfer.dto.exchange.ConvertResponseDto;

@Component
public class ExchangeClient {

    @Value("${services.exchange-service.url}")
    private String exchangeServiceUrl;

    RestClient restClient = RestClient.builder().build();

    public ConvertResponseDto convert(ConvertRequestDto convertRequestDto) {
        return restClient.post()
                .uri(exchangeServiceUrl + "/api/exchange/convert")
                .body(convertRequestDto)
                .retrieve()
                .body(ConvertResponseDto.class);
    }


}
