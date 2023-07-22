package ru.nsu.sber_portal.ccfit.controllers;

import io.swagger.annotations.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.services.*;

import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(produces = "application/json")
@RequiredArgsConstructor
@Slf4j
@Api("Controller for dish")
public class DishController {

    private final DishService dishService;

    @ApiOperation("Dish description")
    @GetMapping("/{title_rest}/dish/{id}")
    public ResponseEntity<Dish> dishDescription(@PathVariable String title_rest, @PathVariable Long id) {
        log.info("Title rest: ", title_rest);

        return Optional.ofNullable(dishService.getDishById(id))
                       .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }


}