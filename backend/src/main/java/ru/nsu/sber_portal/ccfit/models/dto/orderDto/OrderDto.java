package ru.nsu.sber_portal.ccfit.models.dto.orderDto;

import lombok.*;
import ru.nsu.sber_portal.ccfit.models.dto.DishDto;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends OrderPattern {

    private Long price;

    private DishDto dishDto;

}