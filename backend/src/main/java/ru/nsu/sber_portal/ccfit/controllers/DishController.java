package ru.nsu.sber_portal.ccfit.controllers;

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
public class DishController {

    private final DishService dishService;

    @GetMapping("/{titleRest}/dish/{id}")
    public ResponseEntity<Dish> dishDescription(@PathVariable String titleRest, @PathVariable Long id) {
        log.info("Title rest: " + titleRest + " dish description " + id);

        return Optional.ofNullable(dishService.getDishById(id))
                       .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }


}