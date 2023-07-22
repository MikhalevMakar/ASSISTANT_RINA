package ru.nsu.sber_portal.ccfit.models.dto.orderDto;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeOrderDto extends OrderPattern {
    @NotNull
    boolean isIncrement;
}
