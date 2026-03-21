package ru.kolidgio.bankapp.frontui.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.frontui.dto.AccountDto;
import ru.kolidgio.bankapp.frontui.dto.CreateUserDto;
import ru.kolidgio.bankapp.frontui.dto.UserAuthDto;
import ru.kolidgio.bankapp.frontui.dto.UserDto;

import java.util.List;

@Component
public class AccountClient {
    @Value("${services.accounts-service.url}")
    private String accountsServiceUrl;

    private final RestClient restClient = RestClient.builder().build();

    public void create(CreateUserDto createUserDto) {
        restClient.post()
                .uri(accountsServiceUrl + "/api/users")
                .body(createUserDto)
                .retrieve()
                .toBodilessEntity();
    }

    public UserAuthDto getUserAuthByLogin(String login) {
        return restClient.get()
                .uri(accountsServiceUrl + "/api/auth/users/by-login?login={login}", login)
                .retrieve()
                .body(UserAuthDto.class);
    }
    public UserDto getUserByLogin(String login) {
        return restClient.get()
                .uri(accountsServiceUrl+ "/api/users/by-login?login={login}", login)
                .retrieve()
                .body(UserDto.class);
    }
    public List<AccountDto> getAccountsByUserId(Long userId) {
        return restClient.get()
                .uri(accountsServiceUrl+ "/api/users/{userId}/accounts",userId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<AccountDto>>() {});

    }

}
