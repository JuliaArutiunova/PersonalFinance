package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.exchangeRate.ExchangeRateInfo;
import by.it_academy.jd2.service.api.IClientService;
import by.it_academy.jd2.service.feign.CurrencyApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ClientService implements IClientService {

    private final CurrencyApiClient currencyApiClient;

    @Value("${client.currency-api.key}")
    private String currencyApiKey;

    public ClientService(CurrencyApiClient currencyApiClient) {
        this.currencyApiClient = currencyApiClient;

    }

    @Override
    public ExchangeRateInfo getExchangeRate(String baseCurrency, String... currency) {
        String currencies = getCurrenciesParamString(currency);
        return currencyApiClient.getExchangeRate(currencyApiKey, baseCurrency, currencies);
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
