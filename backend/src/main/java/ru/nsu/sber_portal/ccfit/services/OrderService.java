package ru.nsu.sber_portal.ccfit.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.exceptions.DishNotFoundException;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.mappers.OrderMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;

import javax.transaction.Transactional;

import java.util.Optional;

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
    public Order findOrder(@NotNull CheckTable checkTable, @NotNull OrderDto orderDto) {
        Dish dish = DishService.createDish(orderDto.getDishFindDto(), checkTable.getRestaurant(), dishRepository);
        return Optional.ofNullable(orderDto.getDishDto().getId())
            .map(id -> orderRepository.findOrderByCheckIdAndDishId(checkTable.getId(), dish.getId()))
            .orElseThrow(() -> new DishNotFoundException(checkTable.getRestaurant().getNameRestaurant()));
    }


    @Transactional
    public Order createOrderByCheckId(@NotNull OrderDto orderDto, CheckTable checkTable) {
        log.info("Create order by check id " + checkTable.getId());

        return ofNullable(findOrder(checkTable, orderDto))
            .map(order -> {
                log.info("Order was found by dish id " + orderDto.getDishFindDto().getId());
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
        Restaurant restaurant = RestaurantService.createRestaurant(restName, restRepository);
        log.info("Restaurant id {}, Add new order", restaurant.getId());

        CheckTable checkTable = createCheckTable(orderDto.getNumberTable(),
                                                 restaurant);

        settingCheckTable(checkTable, orderDto, restaurant);

        log.info("Check table " + checkTable.getId());

        Order order = createOrderByCheckId(orderDto, checkTable);
        log.info("Order Id " + order.getId() +
                 " price " + order.getPrice() +
                 " weight " + order.getNumberTable());
    }
}