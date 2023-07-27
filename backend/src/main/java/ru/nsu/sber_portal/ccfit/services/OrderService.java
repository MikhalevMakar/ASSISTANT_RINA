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
import java.util.*;

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

        Order order = Optional.ofNullable(orderRepository.findByDishIdAndNumberTable(changeOrderDto.getDishId(),
                                                                                     changeOrderDto.getNumberTable()))
            .orElseThrow(() -> new NoSuchElementException("Dish id " + changeOrderDto.getDishId() + " wasn't found"));
        order.setCount(changeOrderDto.getCount());
       orderRepository.saveAndFlush(order);
    }

    private Order createOrderByCheckId(@NotNull Long checkId, @NotNull OrderDto orderDto) {
        log.info("Create order by check id " + checkId);

        return ofNullable(orderRepository.findOrderByCheckIdAndDishId(checkId, orderDto.getDishId()))
            .map(order -> {
                log.info("Check was found");
                order.setNumberTable(orderDto.getNumberTable());
                order.setDishId(orderDto.getDishId());
                order.setCount(order.getCount() + orderDto.getCount());
                order.setPrice(order.getPrice() + orderDto.getPrice());
                return order;
            }).orElseGet(() -> OrderMapper.mapToEntity(orderDto));
    }

    private static void settingCheckTable(@NotNull CheckTable checkTable,
                                   @NotNull OrderDto orderDto,
                                   @NotNull Restaurant rest) {

        checkTable.setCost(orderDto.getPrice() + checkTable.getCost());
        checkTable.setNumberTable(orderDto.getNumberTable());
        checkTable.setSessionStatus(SessionStatus.PLACED);
        checkTable.setRestaurant(rest);
    }

    public void addNewOrder(@NotNull OrderDto orderDto, String restName) {
        Restaurant restaurant = createRestaurant(restName);
        log.info("Restaurant id" + restaurant.getId());

        log.info("Add new order by id");

        CheckTable checkTable = createCheckTable(orderDto,
                                                 restaurant,
                                                 checkRepository);

        settingCheckTable(checkTable, orderDto, restaurant);
        checkRepository.saveAndFlush(checkTable);

        log.info("Check table " + checkTable.getId());

        Order order = createOrderByCheckId(checkTable.getId(), orderDto);

        order.setCheck(checkTable);
        orderRepository.saveAndFlush(order);

        checkTable.addNewOrder(order);

        checkRepository.saveAndFlush(checkTable);

        log.info("Order Id " + order.getId() +
                " price " + order.getPrice() +
                " weight " + order.getNumberTable());

        restaurant.addCheckTable(checkTable);
        restaurantRepository.save(restaurant);
    }
}
