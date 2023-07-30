package ru.nsu.sber_portal.ccfit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.exceptions.ParseJsonException;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.DeleteOrderDto;
import ru.nsu.sber_portal.ccfit.services.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/backend", produces = "application/json")
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
    public HttpStatus deleteOrderToCheck(@PathVariable String titleRest,
                                         @NotNull HttpEntity<String> requestEntity) {

        ObjectMapper objectMapper = new ObjectMapper();
        log.info("requestEntity: " + requestEntity.getBody());
        try {

            DeleteOrderDto deleteOrder = Optional
                .ofNullable(objectMapper.readValue(requestEntity.getBody(), DeleteOrderDto.class))
                .orElseThrow(() -> new ParseJsonException("Error parse OrderDto"));

            log.info("Number table" + deleteOrder.getNumberTable() + "by" + titleRest);

            checkService.deleteOrder(deleteOrder);
            return HttpStatus.CREATED;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return HttpStatus.BAD_REQUEST;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{titleRest}/cart/payment")
    public HttpStatus shoppingCartPayment(@PathVariable String titleRest,
                                          @NotNull HttpEntity<String> requestEntity) {

        ObjectMapper objectMapper = new ObjectMapper();
        log.info("shoppingCartPayment " + requestEntity.getBody());
        try {
            CheckTableDto checkTableDto = Optional.ofNullable(
                objectMapper.readValue(requestEntity.getBody(), CheckTableDto.class))
                .orElseThrow(() -> new ParseJsonException("Error parse OrderDto"));
                log.info(checkTableDto.toString());
             checkService.payment(titleRest, checkTableDto);
             return HttpStatus.CREATED;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return HttpStatus.BAD_REQUEST;
    }
}