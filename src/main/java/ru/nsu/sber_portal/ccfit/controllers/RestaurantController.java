package ru.nsu.sber_portal.ccfit.controllers;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.models.entity.CategoryMenu;
import ru.nsu.sber_portal.ccfit.services.RestaurantService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{title}")
    public List<CategoryMenu> restaurant(@PathVariable String title) {
        log.info("Get menu restaurant " + title);
        return restaurantService.getCategoryMenuByRest(title);
    }
}