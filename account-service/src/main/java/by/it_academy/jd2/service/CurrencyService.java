package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.ICurrencyDao;
import by.it_academy.jd2.dao.entity.CurrencyInfoEntity;
import by.it_academy.jd2.service.api.ICurrencyService;
import by.it_academy.lib.exception.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CurrencyService implements ICurrencyService {
    private final ICurrencyDao currencyDao;

    public CurrencyService(ICurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Override
    public CurrencyInfoEntity get(UUID uuid) {
        return currencyDao.findById(uuid).orElseThrow(() ->
                new RecordNotFoundException("Валюта не найдена"));
    }

    @Override
    public void save(UUID uuid, String title) {
        CurrencyInfoEntity currency = new CurrencyInfoEntity(uuid, title);
        currencyDao.saveAndFlush(currency);
    }
}
