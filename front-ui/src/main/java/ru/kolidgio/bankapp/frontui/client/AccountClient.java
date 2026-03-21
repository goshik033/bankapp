package ru.kolidgio.bankapp.frontui.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.frontui.dto.CreateUserDto;

@Component
public class AccountClient {
    @Value("${services.accounts-service.url}")
    private String accountsServiceUrl;

    private final RestClient restClient = RestClient.builder().build();

    public void create(CreateUserDto createUserDto) {
        restClient.post()
                .uri(accountsServiceUrl+"/api/users")
                .body(createUserDto)
                .retrieve()
                .toBodilessEntity();
    }

}
