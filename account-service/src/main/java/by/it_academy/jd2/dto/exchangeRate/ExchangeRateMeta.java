package by.it_academy.jd2.dto.exchangeRate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRateMeta {
    @JsonProperty(value = "last_updated_at")
    private Instant lastDtUpdate;
}
