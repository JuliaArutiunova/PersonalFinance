package by.it_academy.jd2.dto;

import by.it_academy.lib.dto.UserInfoDto;
import by.it_academy.lib.enums.EssenceType;
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
public class AuditDto {
    @JsonProperty(value = "uuid",index = 0)
    private UUID auditId;

    @JsonProperty(value = "dt_create",index = 1)
    private Long dtCreate;

    @JsonProperty(value = "dt_update",index = 2)
    private Long dtUpdate;

    @JsonProperty(index = 3)
    private UserInfoDto user;

    @JsonProperty(index = 4)
    private String text;

    @JsonProperty(index = 5)
    private EssenceType type;

    @JsonProperty(value = "id", index = 6)
    private UUID entityId;
}
