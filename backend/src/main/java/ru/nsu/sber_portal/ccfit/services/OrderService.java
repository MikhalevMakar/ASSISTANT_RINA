package ru.nsu.sber_portal.ccfit.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.mappers.OrderMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;

import javax.transaction.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
public class OrderService extends OrderCheckServiceUtility {

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CheckTableRepository checkRepository,
                        DishRepository dishRepository,
                        RestaurantService restaurantService) {

        super(restaurantService,
              orderRepository,
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
    protected Order findOrder(@NotNull OrderPattern deleteOrderDto) {
        return Optional.ofNullable(orderRepository.findByDishIdAndNumberTable(deleteOrderDto.getDishFindDto().getId(),
                deleteOrderDto.getNumberTable()))
            .orElseThrow(() -> new NoSuchElementException ("Dish id " + deleteOrderDto.getDishFindDto()
                .getId() + " wasn't found"));
    }

    @Transactional
    public void deleteOrder(@NotNull OrderPattern deleteOrderDto) {
        Order order = findOrder(deleteOrderDto);
        log.info("Delete order by id " + order.getId());
        order.getCheck().getOrders().remove(order);
        orderRepository.delete(order);
    }

    @Transactional
    public void addNewOrder(@NotNull OrderDto orderDto, String restName) {
        Restaurant restaurant = restaurantService.createRestaurant(restName);
        log.info("Restaurant id {}, Add new order", restaurant.getId());

        CheckTable checkTable = createCheckTable(orderDto.getNumberTable(),
                                                 restaurant);

        Dish dish = DishService.createDish(orderDto.getDishFindDto(), restaurant, dishRepository);
        checkTable.setCost(checkTable.getCost() + (long) dish.getPrice () * orderDto.getCount());

        settingCheckTable(checkTable,
                          orderDto.getNumberTable(),
                          dish.getPrice(),
                          restaurant);

        log.info("Check table " + checkTable.getId());

        Order order = createOrderByCheckId(orderDto, checkTable);
        log.info("Order Id " + order.getId() +
                 " price " + order.getPrice() +
                 " weight " + order.getNumberTable());
    }
}