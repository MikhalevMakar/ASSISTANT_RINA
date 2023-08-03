package ru.nsu.sber_portal.ccfit.models.dto.orderDto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.sber_portal.ccfit.models.dto.DishFindDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class OrderPattern {

    protected DishFindDto dishFindDto = new DishFindDto();

    protected Integer numberTable;

    protected Long count;
}