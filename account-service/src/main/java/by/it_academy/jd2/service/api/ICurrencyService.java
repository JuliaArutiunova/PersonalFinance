package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.CurrencyIdEntity;

import java.util.UUID;

public interface ICurrencyService {
    CurrencyIdEntity get(UUID uuid);
    void save(UUID uuid);
}
