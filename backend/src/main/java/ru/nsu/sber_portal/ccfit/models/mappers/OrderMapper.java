package ru.nsu.sber_portal.ccfit.models.mappers;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderDto;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderPattern;
import ru.nsu.sber_portal.ccfit.models.entity.Order;

@AllArgsConstructor
public class OrderMapper {
    public static void setOrderPatternEntity(@NotNull OrderPattern orderDto,
                                             @NotNull OrderPattern order) {
        order.setCount(orderDto.getCount());
        order.setNumberTable(orderDto.getNumberTable());
        order.setDishId(orderDto.getDishId());
    }

    public static void setOrderEntity(@NotNull Order order,
                                      @NotNull OrderDto orderDto) {
        setOrderPatternEntity(orderDto, order);
        order.setPrice(orderDto.getPrice());
    }

    @Contract("_ -> new")
    public static @NotNull Order mapToEntity(@NotNull OrderDto orderDto) {
        Order order = new Order();
        setOrderEntity(order, orderDto);
        return order;
    }

    public static @NotNull OrderDto mapToDto(@NotNull Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setNumberTable(order.getNumberTable());
        orderDto.setCount(order.getCount());
        orderDto.setPrice(order.getPrice());
        orderDto.setDishId(order.getDishId());
        return orderDto;
    }
}
