package ru.nsu.sber_portal.ccfit.controllers;

import io.swagger.annotations.*;
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
@Api("Controller for restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @ApiOperation("Find restaurant and request to dish controller")
    @GetMapping("/{title}")
    public List<CategoryMenu> restaurant(@PathVariable String title) {
        return restaurantService.getCategoryMenuByRest(title);
    }
}