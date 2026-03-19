package ru.kolidgio.bankapp.exchange.service;

import org.springframework.stereotype.Service;
import ru.kolidgio.bankapp.exchange.dto.ConvertRequestDto;
import ru.kolidgio.bankapp.exchange.dto.ConvertResponseDto;
import ru.kolidgio.bankapp.exchange.dto.ExchangeRateDto;
import ru.kolidgio.bankapp.exchange.dto.UpdateExchangeRateDto;
import ru.kolidgio.bankapp.exchange.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeService {

    private final Map<Currency, ExchangeRateDto> rates = new EnumMap<>(Currency.class);

    public ExchangeService() {
        rates.put(Currency.RUB, new ExchangeRateDto(Currency.RUB, BigDecimal.ONE, BigDecimal.ONE));
        rates.put(Currency.USD, new ExchangeRateDto(Currency.USD, new BigDecimal("89.50"), new BigDecimal("91.50")));
        rates.put(Currency.CNY, new ExchangeRateDto(Currency.CNY, new BigDecimal("12.10"), new BigDecimal("12.70")));
    }

    public List<ExchangeRateDto> getRates() {
        return rates.values().stream().toList();
    }

    public ExchangeRateDto updateRate(UpdateExchangeRateDto dto) {
        ExchangeRateDto updatedRate = new ExchangeRateDto(
                dto.currency(),
                dto.buyRate(),
                dto.sellRate()
        );

        rates.put(dto.currency(), updatedRate);
        return updatedRate;
    }

    public ConvertResponseDto convert(ConvertRequestDto dto) {
        if (dto.fromCurrency() == dto.toCurrency()) {
            return new ConvertResponseDto(
                    dto.fromCurrency(),
                    dto.toCurrency(),
                    dto.amount(),
                    dto.amount()
            );
        }

        BigDecimal rubAmount = toRub(dto.fromCurrency(), dto.amount());
        BigDecimal convertedAmount = fromRub(dto.toCurrency(), rubAmount);

        return new ConvertResponseDto(
                dto.fromCurrency(),
                dto.toCurrency(),
                dto.amount(),
                convertedAmount
        );
    }

    private BigDecimal toRub(Currency fromCurrency, BigDecimal amount) {
        if (fromCurrency == Currency.RUB) {
            return amount;
        }

        ExchangeRateDto rate = getRateOrThrow(fromCurrency);
        return amount.multiply(rate.buyRate()).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal fromRub(Currency toCurrency, BigDecimal rubAmount) {
        if (toCurrency == Currency.RUB) {
            return rubAmount;
        }

        ExchangeRateDto rate = getRateOrThrow(toCurrency);
        return rubAmount.divide(rate.sellRate(), 2, RoundingMode.HALF_UP);
    }

    private ExchangeRateDto getRateOrThrow(Currency currency) {
        ExchangeRateDto rate = rates.get(currency);
        if (rate == null) {
            throw new IllegalArgumentException("Курс для валюты " + currency + " не найден");
        }
        return rate;
    }
}