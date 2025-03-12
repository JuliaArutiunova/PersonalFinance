package by.it_academy.lib.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserActionDto {
    @NotNull
    private UserInfoDto userInfoDto;
    @NotNull
    private ActionInfoDto actionInfoDto;
}
