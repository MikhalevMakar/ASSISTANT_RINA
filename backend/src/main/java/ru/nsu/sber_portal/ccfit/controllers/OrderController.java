package ru.nsu.sber_portal.ccfit.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.*;
import ru.nsu.sber_portal.ccfit.services.OrderService;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/backend", produces = "application/json")
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{titleRest}/order/change")
    public HttpStatus changeOrder(@PathVariable String titleRest,
                                  @RequestBody ChangeOrderDto changeOrderDto) {
        log.info("Change order, body" + changeOrderDto + " in rest" + titleRest);
        orderService.changeOrder(changeOrderDto);
        return HttpStatus.CREATED;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{titleRest}/cart")
    public HttpStatus deleteOrderToCheck(@PathVariable String titleRest,
                                         @RequestBody DeleteOrderDto deleteOrder) {
        log.info("requestEntity: " + deleteOrder + " in rest " + titleRest);
        orderService.deleteOrder(deleteOrder);
        return HttpStatus.CREATED;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{titleRest}/order")
    public HttpStatus createOrderToCheck(@PathVariable String titleRest,
                                         @RequestBody OrderDto orderDto) {
        log.info("Create order to check by info: " + orderDto);
        orderService.addNewOrder(orderDto, titleRest);
        return HttpStatus.CREATED;
    }
}