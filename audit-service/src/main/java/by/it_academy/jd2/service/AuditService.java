package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.IAuditDao;
import by.it_academy.jd2.dto.AuditDto;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.PaginationDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuditService implements IAuditService {
    private final IAuditDao auditDao;

    public AuditService(IAuditDao auditDao) {
        this.auditDao = auditDao;
    }

    @Override
    public void create(ActionInfoDto actionInfoDto) {}

    @Override
    public AuditDto get(UUID id) {
        return null;
    }

    @Override
    public PageDto<AuditDto> getPage(PaginationDto paginationDto) {
        return null;
    }
}
