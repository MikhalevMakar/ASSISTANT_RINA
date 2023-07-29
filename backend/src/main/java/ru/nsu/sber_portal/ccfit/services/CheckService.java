package ru.nsu.sber_portal.ccfit.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderDto;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderPattern;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.enums.*;
import ru.nsu.sber_portal.ccfit.models.mappers.*;
import ru.nsu.sber_portal.ccfit.repositories.*;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class CheckService extends OrderCheckServiceUtility {

    public CheckService(OrderRepository orderRepository,
                        RestaurantRepository restRepository,
                        CheckTableRepository checkRepository,
                        DishRepository dishRepository) {

        super(orderRepository,
              restRepository,
              checkRepository,
              dishRepository);
    }

    @Transactional
    public CheckTableDto getCheck(String nameRest, Integer numberTable) {
        log.info("Method get check for restaurant " + nameRest);

        Restaurant restaurant = createRestaurant(nameRest);

        log.info(" Id restaurant " + restaurant.getId());

        CheckTable checkTable = createCheckTable(numberTable, restaurant);
        checkTable.setRestaurant(restaurant);

        CheckTableDto checkTableDto = CheckMapper.mapperToDto(checkTable);

        log.info("Create check table dto");

        setListOrderFromEntityToDto(checkTable, checkTableDto);
        return checkTableDto;
    }

    @Transactional
    public void checkDeleteOrder(@NotNull Order mainOrder,
                                 @NotNull OrderPattern payedOrderDto) {

        log.info("Call check delete order");

        log.info("Order count " + mainOrder.getCount() + " and " + payedOrderDto.getCount());
        mainOrder.setCount(mainOrder.getCount() - payedOrderDto.getCount());

        if(Objects.equals(mainOrder.getCount(), EMPTY_ORDER))
            deleteOrder(mainOrder);
        else
            orderRepository.save(mainOrder);
    }

    @Transactional
    protected CheckTable createPaymentCheckTable(@NotNull Restaurant restaurant,
                                                 @NotNull CheckTableDto paymentCheckTableDto) {
        CheckTable paymentCheckTable = CheckMapper.mapperToEntity(paymentCheckTableDto);

        paymentCheckTable.setSessionStatus(SessionStatus.FINALIZED);
        paymentCheckTable.setRestaurant(restaurant);
        restaurant.addCheckTable(paymentCheckTable);
        checkRepository.saveAndFlush(paymentCheckTable);
        return paymentCheckTable;
    }

    @Transactional
    public void payment(@NotNull String titleRest,
                        @NotNull CheckTableDto paymentCheckTableDto) {

        Restaurant restaurant = createRestaurant(titleRest);
        log.info("Method payment " + paymentCheckTableDto);

        paymentCheckTableDto.setRestId(restaurant.getId());
        CheckTable paymentCheckTable = createPaymentCheckTable(restaurant, paymentCheckTableDto);

        log.info("Payment " + paymentCheckTable);

        CheckTable mainCheckTable = createCheckTable(paymentCheckTable.getNumberTable(),
                                                     restaurant);
        mainCheckTable
            .setCost(mainCheckTable.getCost() - paymentCheckTable.getCost());

        checkRepository.saveAndFlush(mainCheckTable);

        log.info("Main check table toString(): " + mainCheckTable);

        log.info("Payed check table toString() " + paymentCheckTable);

        for(var orderDto : paymentCheckTableDto.getListOrderDto()) {
            mainCheckTable.getOrders().stream()
                .filter(o -> Objects.equals(o.getDishId(), orderDto.getDishId()))
                .forEach(o -> checkDeleteOrder(o, orderDto));
        }
    }
}