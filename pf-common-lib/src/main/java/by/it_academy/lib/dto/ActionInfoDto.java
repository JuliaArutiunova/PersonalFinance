package by.it_academy.lib.dto;

import by.it_academy.lib.enums.EssenceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActionInfoDto {
    @NotNull
    private UUID userId;
    @NotBlank
    private String text;
    @NotNull
    private EssenceType essenceType;
    @NotNull
    private UUID entityId;
}
