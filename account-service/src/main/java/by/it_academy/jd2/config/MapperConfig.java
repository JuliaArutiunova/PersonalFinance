package by.it_academy.jd2.config;


import by.it_academy.jd2.dao.entity.CurrencyIdEntity;
import by.it_academy.jd2.dao.entity.OperationCategoryIdEntity;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        Converter<LocalDateTime, Long> timeConverter = new AbstractConverter<LocalDateTime, Long>() {
            @Override
            protected Long convert(LocalDateTime localDateTime) {
                return localDateTime.toEpochSecond(ZoneOffset.UTC);
            }
        };

        Converter<LocalDate, Long> dateConverter = new AbstractConverter<LocalDate, Long>() {
            @Override
            protected Long convert(LocalDate localDate) {
                Instant instant = localDate.atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

                return instant.getEpochSecond();
            }
        };


        Converter<CurrencyIdEntity, UUID> currencyConverter = new AbstractConverter<CurrencyIdEntity, UUID>() {
            @Override
            protected UUID convert(CurrencyIdEntity currencyIdEntity) {
                return currencyIdEntity.getId();
            }
        };


        Converter<OperationCategoryIdEntity, UUID> operationCategoryConverter = new AbstractConverter<OperationCategoryIdEntity, UUID>() {
            @Override
            protected UUID convert(OperationCategoryIdEntity operationCategoryIdEntity) {
                return operationCategoryIdEntity.getId();
            }
        };


        modelMapper.addConverter(timeConverter);
        modelMapper.addConverter(dateConverter);
        modelMapper.addConverter(currencyConverter);
        modelMapper.addConverter(operationCategoryConverter);


        return modelMapper;
    }
}
