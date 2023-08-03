package ru.nsu.sber_portal.ccfit.controllers;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.services.*;

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
    @PostMapping("/{titleRest}/cart/payment")
    public HttpStatus shoppingCartPayment(@PathVariable String titleRest,
                                          @RequestBody CheckTableDto checkTableDto) {

        log.info("Shopping cart payment " + checkTableDto);
        checkService.payment(titleRest, checkTableDto);
        return HttpStatus.CREATED;
    }
}