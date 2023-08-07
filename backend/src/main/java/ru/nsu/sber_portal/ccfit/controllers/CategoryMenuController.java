package ru.nsu.sber_portal.ccfit.controllers;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.services.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/backend", produces = "application/json")
@Slf4j
@RequiredArgsConstructor
@RestController
public class CategoryMenuController {

    private final CategoryMenuService categoryMenuService;

    @PostMapping("/{titleRest}/category")
    public CategoryDishesDto viewDishByCategory(@PathVariable String titleRest,
                                                @RequestBody DishFindDto dishFindDto) {
        log.info("View dish by category,  dto : " + dishFindDto);
        return categoryMenuService.getListDishByCategory(titleRest, dishFindDto);
    }
}