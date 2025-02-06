package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.AccountEntity;
import by.it_academy.jd2.dto.AccountCreateDto;
import by.it_academy.jd2.dto.AccountDto;

import by.it_academy.lib.dto.PageDto;

import java.util.UUID;

public interface IAccountService {

    void create(AccountCreateDto accountCreateDTO);

    PageDto<AccountDto> getAccountInfo(int page, int size);

    AccountDto getAccountInfo(UUID id);

    AccountEntity getAccountEntity(UUID id);

    void update(UUID id, AccountCreateDto accountCreateDTO, long dtUpdate);

    void save(AccountEntity accountEntity);

}
