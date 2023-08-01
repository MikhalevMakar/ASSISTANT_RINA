package ru.nsu.sber_portal.ccfit.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
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
    public @NotNull Order createOrderByCheckId(@NotNull OrderDto orderDto, @NotNull CheckTable checkTable) {
        log.info("Create order by check id " + checkTable.getId());
        Dish dish = DishService.createDish(orderDto.getDishFindDto(), checkTable.getRestaurant(), dishRepository);
        log.info("Dish id " + dish.getId());
        return ofNullable(orderRepository.findOrderByCheckIdAndDishId(checkTable.getId(), dish.getId()))
            .map(order -> {
                log.info("Order was found by dish id " + orderDto.getDishFindDto().getId());
                order.setCount(order.getCount() + orderDto.getCount());
                order.setPrice(order.getPrice() + (long) dish.getPrice () * orderDto.getCount());
                orderRepository.save(order);
                return order;
            }).orElseGet(() -> {
                log.info("Order wasn't found");
                Order order = OrderMapper.mapToEntity(orderDto);
                order.setDishId(dish.getId());
                order.setPrice((long) dish.getPrice() * orderDto.getCount());
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

        Dish dish = DishService.createDish(orderDto.getDishFindDto(), restaurant, dishRepository);
        checkTable.setCost(checkTable.getCost() + (long) dish.getPrice () * orderDto.getCount());

        settingCheckTable(checkTable, orderDto, dish, restaurant);

        log.info("Check table " + checkTable.getId());

        log.info("OrderDto " + orderDto.getDishFindDto().getId() +
                 " title" + orderDto.getDishFindDto().getTitle());

        Order order = createOrderByCheckId(orderDto, checkTable);
        log.info("Order Id " + order.getId() +
                 " price " + order.getPrice() +
                 " weight " + order.getNumberTable());
    }
}