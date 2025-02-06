package by.it_academy.jd2.controller;

import by.it_academy.jd2.service.api.ICurrencyService;
import by.it_academy.jd2.service.api.IOperationCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account_data")
public class DataController {

    private final ICurrencyService currencyService;
    private final IOperationCategoryService operationCategoryService;

    public DataController(ICurrencyService currencyService, IOperationCategoryService operationCategoryService) {

        this.currencyService = currencyService;
        this.operationCategoryService = operationCategoryService;
    }

    @PostMapping("/currency")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCurrency(@RequestParam("currency_id") UUID currencyId){
        currencyService.save(currencyId);
    }


    @PostMapping("/operation_category")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveOperationCategory(@RequestParam("operation_category_id") UUID currencyId){
        operationCategoryService.save(currencyId);
    }
}
