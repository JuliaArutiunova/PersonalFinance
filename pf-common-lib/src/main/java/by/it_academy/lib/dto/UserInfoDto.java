package by.it_academy.lib.dto;

import by.it_academy.lib.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {
    @NotNull
    @JsonProperty(value = "user_id")
    private UUID userId;
    @NotBlank
    @Email
    private String mail;
    @NotBlank
    private String fio;
    @NotNull
    @JsonProperty(value = "role")
    private UserRole userRole;
}
