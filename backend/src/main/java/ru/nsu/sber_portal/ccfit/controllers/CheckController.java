package ru.nsu.sber_portal.ccfit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.*;
import lombok.extern.slf4j.*;
import org.jetbrains.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.DeleteOrderDto;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderDto;
import ru.nsu.sber_portal.ccfit.services.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(produces = "application/json")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CheckController {

    private final CheckService checkService;

    @GetMapping("/{title_rest}/cart/{number_table}")
    public CheckTableDto getCart(@PathVariable String title_rest, @PathVariable String number_table) {
        log.info("Get cart by number table" + number_table);
        return checkService.getCheck(title_rest,
                                     Integer.parseInt(number_table));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{title_rest}/cart")
    @ApiOperation("Delete order in check")
    public void deleteOrderToCheck(@PathVariable Long title_rest, DeleteOrderDto deleteOrder) {
        log.info("Number table" + deleteOrder.getNumberTable() + "by" + title_rest);
        checkService.deleteOrder(deleteOrder);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{title_rest}/cart/payment")
    @ApiOperation("Delete order in check")
    public CheckTableDto shoppingCartPayment(@PathVariable String title_rest, CheckTableDto checkTableDto) {
        return checkService.payment(title_rest, checkTableDto);
    }
}