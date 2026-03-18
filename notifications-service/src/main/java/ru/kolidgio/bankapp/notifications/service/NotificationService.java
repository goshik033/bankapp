package ru.kolidgio.bankapp.notifications.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kolidgio.bankapp.notifications.dto.NotificationRequestDto;

@Service
@Slf4j
public class NotificationService {
    public void send(NotificationRequestDto notificationRequestDto) {
        log.info("Уведомление: userId={}, accountId={}, operation={}, amount={}, status={}",
                notificationRequestDto.userId(),
                notificationRequestDto.accountId(),
                notificationRequestDto.operation(),
                notificationRequestDto.amount(),
                notificationRequestDto.status());
    }
}
