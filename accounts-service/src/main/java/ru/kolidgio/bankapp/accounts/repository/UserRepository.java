package ru.kolidgio.bankapp.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolidgio.bankapp.accounts.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);


}
