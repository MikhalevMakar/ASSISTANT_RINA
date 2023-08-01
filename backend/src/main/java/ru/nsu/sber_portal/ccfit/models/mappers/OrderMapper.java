package ru.nsu.sber_portal.ccfit.models.mappers;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.*;
import ru.nsu.sber_portal.ccfit.models.dto.DishFindDto;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderDto;
import ru.nsu.sber_portal.ccfit.models.entity.Order;

@AllArgsConstructor
public class OrderMapper {
    public static void setOrderEntity(@NotNull Order order,
                                      @NotNull OrderDto orderDto) {

        order.setCount(orderDto.getCount());
        order.setNumberTable(orderDto.getNumberTable());
        order.setDishId(orderDto.getDishFindDto().getId());
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
        orderDto.setDishFindDto(new DishFindDto(order.getDishId(), null));
        return orderDto;
    }
}
