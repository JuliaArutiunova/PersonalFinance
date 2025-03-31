package by.it_academy.jd2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@AllArgsConstructor
@Data
@Builder
public class RecalculationDto {
    private BigDecimal oldValue;
    private String oldCurrency;
    private BigDecimal newValue;
    private String newCurrency;
    private BigDecimal accountBalance;
    private String accountCurrency;
}
