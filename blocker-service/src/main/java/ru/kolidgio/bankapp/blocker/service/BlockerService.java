package ru.kolidgio.bankapp.blocker.service;

import org.springframework.stereotype.Service;
import ru.kolidgio.bankapp.blocker.dto.BlockerResponseDto;

import java.math.BigDecimal;

@Service
public class BlockerService {

    public BlockerResponseDto check(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("10000")) > 0) {
            return new BlockerResponseDto(true, "Операция признана подозрительной");
        }
        return new BlockerResponseDto(false, "Операция разрешена");
    }
}