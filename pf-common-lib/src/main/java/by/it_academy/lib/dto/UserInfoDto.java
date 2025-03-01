package by.it_academy.lib.dto;

import by.it_academy.lib.enums.UserRole;
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
    private UUID userId;
    private String email;
    private String fio;
    private UserRole userRole;
}
