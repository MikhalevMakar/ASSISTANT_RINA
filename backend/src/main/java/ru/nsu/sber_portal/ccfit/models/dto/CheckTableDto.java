package ru.nsu.sber_portal.ccfit.models.dto;

import lombok.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckTableDto {
    private Set<OrderPattern> listOrderDto = new HashSet<>();

    private Integer numberTable;

    private Long restId;

    private Long cost = 0L;

    public void addOrderDto(OrderDto orderDto) {
        listOrderDto.add(orderDto);
    }
}