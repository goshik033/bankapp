package ru.kolidgio.bankapp.frontui.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.frontui.dto.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountClient {
    @Value("${services.accounts-service.url}")
    private String accountsServiceUrl;

    private final RestClient oauth2RestClient;

    public void create(CreateUserDto createUserDto) {
        oauth2RestClient.post()
                .uri(accountsServiceUrl + "/api/users")
                .body(createUserDto)
                .retrieve()
                .toBodilessEntity();
    }

    public UserAuthDto getUserAuthByLogin(String login) {
        return oauth2RestClient.get()
                .uri(accountsServiceUrl + "/api/auth/users/by-login?login={login}", login)
                .retrieve()
                .body(UserAuthDto.class);
    }
    public UserDto getUserByLogin(String login) {
        return oauth2RestClient.get()
                .uri(accountsServiceUrl+ "/api/users/by-login?login={login}", login)
                .retrieve()
                .body(UserDto.class);
    }
    public List<AccountDto> getAccountsByUserId(Long userId) {
        return oauth2RestClient.get()
                .uri(accountsServiceUrl+ "/api/users/{userId}/accounts",userId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<AccountDto>>() {});

    }
    public void createAccount(Long userId, CreateAccountDto createAccountDto) {
        oauth2RestClient.post()
                .uri(accountsServiceUrl + "/api/users/{userId}/accounts", userId)
                .body(createAccountDto)
                .retrieve()
                .toBodilessEntity();
    }

}
