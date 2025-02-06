package by.it_academy.jd2.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class UserRegistrationDto {
    @NotBlank(message = "Поле не заполнено")
    @Email(message = "Некорректный email")
    private String mail;

    @NotBlank(message = "Поле не заполнено")
    private String fio;

    @NotBlank(message = "Поле не заполнено")
    private String password;
}
