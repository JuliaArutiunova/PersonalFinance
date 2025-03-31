package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.exchangeRate.ExchangeRateInfo;


public interface IClientService {
    ExchangeRateInfo getExchangeRate(String baseCurrency, String... currency);

}
