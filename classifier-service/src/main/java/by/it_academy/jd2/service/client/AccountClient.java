package by.it_academy.jd2.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "account-service", url = "${client.account-service.url}")
public interface AccountClient {
    @PostMapping("/account_data/currency")
    void saveCurrency(@RequestParam("currency_id") UUID currencyId,
                      @RequestParam("title") String title);
    @PostMapping("/account_data/operation_category")
    void saveOperationCategoryId(@RequestParam("operation_category_id") UUID operationCategoryID);
}
