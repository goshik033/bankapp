package ru.kolidgio.bankapp.transfer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.transfer.dto.notification.NotificationRequestDto;

@Component
public class NotificationClient {

    @Value("${services.notification-service.url}")
    private String notificationServiceUrl;

    private final RestClient restClient = RestClient.builder().build();

    public void send(NotificationRequestDto notificationRequestDto) {
        restClient.post()
                .uri(notificationServiceUrl + "/api/notifications/send")
                .body(notificationRequestDto)
                .retrieve()
                .toBodilessEntity();
    }


}
