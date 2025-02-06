package by.it_academy.jd2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OperationCategoryDto {
    @JsonProperty(value = "uuid",index = 0)
    private UUID id;

    @JsonProperty(value = "dt_create", index = 1)
    private long dtCreate;

    @JsonProperty(value = "dt_update", index = 2)
    private long dtUpdate;

    @JsonProperty(index = 3)
    private String title;
}
