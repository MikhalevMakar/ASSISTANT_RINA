package ru.nsu.sber_portal.ccfit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.exceptions.ParseJsonException;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.ChangeOrderDto;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderDto;
import ru.nsu.sber_portal.ccfit.services.OrderService;

import java.util.Optional;


@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(produces = "application/json")
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @ApiOperation("Change count pos in order")
    @PostMapping("/{titleRest}/order/change")
    public HttpStatus changeOrder(@PathVariable String titleRest,
                                  @NotNull ChangeOrderDto orderDto) {

        log.info("Change count dish " + orderDto.getDishId() + " in rest" + titleRest);

        return orderService.changeOrder(orderDto) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create order")
    @PostMapping("/{titleRest}/order")
    public void createOrderToCheck(@PathVariable String titleRest, @NotNull HttpEntity<String> requestEntity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OrderDto orderDto = Optional.ofNullable(objectMapper.readValue(requestEntity.getBody(), OrderDto.class))
                                    .orElseThrow(() -> new ParseJsonException("Error parse OrderDto"));

            log.info("Cart post: " + orderDto);
            log.info("Check: " + titleRest);
            orderService.addNewOrder(orderDto, titleRest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
