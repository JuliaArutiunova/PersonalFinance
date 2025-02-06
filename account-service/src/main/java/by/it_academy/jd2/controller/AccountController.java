package by.it_academy.jd2.controller;


import by.it_academy.jd2.dto.AccountCreateDto;
import by.it_academy.jd2.dto.AccountDto;
import by.it_academy.jd2.dto.OperationCreateDto;
import by.it_academy.jd2.dto.OperationDto;
import by.it_academy.jd2.service.api.IAccountService;
import by.it_academy.jd2.service.api.IOperationService;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.PaginationDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final IAccountService accountService;
    private final IOperationService operationService;

    public AccountController(IAccountService accountService, IOperationService operationService) {
        this.accountService = accountService;
        this.operationService = operationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@Valid @RequestBody AccountCreateDto accountCreateDto) {
        accountService.create(accountCreateDto);
    }

    @GetMapping
    public PageDto<AccountDto> getAccountPage(@Valid PaginationDto paginationDto) {
        return accountService.getAccountInfo(paginationDto.getPage(), paginationDto.getSize());
    }

    @GetMapping("/{uuid}")
    public AccountDto getAccountInfo(@PathVariable("uuid") UUID uuid) {
        return accountService.getAccountInfo(uuid);
    }


    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public void updateAccount(@PathVariable("uuid") UUID uuid,
                              @PathVariable("dt_update") long dtUpdate,
                              @Valid @RequestBody AccountCreateDto accountCreateDto) {

        accountService.update(uuid, accountCreateDto, dtUpdate);
    }

    @PostMapping("/{uuid}/operation")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOperation(@PathVariable("uuid") UUID accountId,
                             @Valid @RequestBody OperationCreateDto operationCreateDTO) {

        operationService.create(accountId, operationCreateDTO);
    }

    @GetMapping("/{uuid}/operation")
    public PageDto<OperationDto> getOperationPage(@PathVariable("uuid") UUID accountId,
                                                  @Valid PaginationDto paginationDto) {
        return operationService.get(accountId, paginationDto);
    }

    @PutMapping("/{uuid}/operation/{uuid_operation}/dt_update/{dt_update}")
    public void updateOperation(@PathVariable("uuid") UUID accountId,
                                @PathVariable("uuid_operation") UUID operationId,
                                @PathVariable("dt_update") long dtUpdate,
                                @Valid @RequestBody OperationCreateDto operationCreateDTO) {

        operationService.update(accountId, operationId, dtUpdate, operationCreateDTO);

    }

    @DeleteMapping("/{uuid}/operation/{uuid_operation}/dt_update/{dt_update}")
    public void deleteOperation(@PathVariable("uuid") UUID accountId,
                                @PathVariable("uuid_operation") UUID operationId,
                                @PathVariable("dt_update") long dtUpdate) {

        operationService.delete(accountId, operationId, dtUpdate);
    }

}
