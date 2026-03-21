package ru.kolidgio.bankapp.frontui.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CreateUserDto(
        @NotBlank @Size(min = 3, max = 50) String login,

        @NotBlank @Email @Size(max = 255) String email,

        @NotBlank @Size(max = 100) String firstName,

        @NotBlank @Size(max = 100) String lastName,

        @NotNull @Past @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,

        @NotBlank @Size(min = 8, max = 100) String password
) {
}