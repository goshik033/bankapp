package ru.kolidgio.bankapp.accounts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kolidgio.bankapp.accounts.dto.user.UserAuthDto;
import ru.kolidgio.bankapp.accounts.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/users/by-login")
    public UserAuthDto getUserByLogin(@RequestParam("login") String login) {
        return userService.findAuthByLogin(login);
    }

}
