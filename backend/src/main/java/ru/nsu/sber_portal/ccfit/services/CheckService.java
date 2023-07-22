package ru.nsu.sber_portal.ccfit.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.exceptions.*;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.enums.*;
import ru.nsu.sber_portal.ccfit.models.mappers.*;
import ru.nsu.sber_portal.ccfit.repositories.*;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckService {

    private static final Long EMPTY_ORDER = 0L;

    private final CheckTableRepository checkRepository;

    private final RestaurantRepository restaurantRepository;

    private final DishRepository dishRepository;

    private final OrderRepository orderRepository;

    @Transactional
    public void deleteOrder(@NotNull DeleteOrderDto deleteOrderDto) {
        Order order = Optional.ofNullable(orderRepository.findByDishIdAndNumberTable(deleteOrderDto.getDishId(),
                deleteOrderDto.getNumberTable()))
            .orElseThrow(() -> new NoSuchElementException ("Dish id " + deleteOrderDto.getDishId() + " wasn't found"));

        orderRepository.deleteById(order.getId());
    }

    public static CheckTable createCheckTable(@NotNull OrderDto orderDto,
                                              @NotNull Optional<Restaurant> restaurant,
                                              @NotNull CheckTableRepository checkRepository) {
        return ofNullable(checkRepository
            .findByNumberTableAndRestaurantIdAndSessionStatus(
                orderDto.getNumberTable(),
                restaurant.map(Restaurant::getId).orElseThrow(
                    () -> new FindByIdException("Exception on find restaurant")),
                SessionStatus.PLACED
            ))
            .orElseGet(CheckTable::new);
    }

    @Transactional
    public CheckTableDto getCheck(String nameRest, Integer numberTable) {
        log.info("Method get check");

        Restaurant restaurant = restaurantRepository.findByNameRestaurant(nameRest)
            .orElseThrow(() ->new FindRestByTitleException ("No such rest"));


        log.info(" Id restaurant " + restaurant.getId());

        CheckTable checkTable = Optional.ofNullable(checkRepository
                                     .findByNumberTableAndRestaurantIdAndSessionStatus(numberTable,
                                                                                       restaurant.getId(),
                                                                                       SessionStatus.PLACED))
                                .orElseGet(CheckTable::new);

        CheckTableDto checkTableDto = CheckMapper.mapperToDto(checkTable);

        for(var order : checkTable.getOrders()) {
            OrderDto orderDto = OrderMapper.mapToDto(order);
            orderDto.setDishDto(
                DishDtoMapper.mapToDto(dishRepository.getById(order.getDishId())));
            checkTableDto.addOrderDto(orderDto);
        }

        log.info("Create object check id " + checkTable.getId());
        return checkTableDto;
    }

    @Transactional
    public CheckTableDto payment(String titleRest, @NotNull CheckTableDto checkTableDto) {
        CheckTable paymentCheckTable = CheckMapper.mapperToEntity(checkTableDto)    ;

        paymentCheckTable.setSessionStatus(SessionStatus.FINALIZED);
        checkRepository.saveAndFlush(paymentCheckTable);

        CheckTable mainCheckTable = createCheckTable(Objects.requireNonNull(checkTableDto.getListOrderDto()
                                                                                .stream().findFirst().orElse(null)),
                                                     restaurantRepository.findByNameRestaurant(titleRest),
                                                     checkRepository);



        CheckTableDto mainCheckTableDto = CheckMapper.mapperToDto(mainCheckTable);

        Long paymentCost = EMPTY_ORDER;

        for(var orderDto : mainCheckTableDto.getListOrderDto()) {
            paymentCost += orderDto.getPrice();
            deleteOrder(orderDto);
        }

        mainCheckTableDto.getListOrderDto()
                        .removeAll(checkTableDto.getListOrderDto());

        mainCheckTableDto
            .setCost(mainCheckTableDto.getCost() - paymentCost);

        if(mainCheckTableDto.getCost().equals(EMPTY_ORDER))
            checkRepository.delete(mainCheckTable);

        return mainCheckTableDto;
    }
}