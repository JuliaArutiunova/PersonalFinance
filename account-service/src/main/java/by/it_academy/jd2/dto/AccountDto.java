package by.it_academy.jd2.dto;

import by.it_academy.jd2.dao.entity.AccountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AccountDto {
    @JsonProperty(value = "uuid",index = 0)
    private UUID id;

    @JsonProperty(value = "dt_create", index = 1)
    private Long dtCreate;

    @JsonProperty(value = "dt_update", index = 2)
    private Long dtUpdate;

    @JsonProperty(index = 3)
    private String title;

    @JsonProperty(index = 4)
    private String description;

    @JsonProperty(index = 5)
    private BigDecimal balance;

    @JsonProperty(index = 6)
    private AccountType type;

    @JsonProperty(index = 7)
    private UUID currency;
}
