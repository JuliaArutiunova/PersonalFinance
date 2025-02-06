package by.it_academy.jd2.controller;

import by.it_academy.jd2.service.api.ICurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/classifier_data")
public class DataController {
    private final ICurrencyService currencyService;
    public DataController(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public Map<UUID, String> getCurrencyNames(@RequestBody UUID[] uuids) {
        return currencyService.getNames(uuids);
    }
}
