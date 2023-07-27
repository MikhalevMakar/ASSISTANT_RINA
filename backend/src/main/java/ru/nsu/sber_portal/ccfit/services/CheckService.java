package ru.nsu.sber_portal.ccfit.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;
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
    public void deleteOrder(@NotNull OrderPattern deleteOrderDto) {
        Order order = createOrder(deleteOrderDto);
        CheckTable checkTable = order.getCheck();
        checkTable.getOrders().remove(order);
        orderRepository.delete(order);
        orderRepository.flush();
    }

    @Transactional
    public void setListOrderFromEntityToDto(@NotNull CheckTable checkTable,
                                             @NotNull CheckTableDto checkTableDto) {
        for(var order : checkTable.getOrders()) {

            OrderDto orderDto = OrderMapper.mapToDto(order);
            log.info("GET DISH ID " + order.getDishId());

            orderDto.setDishId(order.getDishId());

            orderDto.setDishDto(
                DishDtoMapper.mapToDto(dishRepository.getReferenceById(order.getDishId())));

            checkTableDto.addOrderDto(orderDto);
        }
    }

    @Transactional
    public CheckTableDto getCheck(String nameRest, Integer numberTable) {
        log.info("Method get check");

        Restaurant restaurant = createRestaurant(nameRest);

        log.info(" Id restaurant " + restaurant.getId());

        CheckTable checkTable = Optional.ofNullable(checkRepository
                                     .findByNumberTableAndRestaurantIdAndSessionStatus(numberTable,
                                                                                       restaurant.getId(),
                                                                                       SessionStatus.PLACED))
                                .orElseGet(CheckTable::new);

        checkTable.setRestaurant(restaurant);
        CheckTableDto checkTableDto = CheckMapper.mapperToDto(checkTable);

        log.info("Create check table dto");

        setListOrderFromEntityToDto(checkTable, checkTableDto);

        log.info("Create object check id " + checkTable + "");
        return checkTableDto;
    }

    private void checkDeleteOrder(@NotNull OrderPattern mainOrderDto,
                                  @NotNull OrderPattern payedOrderDto) {

        log.info("Call check delete order");

        Order order = orderRepository.findByDishIdAndNumberTable(mainOrderDto.getDishId(),
            mainOrderDto.getNumberTable());

        log.info("Order count " + order.getCount() + " and " + mainOrderDto.getCount());
        order.setCount(mainOrderDto.getCount() - payedOrderDto.getCount());

        if(Objects.equals(order.getCount(), EMPTY_ORDER)) {
            log.info("Delete order");
            deleteOrder(mainOrderDto);
        }
        orderRepository.flush();
    }

    @Transactional
    public void payment(String titleRest,
                        @NotNull CheckTableDto checkTableDto) {

        Restaurant restaurant = createRestaurant(titleRest);
        log.info("Method payment " + checkTableDto);

        checkTableDto.setRestId(restaurant.getId());
        CheckTable paymentCheckTable = CheckMapper.mapperToEntity(checkTableDto);

        paymentCheckTable.setSessionStatus(SessionStatus.FINALIZED);
        paymentCheckTable.setRestaurant(restaurant);
        checkRepository.saveAndFlush(paymentCheckTable);
        restaurant.addCheckTable(paymentCheckTable);

        log.info("Payment " + paymentCheckTable);

        CheckTable mainCheckTable = createCheckTable(Objects.requireNonNull(checkTableDto.getListOrderDto()
                                                        .stream().findFirst().orElse(null)),
                                                     restaurant,
                                                     checkRepository);

        mainCheckTable.setNumberTable(checkTableDto.getNumberTable());
        mainCheckTable.setSessionStatus(SessionStatus.PLACED);
        mainCheckTable.setRestaurant(restaurant);

        CheckTableDto mainCheckTableDto = CheckMapper.mapperToDto(mainCheckTable);
        setListOrderFromEntityToDto(mainCheckTable, mainCheckTableDto);

        log.info("Main check table toString(): " + mainCheckTableDto);

        log.info("Payed check table toString() " + checkTableDto);

        for(var orderDto : checkTableDto.getListOrderDto()) {
            mainCheckTableDto.getListOrderDto().stream()
                .filter(o -> Objects.equals(o.getDishId(), orderDto.getDishId()))
                .forEach(o -> checkDeleteOrder(o, orderDto));
        }

        mainCheckTable
            .setCost(mainCheckTableDto.getCost() - paymentCheckTable.getCost());

        if(mainCheckTable.getCost().equals(EMPTY_ORDER))
            checkRepository.delete(mainCheckTable);
        else
            checkRepository.saveAndFlush(mainCheckTable);
    }
}