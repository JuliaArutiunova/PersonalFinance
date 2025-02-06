package by.it_academy.lib.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDto {
    @PositiveOrZero(message = "Число должно быть положительныи или 0")
    private int page = 0;
    @Positive(message = "Число должно быть больше 0")
    private int size = 20;
}
