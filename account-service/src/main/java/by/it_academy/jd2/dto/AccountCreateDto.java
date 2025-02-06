package by.it_academy.jd2.dto;

import by.it_academy.jd2.dao.entity.AccountType;
import by.it_academy.lib.validation.EnumValue;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountCreateDto {
    @NotBlank(message = "Поле не заполнено")
    private String title;

    @NotBlank(message = "Поле не заполнено")
    private String description;

    @EnumValue(enumClass = AccountType.class)
    @NotBlank(message = "Поле не заполнено")
    private String type;

    @NotNull(message = "Поле не заполнено")
    private UUID currency;
}
