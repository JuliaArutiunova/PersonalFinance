package by.it_academy.jd2.dto.exchangeRate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRateInfo {
    private ExchangeRateMeta meta;
    private Map<String, ExchangeRateData> data;

}
