package by.it_academy.jd2.dto;

import by.it_academy.jd2.validation.CurrencyCode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CurrencyCreateDto {
    @NotBlank(message = "Поле не заполнено")
    @CurrencyCode(message = "Валюты с таким кодом не существует")
    private String title;

    @NotBlank(message = "Поле не заполнено")
    private String description;
}
