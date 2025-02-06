package by.it_academy.jd2.user_service.dto;

import by.it_academy.lib.enums.UserRole;
import by.it_academy.jd2.user_service.dao.entity.UserStatus;
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
public class UserDto {
    @JsonProperty(value = "uuid",index = 0)
    private UUID userId;

    @JsonProperty(value = "dt_create", index = 1)
    private long dtCreate;

    @JsonProperty(value = "dt_update", index = 2)
    private long dtUpdate;

    @JsonProperty(index = 3)
    private String mail;

    @JsonProperty(index = 4)
    private String fio;

    @JsonProperty(index = 5)
    private UserRole role;

    @JsonProperty(index = 6)
    private UserStatus status;
}
