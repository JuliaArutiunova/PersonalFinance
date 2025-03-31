package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.CurrencyInfoEntity;

import java.util.UUID;

public interface ICurrencyService {
    CurrencyInfoEntity get(UUID uuid);
    void save(UUID uuid, String title);
}
