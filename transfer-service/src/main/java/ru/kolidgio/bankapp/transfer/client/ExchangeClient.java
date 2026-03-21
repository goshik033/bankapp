package ru.kolidgio.bankapp.transfer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.transfer.dto.exchange.ConvertRequestDto;
import ru.kolidgio.bankapp.transfer.dto.exchange.ConvertResponseDto;

@Component
@RequiredArgsConstructor
public class ExchangeClient {

    @Value("${services.exchange-service.url}")
    private String exchangeServiceUrl;

    private final RestClient oauth2RestClient  ;

    public ConvertResponseDto convert(ConvertRequestDto convertRequestDto) {
        return oauth2RestClient.post()
                .uri(exchangeServiceUrl + "/api/exchange/convert")
                .body(convertRequestDto)
                .retrieve()
                .body(ConvertResponseDto.class);
    }


}
