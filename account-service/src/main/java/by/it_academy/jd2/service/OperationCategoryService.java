package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.IOperationCategoryDao;
import by.it_academy.jd2.dao.entity.OperationCategoryIdEntity;
import by.it_academy.jd2.service.api.IOperationCategoryService;
import by.it_academy.lib.exception.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class OperationCategoryService implements IOperationCategoryService {

    private final IOperationCategoryDao operationCategoryDao;

    public OperationCategoryService(IOperationCategoryDao operationCategoryDao) {
        this.operationCategoryDao = operationCategoryDao;
    }

    @Override
    public OperationCategoryIdEntity get(UUID id) {
        return operationCategoryDao.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Категория операции не найдена"));
    }

    @Override
    public void save(UUID uuid) {
        OperationCategoryIdEntity operationCategory = new OperationCategoryIdEntity(uuid);
        operationCategoryDao.saveAndFlush(operationCategory);
    }
}
