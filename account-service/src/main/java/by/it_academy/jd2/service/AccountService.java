package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.IAccountDao;
import by.it_academy.jd2.dao.entity.AccountEntity;
import by.it_academy.jd2.dao.entity.AccountType;
import by.it_academy.jd2.dao.entity.CurrencyInfoEntity;
import by.it_academy.jd2.dto.AccountCreateDto;
import by.it_academy.jd2.dto.AccountDto;
import by.it_academy.jd2.service.api.IAccountService;
import by.it_academy.jd2.service.api.ICurrencyService;
import by.it_academy.jd2.service.feign.AuditClient;
import by.it_academy.jd2.service.utils.MoneyOperator;
import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.enums.EssenceType;
import by.it_academy.lib.exception.DataChangedException;
import by.it_academy.lib.exception.PageNotExistsException;
import by.it_academy.lib.exception.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    private final IAccountDao accountDao;
    private final ICurrencyService currencyService;
    private final UserHolder userHolder;
    private final MoneyOperator moneyOperator;
    private final ModelMapper modelMapper;

    private final AuditClient auditClient;

    public AccountService(IAccountDao accountDao, ICurrencyService currencyService,
                          UserHolder userHolder, MoneyOperator moneyOperator, ModelMapper modelMapper, AuditClient auditClient) {
        this.accountDao = accountDao;
        this.currencyService = currencyService;
        this.userHolder = userHolder;
        this.moneyOperator = moneyOperator;
        this.modelMapper = modelMapper;
        this.auditClient = auditClient;
    }

    @Override
    @Transactional
    public void create(AccountCreateDto accountCreateDto) {

        CurrencyInfoEntity currency = currencyService.get(accountCreateDto.getCurrency());

        UUID userId = userHolder.getUserId();

        AccountEntity account = new AccountEntity();
        account.setId(UUID.randomUUID());
        account.setUser(userId);
        account.setTitle(accountCreateDto.getTitle());
        account.setDescription(accountCreateDto.getDescription());
        account.setType(AccountType.valueOf(accountCreateDto.getType()));
        account.setCurrency(currency);
        account.setBalance(BigDecimal.ZERO);

        accountDao.saveAndFlush(account);

        sendToAudit(account.getId(), "Создан новый счет");
;

    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<AccountDto> getAccountInfo(int pageNumber, int size) {
        UUID userId = userHolder.getUserId();
        Page<AccountEntity> page = accountDao.findAllByUser(userId, PageRequest.of(pageNumber, size));

        if (!page.hasPrevious() && pageNumber > 0) {
            throw new PageNotExistsException("Страницы с таким номером не существует");
        }

        if (page.isEmpty()) {
            return new PageDto<>();
        }

        return modelMapper.map(page, new TypeToken<PageDto<AccountDto>>() {
        }.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountInfo(UUID id) {
        AccountEntity accountEntity = getAccountEntity(id);
        return modelMapper.map(accountEntity, AccountDto.class);

    }

    @Override
    @Transactional(readOnly = true)
    public AccountEntity getAccountEntity(UUID id) {
        return accountDao.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Счет не найден"));
    }


    @Override
    @Transactional
    public void update(UUID id, AccountCreateDto accountCreateDto, long dtUpdate) {
        AccountEntity account = getAccountEntity(id);

        if (account.getDtUpdate().toEpochSecond(ZoneOffset.UTC) != dtUpdate) {
            throw new DataChangedException();
        }

        if (!account.getCurrency().getId().equals(accountCreateDto.getCurrency())) {
            CurrencyInfoEntity newCurrency = currencyService.get(accountCreateDto.getCurrency());

            if (account.getBalance().compareTo(BigDecimal.ZERO) != 0) {
                account.setBalance(moneyOperator.convertBalanceAmount(account.getBalance(),
                        accountCreateDto.getCurrency(), account.getCurrency().getId()));
            }

            account.setCurrency(newCurrency);
        }

        account.setTitle(accountCreateDto.getTitle());
        account.setDescription(accountCreateDto.getDescription());
        account.setType(AccountType.valueOf(accountCreateDto.getType()));

        accountDao.saveAndFlush(account);

        sendToAudit(account.getId(), "Обновлены данные счета");
    }

    @Override
    @Transactional
    public void save(AccountEntity accountEntity) {
        accountDao.saveAndFlush(accountEntity);
    }
    @Async
    private void sendToAudit(UUID entityId, String message){
        auditClient.createAudit(ActionInfoDto.builder()
                .userId(userHolder.getUserId())
                .entityId(entityId)
                .essenceType(EssenceType.ACCOUNT)
                .text(message)
                .build());
    }


}
