package ru.kolidgio.bankapp.exchangegenerator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kolidgio.bankapp.exchangegenerator.client.ExchangeClient;
import ru.kolidgio.bankapp.exchangegenerator.dto.UpdateExchangeRateDto;
import ru.kolidgio.bankapp.exchangegenerator.entity.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ExchangeGeneratorService {

    private final ExchangeClient exchangeClient;

    @Scheduled(fixedRate = 1000)
    public void generateRates() {
        exchangeClient.updateRate(new UpdateExchangeRateDto(
                Currency.USD,
                randomRate("88.00", "90.00"),
                randomRate("90.50", "93.00")
        ));

        exchangeClient.updateRate(new UpdateExchangeRateDto(
                Currency.CNY,
                randomRate("11.80", "12.40"),
                randomRate("12.50", "13.00")
        ));
    }

    private BigDecimal randomRate(String min, String max) {
        double minValue = Double.parseDouble(min);
        double maxValue = Double.parseDouble(max);
        double randomValue = ThreadLocalRandom.current().nextDouble(minValue, maxValue);

        return BigDecimal.valueOf(randomValue).setScale(2, RoundingMode.HALF_UP);
    }
}