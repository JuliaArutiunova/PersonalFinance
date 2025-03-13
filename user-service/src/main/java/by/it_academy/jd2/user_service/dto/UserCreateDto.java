package by.it_academy.jd2.user_service.dto;

import by.it_academy.jd2.user_service.dao.entity.UserStatus;

import by.it_academy.lib.enums.UserRole;
import by.it_academy.lib.validation.EnumValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Data
@Builder
public class UserCreateDto {

    @NotBlank(message = "Поле не заполнено")
    @Email(message = "Некорректный email")
    private String mail;

    @NotBlank(message = "Поле не заполнено")
    private String fio;

    @NotBlank(message = "Поле не заполнено")
    @EnumValue(enumClass = UserRole.class, message = "Некорректная роль")
    private String role;

    @NotBlank(message = "Поле не заполнено")
    @EnumValue(enumClass = UserStatus.class, message = "Некорректный статус")
    private String status;

    @NotBlank(message = "Поле не заполнено")
    @Size(min = 4, message = "Пароль должен быть не менее 4 символов")
    private String password;

}
