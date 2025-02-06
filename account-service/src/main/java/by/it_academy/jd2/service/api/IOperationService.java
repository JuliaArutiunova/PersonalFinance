package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.OperationEntity;
import by.it_academy.jd2.dto.OperationCreateDto;
import by.it_academy.jd2.dto.OperationDto;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.PaginationDto;

import java.util.UUID;

public interface IOperationService {

    void create(UUID accountId, OperationCreateDto operationCreateDTO);

    PageDto<OperationDto> get(UUID accountId, PaginationDto paginationDTO);

    OperationEntity get(UUID uuid);

    void update(UUID accountId, UUID operationId, long dtUpdate, OperationCreateDto operationCreateDTO);

    void delete(UUID accountId, UUID operationId, long dtUpdate);
}
