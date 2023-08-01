package ru.nsu.sber_portal.ccfit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.exceptions.ParseJsonException;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;
import ru.nsu.sber_portal.ccfit.services.OrderService;

import java.util.Optional;


@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/backend", produces = "application/json")
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final OrderService orderService;

    @PostMapping("/{titleRest}/order/change")
    public HttpStatus changeOrder(@PathVariable String titleRest,
                                  @NotNull HttpEntity<String> requestEntity) {
        log.info("Change order, body" + requestEntity.getBody());
        ChangeOrderDto changeOrderDto;
        try {
            changeOrderDto = Optional.ofNullable(
                objectMapper.readValue(requestEntity.getBody(), ChangeOrderDto.class))
                .orElseThrow(() -> new ParseJsonException("Error parse OrderDto"));
        } catch (JsonProcessingException e) {
            log.warn("During call method readValue " + e);
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }

        log.info("Change count dish " + changeOrderDto.getDishFindDto().getId() + " in rest" + titleRest);
        orderService.changeOrder(changeOrderDto);
        return HttpStatus.CREATED;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{titleRest}/order")
    public HttpStatus createOrderToCheck(@PathVariable String titleRest,
                                         @NotNull HttpEntity<String> requestEntity) {
        log.info("Create order to check by info: " + requestEntity.getBody());
        try {
            OrderDto orderDto = Optional.ofNullable(
                objectMapper.readValue(requestEntity.getBody(), OrderDto.class))
                                    .orElseThrow(() -> new ParseJsonException("Error parse OrderDto"));

            log.info("Cart post: " + orderDto + ", check: " + titleRest);
            log.info("Order dto " + orderDto.getDishFindDto().getId());
            orderService.addNewOrder(orderDto, titleRest);
            return HttpStatus.CREATED;
        } catch (JsonProcessingException e) {
            log.warn("During call method readValue " + e);
        }
        return HttpStatus.BAD_REQUEST;
    }
}