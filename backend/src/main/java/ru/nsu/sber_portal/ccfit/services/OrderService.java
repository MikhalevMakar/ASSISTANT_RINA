package ru.nsu.sber_portal.ccfit.services;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private static final Long EMPTY_ORDER = 0L;

    private static final Short INCREASE = 0;

    private final OrderRepository orderRepository;

    private final RestaurantRepository restRepository;

    private final CheckTableRepository checkRepository;

    public boolean increaseOrder(@NotNull Order order) {
       order.setCount(order.getCount() + INCREASE);
       return true;
    }

    public boolean decreaseOrder(@NotNull Order order) {
        boolean statusReturn = order.getCount() > EMPTY_ORDER;
        order.setCount(statusReturn ? order.getCount() - INCREASE : order.getCount());
        return statusReturn;
    }

    @Transactional
    public boolean changeOrder(@NotNull ChangeOrderDto changeOrderDto) {

        Order order = Optional.ofNullable(orderRepository.findByDishIdAndNumberTable(changeOrderDto.getDishId(),
                                                                                     changeOrderDto.getNumberTable()))
            .orElseThrow(() -> new NoSuchElementException("Dish id " + changeOrderDto.getDishId() + " wasn't found"));

        boolean statusReturn =
            (changeOrderDto.isIncrement()) ? increaseOrder(order) : decreaseOrder(order);

       orderRepository.saveAndFlush(order);
       return statusReturn;
    }

    private Order createOrderByCheckId(@NotNull Long checkId, @NotNull OrderDto orderDto) {
        log.info("Create order by check id " + checkId);

        return ofNullable(orderRepository.findOrderByCheckIdAndDishId(checkId, orderDto.getDishId()))
            .map(order -> {
                log.info("Check was found");
                order.setCount(order.getCount() + orderDto.getCount());
                order.setPrice(order.getPrice() + orderDto.getPrice());
                return order;
            }).orElseGet(() -> OrderMapper.mapToEntity(orderDto));
    }

    private void settingCheckTable(@NotNull CheckTable checkTable,
                                   @NotNull OrderDto orderDto,
                                   @NotNull Optional<Restaurant> rest) {

        checkTable.setCost(orderDto.getPrice() + checkTable.getCost());
        checkTable.setNumberTable(orderDto.getNumberTable());
        checkTable.setSessionStatus(SessionStatus.PLACED);
        checkTable.setRestaurant(rest.orElse(null));
    }

    public void addNewOrder(@NotNull OrderDto orderDto, String restName) {
        Optional<Restaurant> restaurant = restRepository.findByNameRestaurant(restName);

        log.info("Add new order by id");

        CheckTable checkTable = CheckService.createCheckTable(orderDto,
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

        restaurant.ifPresent(r -> r.addCheckTable(checkTable));
        restaurant.ifPresent(restRepository::save);
    }
}
