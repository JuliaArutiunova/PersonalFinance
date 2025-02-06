package by.it_academy.jd2.dto.exchangeRate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRateData {
    private String code;
    private BigDecimal value;
}
