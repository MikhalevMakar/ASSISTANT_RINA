package ru.nsu.sber_portal.ccfit.models.dto.orderDto;

import lombok.*;
import ru.nsu.sber_portal.ccfit.models.dto.DishDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends OrderPattern {

    private Long price; //TODO delete

    private DishDto dishDto;

}