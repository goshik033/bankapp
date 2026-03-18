package ru.kolidgio.bankapp.notifications.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kolidgio.bankapp.notifications.dto.NotificationRequestDto;
import ru.kolidgio.bankapp.notifications.service.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationsController {
    private final NotificationService notificationService;
    @PostMapping("/send")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void send(@RequestBody NotificationRequestDto  notificationRequestDto) {
        notificationService.send(notificationRequestDto);
    }
}
