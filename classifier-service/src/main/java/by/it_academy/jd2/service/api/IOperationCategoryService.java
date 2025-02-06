package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.OperationCategoryCreateDto;
import by.it_academy.jd2.dto.OperationCategoryDto;
import by.it_academy.lib.dto.PageDto;

public interface IOperationCategoryService {
    void create(OperationCategoryCreateDto operationCategoryCreateDto);

    PageDto<OperationCategoryDto> getOperationCategoryPage(int pageNumber, int size);

}
