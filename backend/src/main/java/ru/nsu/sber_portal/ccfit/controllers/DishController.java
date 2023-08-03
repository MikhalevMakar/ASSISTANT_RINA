package ru.nsu.sber_portal.ccfit.controllers;

import lombok.extern.slf4j.*;
import org.springframework.http.*;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.services.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/backend", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class DishController {

    private final DishService dishService;

    @PostMapping("/{titleRest}/dish")
    public ResponseEntity<DishDto> dishDescription(@PathVariable String titleRest,
                                                   @RequestBody DishFindDto dishFindDto) {
        log.info("Title rest: " + titleRest + " dish description " + dishFindDto);
        return Optional.ofNullable(dishService.getDishDto(titleRest, dishFindDto))
                       .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }
}