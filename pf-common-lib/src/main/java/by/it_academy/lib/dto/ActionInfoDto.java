package by.it_academy.lib.dto;

import by.it_academy.lib.enums.EssenceType;
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
    private UUID userId;
    private String text;
    private EssenceType essenceType;
    private UUID entityId;
}
