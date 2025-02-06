package by.it_academy.jd2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationCategoryCreateDto {
    @NotBlank(message = "Поле не заполнено")
    private String title;
}
