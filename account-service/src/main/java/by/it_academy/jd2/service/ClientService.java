package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.exchangeRate.ExchangeRateInfo;
import by.it_academy.jd2.service.api.IClientService;
import by.it_academy.jd2.service.feign.ClassifierClient;
import by.it_academy.jd2.service.feign.CurrencyApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.UUID;

@Service
public class ClientService implements IClientService {

    private final CurrencyApiClient currencyApiClient;
    private final ClassifierClient classifierClient;
    @Value("${client.currency-api.key}")
    private String currencyApiKey;

    public ClientService(CurrencyApiClient currencyApiClient, ClassifierClient classifierClient) {
        this.currencyApiClient = currencyApiClient;
        this.classifierClient = classifierClient;
    }

    @Override
    public ExchangeRateInfo getExchangeRate(String baseCurrency, String... currency) {
        String currencies = getCurrenciesParamString(currency);
        return currencyApiClient.getExchangeRate(currencyApiKey, baseCurrency, currencies);
    }

    @Override
    public Map<UUID, String> getCurrencyNames(UUID... currencies) {
        return classifierClient.getCurrencyNames(currencies);
    }


    private String getCurrenciesParamString(String[] currency) {
        int currencyLength = currency.length;

        if (currencyLength > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < currencyLength; i++) {
                stringBuilder.append(currency[i]);
                stringBuilder.append(i != currencyLength - 1 ? "," : "");
            }
            return stringBuilder.toString();

        } else {
            return currency[0];
        }
    }

}
