package by.it_academy.jd2.controller;


import by.it_academy.jd2.dto.CurrencyCreateDto;
import by.it_academy.jd2.dto.CurrencyDto;
import by.it_academy.jd2.dto.OperationCategoryCreateDto;
import by.it_academy.jd2.dto.OperationCategoryDto;
import by.it_academy.jd2.service.api.ICurrencyService;
import by.it_academy.jd2.service.api.IOperationCategoryService;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.PaginationDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classifier")
public class ClassifierController {

    private final ICurrencyService currencyService;
    private final IOperationCategoryService operationCategoryService;

    public ClassifierController(ICurrencyService currencyService,
                                IOperationCategoryService operationCategoryService) {
        this.currencyService = currencyService;
        this.operationCategoryService = operationCategoryService;
    }

    @PostMapping("/currency")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCurrency(@Valid @RequestBody CurrencyCreateDto currencyCreateDTO) {
        currencyService.create(currencyCreateDTO);
    }

    @GetMapping("/currency")
    public PageDto<CurrencyDto> getCurrencyPage(@Valid PaginationDto paginationDto) {
        return currencyService.getCurrencyPage(paginationDto.getPage(), paginationDto.getSize());
    }

    @PostMapping("/operation/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOperationCategory(@Valid
                                     @RequestBody OperationCategoryCreateDto operationCategoryCreateDto) {
        operationCategoryService.create(operationCategoryCreateDto);
    }

    @GetMapping("/operation/category")
    public PageDto<OperationCategoryDto> getOperationCategoryPage(@Valid PaginationDto paginationDto) {
        return operationCategoryService.
                getOperationCategoryPage(paginationDto.getPage(), paginationDto.getSize());
    }
}
