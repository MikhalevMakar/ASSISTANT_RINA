package ru.nsu.sber_portal.ccfit.services;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.nsu.sber_portal.ccfit.exceptions.FindRestByTitleException;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderPattern;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.enums.SessionStatus;
import ru.nsu.sber_portal.ccfit.repositories.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Slf4j
public class OrderCheckServiceUtility {

    protected static final Long EMPTY_ORDER = 0L;

    protected final OrderRepository orderRepository;

    protected final RestaurantRepository restaurantRepository;

    protected final CheckTableRepository checkRepository;

    protected final DishRepository dishRepository;

    protected CheckTable createCheckTable(@NotNull OrderPattern orderDto,
                                          @NotNull Restaurant restaurant,
                                          @NotNull CheckTableRepository checkRepository) {
        return ofNullable(checkRepository
            .findByNumberTableAndRestaurantIdAndSessionStatus(
                orderDto.getNumberTable(),
                restaurant.getId(),
                SessionStatus.PLACED
            ))
            .orElseGet(CheckTable::new);
    }

    protected Restaurant createRestaurant(String nameRest){
        return restaurantRepository.findByNameRestaurant (nameRest)
            .orElseThrow (() -> new FindRestByTitleException ("No such rest"));
    }

    protected Order createOrder(@NotNull OrderPattern deleteOrderDto) {
        return Optional.ofNullable(orderRepository.findByDishIdAndNumberTable(deleteOrderDto.getDishId(),
                deleteOrderDto.getNumberTable()))
            .orElseThrow(() -> new NoSuchElementException ("Dish id " + deleteOrderDto.getDishId() + " wasn't found"));
    }
}
