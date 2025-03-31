package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.IOperationDao;
import by.it_academy.jd2.dao.entity.AccountEntity;
import by.it_academy.jd2.dao.entity.CurrencyInfoEntity;
import by.it_academy.jd2.dao.entity.OperationCategoryIdEntity;
import by.it_academy.jd2.dao.entity.OperationEntity;
import by.it_academy.jd2.dto.OperationCreateDto;
import by.it_academy.jd2.dto.OperationDto;
import by.it_academy.jd2.dto.RecalculationDto;
import by.it_academy.jd2.service.api.*;
import by.it_academy.jd2.service.feign.AuditClient;
import by.it_academy.jd2.service.utils.MoneyOperator;
import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.PaginationDto;
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
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class OperationService implements IOperationService {
    private final IOperationDao operationDao;
    private final IAccountService accountService;
    private final IOperationCategoryService operationCategoryService;
    private final ICurrencyService currencyService;
    private final ModelMapper modelMapper;
    private final MoneyOperator moneyOperator;
    private final UserHolder userHolder;
    private final AuditClient auditClient;

    public OperationService(IOperationDao operationDao, IAccountService accountService,
                            IOperationCategoryService operationCategoryService,
                            ICurrencyService currencyService, ModelMapper modelMapper,
                            MoneyOperator moneyOperator, UserHolder userHolder, AuditClient auditClient) {
        this.operationDao = operationDao;
        this.accountService = accountService;
        this.operationCategoryService = operationCategoryService;
        this.currencyService = currencyService;
        this.modelMapper = modelMapper;
        this.moneyOperator = moneyOperator;
        this.userHolder = userHolder;
        this.auditClient = auditClient;
    }

    @Override
    @Transactional
    public void create(UUID accountId, OperationCreateDto operationCreateDto) {
        AccountEntity account = accountService.getAccountEntity(accountId);

        CurrencyInfoEntity newCurrency = currencyService.get(operationCreateDto.getCurrency());

        OperationCategoryIdEntity operationCategory = operationCategoryService
                .get(operationCreateDto.getCategory());

        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setId(UUID.randomUUID());
        operationEntity.setAccount(account);
        operationEntity.setDate(Instant.ofEpochSecond(operationCreateDto.getDate())
                .atZone(ZoneOffset.UTC).toLocalDate());
        operationEntity.setDescription(operationCreateDto.getDescription());
        operationEntity.setCategory(operationCategory);
        operationEntity.setValue(operationCreateDto.getValue());
        operationEntity.setCurrency(newCurrency);

        BigDecimal newBalance = moneyOperator.calculateBalance(account.getBalance(),
                operationCreateDto.getValue(), account.getCurrency().getId(),
                operationCreateDto.getCurrency());

        account.setBalance(newBalance);
        accountService.save(account);

        operationDao.saveAndFlush(operationEntity);

        sendToAudit(operationEntity.getId(), "Совершена операция по счету");
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<OperationDto> get(UUID accountId, PaginationDto paginationDto) {
        Page<OperationEntity> operationEntityPage =
                operationDao.findAllByAccountId(accountId,
                        PageRequest.of(paginationDto.getPage(), paginationDto.getSize()));

        if (paginationDto.getPage() > operationEntityPage.getTotalPages() - 1) {
            throw new PageNotExistsException("Страницы с таким номером не существует");
        }

        return modelMapper.map(operationEntityPage, new TypeToken<PageDto<OperationDto>>() {
        }.getType());
    }

    @Override
    @Transactional(readOnly = true)
    public OperationEntity get(UUID uuid) {
        return operationDao.findById(uuid).orElseThrow(() ->
                new RecordNotFoundException("Операция не найдена"));
    }


    @Override
    @Transactional
    public void update(UUID accountId, UUID operationId, long dtUpdate, OperationCreateDto operationCreateDto) {

        OperationEntity operationEntity = operationDao.findByOperationIdAndAccountId(operationId, accountId);

        if (operationEntity.getDtUpdate().toEpochSecond(ZoneOffset.UTC) != dtUpdate) {
            throw new DataChangedException();
        }

        CurrencyInfoEntity newCurrency = currencyService.get(operationCreateDto.getCurrency());

        OperationCategoryIdEntity operationCategory = operationCategoryService
                .get(operationCreateDto.getCategory());

        operationEntity.setDate(Instant.ofEpochSecond(operationCreateDto.getDate())
                .atZone(ZoneOffset.UTC).toLocalDate());
        operationEntity.setDescription(operationCreateDto.getDescription());
        operationEntity.setCategory(operationCategory);
        operationEntity.setValue(operationCreateDto.getValue());

        if (!operationCreateDto.getCurrency().equals(operationEntity.getCurrency().getId()) ||
                operationCreateDto.getValue().compareTo(operationEntity.getValue()) != 0) {
            AccountEntity account = operationEntity.getAccount();

            BigDecimal newBalance = moneyOperator.recalculateBalance(
                    RecalculationDto.builder()
                            .oldValue(operationEntity.getValue())
                            .oldCurrency(operationEntity.getCurrency().getId())
                            .newValue(operationCreateDto.getValue())
                            .newCurrency(operationCreateDto.getCurrency())
                            .accountBalance(account.getBalance())
                            .accountCurrency(account.getCurrency().getId())
                            .build());

            operationEntity.setCurrency(newCurrency);
            account.setBalance(newBalance);
            accountService.save(account);
        }

        operationDao.saveAndFlush(operationEntity);

        sendToAudit(operationId, "Изменена операция по счету");

    }

    @Override
    public void delete(UUID accountId, UUID operationId, long dtUpdate) {
        OperationEntity operationEntity = operationDao.findByOperationIdAndAccountId(operationId, accountId);

        if (operationEntity.getDtUpdate().toEpochSecond(ZoneOffset.UTC) != dtUpdate) {
            throw new DataChangedException();
        }

        AccountEntity account = operationEntity.getAccount();
        BigDecimal newBalance = moneyOperator.refundAmount(account.getBalance(), operationEntity.getValue(),
                account.getCurrency().getId(), operationEntity.getCurrency().getId());

        account.setBalance(newBalance);
        accountService.save(account);

        operationDao.delete(operationEntity);

        sendToAudit(operationId, "Удалена операция по счету");

    }

    @Async
    private void sendToAudit(UUID entityId, String message) {
        auditClient.createAudit(ActionInfoDto.builder()
                .userId(userHolder.getUserId())
                .entityId(entityId)
                .essenceType(EssenceType.OPERATION)
                .text(message)
                .build());
    }


}
