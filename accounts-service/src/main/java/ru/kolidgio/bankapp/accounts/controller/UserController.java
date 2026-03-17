package ru.kolidgio.bankapp.accounts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kolidgio.bankapp.accounts.dto.user.CreateUserDto;
import ru.kolidgio.bankapp.accounts.dto.user.UpdateUserDto;
import ru.kolidgio.bankapp.accounts.dto.user.UpdateUserPasswordDto;
import ru.kolidgio.bankapp.accounts.dto.user.UserDto;
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

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable("id") Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        return userService.update(id, updateUserDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PatchMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable("id") Long id,
                               @RequestBody @Valid UpdateUserPasswordDto updateUserPasswordDto) {
        userService.changePassword(id, updateUserPasswordDto);
    }
}