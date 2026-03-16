package ru.kolidgio.bankapp.accounts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kolidgio.bankapp.accounts.dto.CreateUserDto;
import ru.kolidgio.bankapp.accounts.dto.UserDto;
import ru.kolidgio.bankapp.accounts.entity.User;
import ru.kolidgio.bankapp.accounts.repository.UserRepository;
import ru.kolidgio.bankapp.accounts.service.errors.BadRequestException;
import ru.kolidgio.bankapp.accounts.service.errors.ConflictException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto create(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.email()))
            throw new ConflictException("Пользователь с email " + createUserDto.email() + " уже существует");
        if (userRepository.existsByLogin(createUserDto.login()))
            throw new ConflictException("Пользователь с login " + createUserDto.login() + " уже существует");
        User user = User.builder()
                .login(createUserDto.login())
                .email(createUserDto.email())
                .firstName(createUserDto.firstName())
                .lastName(createUserDto.lastName())
                .birthDate(createUserDto.birthDate())
                .hashPassword(passwordEncoder.encode(createUserDto.password()))
                .build();
        try {
            return toDto(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Не удалось создать пользователя из-за ограничения БД", e);
        }
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getLogin(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthDate()
        );
    }


}
