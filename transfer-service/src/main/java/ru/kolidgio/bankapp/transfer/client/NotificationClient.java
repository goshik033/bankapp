package ru.kolidgio.bankapp.transfer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.kolidgio.bankapp.transfer.dto.notification.NotificationRequestDto;

@Component
@RequiredArgsConstructor
public class NotificationClient {

    @Value("${services.notification-service.url}")
    private String notificationServiceUrl;

    private final RestClient oauth2RestClient;

    public void send(NotificationRequestDto notificationRequestDto) {
        oauth2RestClient.post()
                .uri(notificationServiceUrl + "/api/notifications/send")
                .body(notificationRequestDto)
                .retrieve()
                .toBodilessEntity();
    }


}
