package by.it_academy.jd2.service.feign;

import by.it_academy.jd2.dto.exchangeRate.ExchangeRateInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency-api", url = "${client.currency-api.url}")
public interface CurrencyApiClient {
    @GetMapping
    ExchangeRateInfo getExchangeRate(@RequestParam("apikey") String apiKey,
                                     @RequestParam("base_currency") String baseCurrency,
                                     @RequestParam("currencies") String currencies);
}
