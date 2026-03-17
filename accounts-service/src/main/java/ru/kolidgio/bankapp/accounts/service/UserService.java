package ru.kolidgio.bankapp.accounts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolidgio.bankapp.accounts.dto.user.CreateUserDto;
import ru.kolidgio.bankapp.accounts.dto.user.UpdateUserDto;
import ru.kolidgio.bankapp.accounts.dto.user.UpdateUserPasswordDto;
import ru.kolidgio.bankapp.accounts.dto.user.UserDto;
import ru.kolidgio.bankapp.accounts.entity.User;
import ru.kolidgio.bankapp.accounts.repository.UserRepository;
import ru.kolidgio.bankapp.accounts.service.errors.BadRequestException;
import ru.kolidgio.bankapp.accounts.service.errors.ConflictException;
import ru.kolidgio.bankapp.accounts.service.errors.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        requireId(id, "userId");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь  с id " + id + " не найден"));
        return toDto(user);

    }

    @Transactional
    public UserDto update(Long id, UpdateUserDto updateUserDto) {
        requireId(id, "userId");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));

        if (updateUserDto.email() != null && !updateUserDto.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updateUserDto.email())) {
                throw new ConflictException("Пользователь с email " + updateUserDto.email() + " уже существует");
            }
            user.setEmail(updateUserDto.email());
        }

        if (updateUserDto.firstName() != null) {
            user.setFirstName(updateUserDto.firstName());
        }

        if (updateUserDto.lastName() != null) {
            user.setLastName(updateUserDto.lastName());
        }

        if (updateUserDto.birthDate() != null) {
            user.setBirthDate(updateUserDto.birthDate());
        }

        try {
            return toDto(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Не удалось обновить пользователя из-за ограничения БД", e);
        }
    }

    @Transactional
    public void delete(Long id) {
        requireId(id, "userId");
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь  с id " + id + " не найден"));
        userRepository.deleteById(id);
    }

    @Transactional
    public void changePassword(Long id, UpdateUserPasswordDto updateUserPasswordDto) {
        requireId(id, "userId");

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + id + " не найден"));

        if (!passwordEncoder.matches(updateUserPasswordDto.oldPassword(), user.getHashPassword())) {
            throw new BadRequestException("Неверный пароль");
        }

        user.setHashPassword(passwordEncoder.encode(updateUserPasswordDto.newPassword()));
        userRepository.save(user);
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

    private void requireId(Long id, String field) {
        if (id == null) {
            throw new BadRequestException(field + " не должен быть null");
        }
        if (id < 1) {
            throw new BadRequestException(field + " не должен быть меньше 1");
        }
    }


}
