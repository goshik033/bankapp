package ru.kolidgio.bankapp.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String login;
    @Column(nullable = false, length = 100)
    private String lastName;
    @Column(nullable = false, length = 100)
    private String firstName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(name = "hash_password", nullable = false)
    private String hashPassword;
}