package ru.kolidgio.bankapp.transfer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class OAuth2ClientConfig {

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository
    ) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService
    ) {
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository,
                        authorizedClientService
                );

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }

    @Bean
    public RestClient oauth2RestClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        return RestClient.builder()
                .requestInterceptor((request, body, execution) -> {
                    Authentication principal = new TestingAuthenticationToken("service-client", "N/A");
                    ((TestingAuthenticationToken) principal).setAuthenticated(true);

                    OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                            .withClientRegistrationId("keycloak")
                            .principal(principal)
                            .build();

                    OAuth2AuthorizedClient authorizedClient =
                            authorizedClientManager.authorize(authorizeRequest);

                    if (authorizedClient == null || authorizedClient.getAccessToken() == null) {
                        throw new IllegalStateException("Не удалось получить access token для клиента keycloak");
                    }

                    request.getHeaders().set(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + authorizedClient.getAccessToken().getTokenValue()
                    );

                    return execution.execute(request, body);
                })
                .build();
    }
}