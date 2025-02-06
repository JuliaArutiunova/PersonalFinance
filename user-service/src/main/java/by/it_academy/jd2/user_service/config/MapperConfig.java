package by.it_academy.jd2.user_service.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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
        modelMapper.addConverter(timeConverter);

        return modelMapper;
    }
}
