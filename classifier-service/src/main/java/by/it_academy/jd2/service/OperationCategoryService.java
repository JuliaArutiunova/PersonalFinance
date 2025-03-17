package by.it_academy.jd2.service;


import by.it_academy.jd2.dto.OperationCategoryCreateDto;
import by.it_academy.jd2.dto.OperationCategoryDto;
import by.it_academy.jd2.service.client.AccountClient;
import by.it_academy.jd2.dao.entity.OperationCategoryEntity;
import by.it_academy.jd2.service.client.AuditClient;
import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.enums.EssenceType;
import by.it_academy.lib.exception.PageNotExistsException;
import by.it_academy.jd2.service.api.IOperationCategoryService;
import by.it_academy.jd2.dao.api.IOperationCategoryDao;
import by.it_academy.lib.exception.RecordAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
public class OperationCategoryService implements IOperationCategoryService {

    private final IOperationCategoryDao operationCategoryDao;
    private final ModelMapper modelMapper;
    private final AccountClient accountClient;

    private final AuditClient auditClient;
    private final UserHolder userHolder;

    public OperationCategoryService(IOperationCategoryDao operationCategoryDao, ModelMapper modelMapper, AccountClient accountClient, AuditClient auditClient, UserHolder userHolder) {
        this.operationCategoryDao = operationCategoryDao;
        this.modelMapper = modelMapper;
        this.accountClient = accountClient;
        this.auditClient = auditClient;
        this.userHolder = userHolder;
    }


    @Override
    @Transactional
    public void create(OperationCategoryCreateDto operationCategoryCreateDto) {

        if (operationCategoryDao.existsByTitle(operationCategoryCreateDto.getTitle())) {
            throw new RecordAlreadyExistsException("Категория с таким названием уже существует");
        }

        OperationCategoryEntity operationCategory = OperationCategoryEntity.builder()
                .id(UUID.randomUUID())
                .title(operationCategoryCreateDto.getTitle())
                .build();
        operationCategoryDao.saveAndFlush(operationCategory);

        accountClient.saveOperationCategoryId(operationCategory.getId());

        sendOperationCategoryCreationAudit(operationCategory.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<OperationCategoryDto> getOperationCategoryPage(int pageNumber, int size) {
        Page<OperationCategoryEntity> page = operationCategoryDao.findAll(PageRequest.of(pageNumber, size));

        if (!page.hasPrevious() && pageNumber > 0) {
            throw new PageNotExistsException("Страницы с таким номером не существует");
        }

        if (page.isEmpty()) {
            return new PageDto<>();
        }

        return modelMapper.map(page, new TypeToken<PageDto<OperationCategoryDto>>() {
        }.getType());
    }

    @Async
    private void sendOperationCategoryCreationAudit(UUID entityId) {
        auditClient.createAudit(ActionInfoDto.builder()
                .userId(userHolder.getUserId())
                .entityId(entityId)
                .essenceType(EssenceType.OPERATION_CATEGORY)
                .text("Создана новая категория операций")
                .build());
    }
}
