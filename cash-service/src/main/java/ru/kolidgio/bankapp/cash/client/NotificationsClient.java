package ru.kolidgio.bankapp.cash.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.cash.dto.notifications.NotificationRequestDto;

@Component
@RequiredArgsConstructor
public class NotificationsClient {

    private final RestClient oauth2RestClient;

    @Value("${services.notification-service.url}")
    private String notificationServiceUrl;

    public void send(NotificationRequestDto notificationRequestDto) {
        oauth2RestClient.post()
                .uri(notificationServiceUrl + "/api/notifications/send")
                .body(notificationRequestDto)
                .retrieve()
                .toBodilessEntity();

    }
}
