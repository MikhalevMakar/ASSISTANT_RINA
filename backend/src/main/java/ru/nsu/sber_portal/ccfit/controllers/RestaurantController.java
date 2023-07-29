package ru.nsu.sber_portal.ccfit.controllers;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.models.dto.CategoryMenuDto;
import ru.nsu.sber_portal.ccfit.services.RestaurantService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{title_rest}")
    public List<CategoryMenuDto> restaurant(@PathVariable String title_rest) {
        return restaurantService.getCategoryMenuByRest(title_rest);
    }
}