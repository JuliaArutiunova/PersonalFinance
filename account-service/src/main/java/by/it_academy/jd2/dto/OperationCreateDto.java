package by.it_academy.jd2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationCreateDto {
    
    @NotNull(message = "Поле не заполнено")
    private Long date;

    @NotBlank(message = "Поле не заполнено")
    private String description;

    @NotNull(message = "Поле не заполнено")
    private UUID category;

    @NotNull(message = "Поле не заполнено")
    private BigDecimal value;

    @NotNull(message = "Поле не заполнено")
    private UUID currency;


}
