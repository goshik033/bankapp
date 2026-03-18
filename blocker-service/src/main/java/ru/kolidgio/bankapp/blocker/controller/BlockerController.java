package ru.kolidgio.bankapp.blocker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kolidgio.bankapp.blocker.dto.BlockerResponseDto;
import ru.kolidgio.bankapp.blocker.service.BlockerService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blocker")
public class BlockerController {

    private final BlockerService blockerService;

    @PostMapping("/check")
    public BlockerResponseDto check(@RequestBody Map<String, BigDecimal> body) {
        return blockerService.check(body.get("amount"));
    }
}