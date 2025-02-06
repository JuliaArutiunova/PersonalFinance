package by.it_academy.jd2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
@AllArgsConstructor
@Data
@Builder
public class RecalculationDto {
    private BigDecimal oldValue;
    private UUID oldCurrency;
    private BigDecimal newValue;
    private UUID newCurrency;
    private BigDecimal accountBalance;
    private UUID accountCurrency;
}
