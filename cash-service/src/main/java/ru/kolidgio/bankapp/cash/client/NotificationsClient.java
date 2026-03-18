package ru.kolidgio.bankapp.cash.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.cash.dto.notifications.NotificationRequestDto;

@Component
public class NotificationsClient {

    private final RestClient restClient = RestClient.builder().build();

    @Value("${services.notification-service.url}")
    private String notificationServiceUrl;

    public void send(NotificationRequestDto notificationRequestDto) {
        restClient.post()
                .uri(notificationServiceUrl + "/api/notifications/send")
                .body(notificationRequestDto)
                .retrieve()
                .toBodilessEntity();

    }
}
