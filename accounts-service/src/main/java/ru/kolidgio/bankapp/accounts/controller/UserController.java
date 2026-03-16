package ru.kolidgio.bankapp.accounts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kolidgio.bankapp.accounts.dto.CreateUserDto;
import ru.kolidgio.bankapp.accounts.dto.UserDto;
import ru.kolidgio.bankapp.accounts.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto create(@RequestBody @Valid CreateUserDto createUserDto) {
        return userService.create(createUserDto);

    }

    @GetMapping
    public List<UserDto> findAll() {
        return userService.findAll();
    }
}
