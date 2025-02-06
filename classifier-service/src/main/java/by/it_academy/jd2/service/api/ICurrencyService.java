package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.CurrencyCreateDto;
import by.it_academy.jd2.dto.CurrencyDto;
import by.it_academy.lib.dto.PageDto;

import java.util.Map;
import java.util.UUID;

public interface ICurrencyService {
    void create(CurrencyCreateDto currencyCreateDto);

    PageDto<CurrencyDto> getCurrencyPage(int pageNumber, int size);

    Map<UUID, String> getNames(UUID[] uuids);

}
