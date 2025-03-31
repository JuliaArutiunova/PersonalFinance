package by.it_academy.jd2.service;

import by.it_academy.jd2.dto.CurrencyCreateDto;
import by.it_academy.jd2.dto.CurrencyDto;
import by.it_academy.jd2.service.client.AccountClient;
import by.it_academy.jd2.dao.api.ICurrencyDao;
import by.it_academy.jd2.dao.entity.CurrencyEntity;
import by.it_academy.jd2.service.api.ICurrencyService;
import by.it_academy.jd2.service.client.AuditClient;
import by.it_academy.lib.dto.ActionInfoDto;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.enums.EssenceType;
import by.it_academy.lib.exception.PageNotExistsException;
import by.it_academy.lib.exception.RecordAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CurrencyService implements ICurrencyService {

    private final ICurrencyDao currencyDao;
    private final ModelMapper modelMapper;
    private final AccountClient accountClient;
    private final AuditClient auditClient;
    private final UserHolder userHolder;

    public CurrencyService(ICurrencyDao currencyDao, ModelMapper modelMapper, AccountClient accountClient,
                           AuditClient auditClient, UserHolder userHolder) {
        this.currencyDao = currencyDao;
        this.modelMapper = modelMapper;
        this.accountClient = accountClient;
        this.auditClient = auditClient;
        this.userHolder = userHolder;
    }


    @Override
    @Transactional
    public void create(CurrencyCreateDto currencyCreateDto) {
        String title = currencyCreateDto.getTitle();

        if (currencyDao.existsByTitle(title)) {
            throw new RecordAlreadyExistsException("Валюта c таким названием уже существует");
        }
        CurrencyEntity currencyEntity = CurrencyEntity.builder()
                .id(UUID.randomUUID())
                .title(title)
                .description(currencyCreateDto.getDescription())
                .build();
        currencyDao.saveAndFlush(currencyEntity);

        accountClient.saveCurrency(currencyEntity.getId(), currencyEntity.getTitle());

        sendCurrencyCreationAudit(currencyEntity.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<CurrencyDto> getCurrencyPage(int pageNumber, int size) {

        Page<CurrencyEntity> page = currencyDao.findAll(PageRequest.of(pageNumber, size));

        if (!page.hasPrevious() && pageNumber > 0) {
            throw new PageNotExistsException("Страницы с таким номером не существует");
        }

        if (page.isEmpty()) {
            return new PageDto<>();
        }

        return modelMapper.map(page, new TypeToken<PageDto<CurrencyDto>>() {
        }.getType());
    }


    @Override
    public Map<UUID, String> getNames(UUID[] uuids) {
        List<CurrencyEntity> currencyEntities = currencyDao.findAllById(Arrays.asList(uuids));
        Map<UUID, String> names = new HashMap<>();
        currencyEntities.forEach(currencyEntity -> {
            names.put(currencyEntity.getId(), currencyEntity.getTitle());
        });
        return names;
    }

    @Async
    private void sendCurrencyCreationAudit(UUID entityId) {
        auditClient.createAudit(ActionInfoDto.builder()
                .userId(userHolder.getUserId())
                .entityId(entityId)
                .essenceType(EssenceType.CURRENCY)
                .text("Создана новая валюта")
                .build());
    }

}
