package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.exchangeRate.ExchangeRateInfo;

import java.util.Map;
import java.util.UUID;

public interface IClientService {
    ExchangeRateInfo getExchangeRate(String baseCurrency, String... currency);

    Map<UUID,String> getCurrencyNames(UUID... currencies);


}
