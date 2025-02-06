package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.OperationCategoryIdEntity;

import java.util.UUID;

public interface IOperationCategoryService {
    OperationCategoryIdEntity get(UUID id);
    void save(UUID uuid);
}
