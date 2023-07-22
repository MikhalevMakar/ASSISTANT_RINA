package ru.nsu.sber_portal.ccfit.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.*;
import lombok.extern.slf4j.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.DeleteOrderDto;
import ru.nsu.sber_portal.ccfit.services.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(produces = "application/json")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CheckController {

    private final CheckService checkService;

    @GetMapping("/{titleRest}/cart/{numberTable}")
    public CheckTableDto getCart(@PathVariable String titleRest, @PathVariable String numberTable) {
        log.info("Get cart by number table" + numberTable);
        return checkService.getCheck(titleRest,
                                     Integer.parseInt(numberTable));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{titleRest}/cart")
    @ApiOperation("Delete order in check")
    public void deleteOrderToCheck(@PathVariable Long titleRest, @NotNull DeleteOrderDto deleteOrder) {
        log.info("Number table" + deleteOrder.getNumberTable() + "by" + titleRest);
        checkService.deleteOrder(deleteOrder);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping("/{titleRest}/cart/payment")
    @ApiOperation("Delete order in check")
    public CheckTableDto shoppingCartPayment(@PathVariable String titleRest, CheckTableDto checkTableDto) {
        return checkService.payment(titleRest, checkTableDto);
    }
}