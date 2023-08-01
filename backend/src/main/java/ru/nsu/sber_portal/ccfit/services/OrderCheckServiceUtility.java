package ru.nsu.sber_portal.ccfit.services;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.nsu.sber_portal.ccfit.exceptions.*;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.enums.SessionStatus;
import ru.nsu.sber_portal.ccfit.models.mappers.*;
import ru.nsu.sber_portal.ccfit.repositories.*;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Slf4j
public class OrderCheckServiceUtility {

    protected static final Integer EMPTY_ORDER = 0;

    protected final OrderRepository orderRepository;

    protected final RestaurantRepository restRepository;

    protected final CheckTableRepository checkRepository;

    protected final DishRepository dishRepository;

    protected CheckTable createCheckTable(@NotNull Integer numberTable,
                                          @NotNull Restaurant restaurant) {
        return ofNullable(checkRepository
            .findByNumberTableAndRestaurantIdAndSessionStatus(
                numberTable,
                restaurant.getId(),
                SessionStatus.PLACED
            ))
            .orElseGet(() -> {
                CheckTable checkTable = new CheckTable();
                checkTable.setNumberTable(numberTable);
                checkTable.setRestaurant(restaurant);
                checkTable.setSessionStatus(SessionStatus.PLACED);
                restaurant.addCheckTable(checkTable);
                checkRepository.save(checkTable);
                return checkTable;
            });
    }

    @Transactional
    protected Order findOrder(@NotNull OrderPattern deleteOrderDto) {
        return Optional.ofNullable(orderRepository.findByDishIdAndNumberTable(deleteOrderDto.getDishFindDto().getId(),
                                   deleteOrderDto.getNumberTable()))
            .orElseThrow(() -> new NoSuchElementException("Dish id " + deleteOrderDto.getDishFindDto()
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
    public void setListOrderFromEntityToDto(@NotNull CheckTable checkTable,
                                            @NotNull CheckTableDto checkTableDto) {
        for(var order : checkTable.getOrders()) {

            OrderDto orderDto = OrderMapper.mapToDto(order);
            log.info("Info order dish id " + order.getDishId() +
                     " count " + order.getCount() +
                     " price " + order.getPrice());

            Dish dish = dishRepository.findById(order.getDishId())
                .orElseThrow(() -> new DishNotFoundException ("Dish id " + order.getDishId()));

            orderDto.setDishDto(
                DishMapper.mapperToDto(dish)
            );

            checkTableDto.addOrderDto(orderDto);
        }
    }

    public void settingCheckTable(@NotNull CheckTable checkTable,
                                  @NotNull OrderDto orderDto,
                                  @NotNull Dish dish,
                                  @NotNull Restaurant rest) {

        log.info("Setting check table");
        checkTable.setCost(dish.getPrice() + checkTable.getCost());
        checkTable.setNumberTable(orderDto.getNumberTable());
        checkTable.setSessionStatus(SessionStatus.PLACED);
        checkTable.setRestaurant(rest);
    }
}