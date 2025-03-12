package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.AuditDto;
import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.PaginationDto;

import java.util.UUID;

public interface IAuditService {
    void create(ActionInfoDto actionInfoDto);
    AuditDto get(UUID id);
    PageDto<AuditDto> getPage(int pageNumber, int size);
}
