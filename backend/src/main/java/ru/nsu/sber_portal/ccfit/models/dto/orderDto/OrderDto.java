package ru.nsu.sber_portal.ccfit.models.dto.orderDto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import ru.nsu.sber_portal.ccfit.models.dto.DishDto;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class OrderDto extends OrderPattern {

    private Long price;
    private DishDto dishDto;
}