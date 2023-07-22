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
    @GetMapping("/{titleRest}/dish/{id}")
    public ResponseEntity<Dish> dishDescription(@PathVariable String titleRest, @PathVariable Long id) {
        log.info("Title rest: " + titleRest + " dish description " + id);

        return Optional.ofNullable(dishService.getDishById(id))
                       .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }


}