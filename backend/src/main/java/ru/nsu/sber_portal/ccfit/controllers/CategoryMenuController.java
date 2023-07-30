package ru.nsu.sber_portal.ccfit.controllers;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.models.dto.DishDto;
import ru.nsu.sber_portal.ccfit.services.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/backend", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
@RestController
public class CategoryMenuController {

    private final CategoryMenuService categoryMenuService;

    @GetMapping("/{titleRest}/category/{id}")
    public List<DishDto> viewDishByCategory(@PathVariable String titleRest, @PathVariable Long id){
        log.info ("View dish by category,  rest: " + titleRest);
        return categoryMenuService.getListDishByCategory(id);
    }
}

// TODO application properties изменить catalog-database, пароль
// добавить category name в Dish