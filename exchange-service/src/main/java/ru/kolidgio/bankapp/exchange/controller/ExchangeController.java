package ru.kolidgio.bankapp.exchange.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kolidgio.bankapp.exchange.dto.ConvertRequestDto;
import ru.kolidgio.bankapp.exchange.dto.ConvertResponseDto;
import ru.kolidgio.bankapp.exchange.dto.ExchangeRateDto;
import ru.kolidgio.bankapp.exchange.dto.UpdateExchangeRateDto;
import ru.kolidgio.bankapp.exchange.service.ExchangeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/rates")
    public List<ExchangeRateDto> getRates() {
        return exchangeService.getRates();
    }

    @PostMapping("/rates")
    public ExchangeRateDto updateRate(@RequestBody @Valid UpdateExchangeRateDto dto) {
        return exchangeService.updateRate(dto);
    }

    @PostMapping("/convert")
    public ConvertResponseDto convert(@RequestBody @Valid ConvertRequestDto dto) {
        return exchangeService.convert(dto);
    }
}