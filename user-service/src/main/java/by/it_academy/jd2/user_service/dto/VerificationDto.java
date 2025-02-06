package by.it_academy.jd2.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationDto {
    @NotBlank(message = "Код активации не был передан")
    private String code;
    @NotBlank(message = "email не был передан")
    private String mail;
}
