package ru.nsu.sber_portal.ccfit.models.mappers;

import org.jetbrains.annotations.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderDto;
import ru.nsu.sber_portal.ccfit.models.entity.Order;

public class OrderMapper {

    @Contract("_ -> new")
    public static @NotNull Order mapToEntity(@NotNull OrderDto orderDto) {
        Order order =  new Order();

        order.setNumberTable(orderDto.getNumberTable());
        order.setCount(orderDto.getCount());
        order.setPrice(orderDto.getPrice());
        order.setDishId(orderDto.getDishId());

        return order;
    }

    public static @NotNull OrderDto mapToDto(@NotNull Order order) {
        OrderDto orderDto = new OrderDto ();
        orderDto.setNumberTable(order.getNumberTable());
        orderDto.setCount(order.getCount());
        orderDto.setPrice(order.getPrice());
        return orderDto;
    }
}
