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
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.ChangeOrderDto;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderDto;
import ru.nsu.sber_portal.ccfit.services.OrderService;


@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(produces = "application/json")
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @ApiOperation("Change count pos in order")
    @PostMapping("/{title_rest}/order/change")
    public HttpStatus changeOrder(@PathVariable String title_rest,
                                  @NotNull ChangeOrderDto orderDto) {

        log.info("Change count dish " + orderDto.getDishId() + " in rest" + title_rest);

        return orderService.changeOrder(orderDto) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Create order")
    @PostMapping("/{title_rest}/order")
    public void createOrderToCheck(@PathVariable String title_rest, @NotNull HttpEntity<String> requestEntity) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OrderDto orderDto = objectMapper.readValue(requestEntity.getBody(), OrderDto.class);
            log.info("Cart post: " + orderDto);
            log.info("Check: " + title_rest);
            orderService.addNewOrder(orderDto, title_rest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
