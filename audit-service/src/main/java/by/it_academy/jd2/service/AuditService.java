package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.IAuditDao;
import by.it_academy.jd2.dao.entity.AuditEntity;
import by.it_academy.jd2.dao.entity.UserInfoEntity;
import by.it_academy.jd2.dto.AuditDto;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.api.IUserInfoService;
import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.exception.PageNotExistsException;
import by.it_academy.lib.exception.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuditService implements IAuditService {
    private final IAuditDao auditDao;
    private final IUserInfoService userInfoService;
    private final ModelMapper modelMapper;

    public AuditService(IAuditDao auditDao, IUserInfoService userInfoService, ModelMapper modelMapper) {
        this.auditDao = auditDao;
        this.userInfoService = userInfoService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void create(ActionInfoDto actionInfoDto) {
        UserInfoEntity userInfo = userInfoService.getById(actionInfoDto.getUserId());

        AuditEntity auditEntity = AuditEntity.builder()
                .auditId(UUID.randomUUID())
                .user(userInfo)
                .text(actionInfoDto.getText())
                .essenceType(actionInfoDto.getEssenceType())
                .entityId(actionInfoDto.getEntityId())
                .build();

        auditDao.saveAndFlush(auditEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public AuditDto get(UUID id) {
        AuditEntity auditEntity = auditDao.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Действие не найдено"));
        return modelMapper.map(auditEntity, AuditDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<AuditDto> getPage(int pageNumber, int size) {
        Page<AuditEntity> page = auditDao.findAll(PageRequest.of(pageNumber, size));
        if (!page.hasPrevious() && pageNumber > 0) {
            throw new PageNotExistsException("Страницы с номером " + pageNumber + " не существует");
        }
        if (page.isEmpty()) {
            return new PageDto<>();
        }
        return modelMapper.map(page, new TypeToken<PageDto<AuditDto>>() {
        }.getType());

    }
}
