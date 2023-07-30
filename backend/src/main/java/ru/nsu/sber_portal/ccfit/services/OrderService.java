package ru.nsu.sber_portal.ccfit.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.enums.SessionStatus;
import ru.nsu.sber_portal.ccfit.models.mappers.OrderMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;

import javax.transaction.Transactional;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
public class OrderService extends OrderCheckServiceUtility {

    public OrderService(OrderRepository orderRepository,
                        RestaurantRepository restaurantRepository,
                        CheckTableRepository checkRepository,
                        DishRepository dishRepository) {

        super(orderRepository,
              restaurantRepository,
              checkRepository,
              dishRepository);
    }

    @Transactional
    public void changeOrder(@NotNull ChangeOrderDto changeOrderDto) {
        log.info("Change order");
        Order order = findOrder(changeOrderDto);
        order.setCount(changeOrderDto.getCount());
        orderRepository.saveAndFlush(order);
    }

    @Transactional
    public Order createOrderByCheckId(@NotNull Long checkId, @NotNull OrderDto orderDto, CheckTable checkTable) {
        log.info("Create order by check id " + checkId);

        return ofNullable(orderRepository.findOrderByCheckIdAndDishId(checkId, orderDto.getDishId()))
            .map(order -> {
                log.info("Order was found by dish id " + orderDto.getDishId());
                order.setCount(order.getCount() + orderDto.getCount());
                order.setPrice(order.getPrice() + orderDto.getPrice());
                orderRepository.save(order);
                return order;
            }).orElseGet(() -> {
                log.info("Order wasn't found");

                Order order = OrderMapper.mapToEntity(orderDto);
                order.setCheck(checkTable);
                checkTable.addNewOrder(order);
                checkRepository.save(checkTable);
                return order;
            });
    }

    @Transactional
    public void addNewOrder(@NotNull OrderDto orderDto, String restName) {
        Restaurant restaurant = createRestaurant(restName);
        log.info("Restaurant id {}, Add new order", restaurant.getId());

        CheckTable checkTable = createCheckTable(orderDto.getNumberTable(),
                                                 restaurant);

        settingCheckTable(checkTable, orderDto, restaurant);

        log.info("Check table " + checkTable.getId());

        Order order = createOrderByCheckId(checkTable.getId(), orderDto, checkTable);
        log.info("Order Id " + order.getId() +
                 " price " + order.getPrice() +
                 " weight " + order.getNumberTable());
    }
}