package ru.nsu.sber_portal.ccfit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.exceptions.ParseJsonException;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.services.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/backend", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
@RestController
public class CategoryMenuController {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final CategoryMenuService categoryMenuService;

    @GetMapping("/{titleRest}/category")
    public CategoryDishesDto viewDishByCategory(@PathVariable String titleRest,
                                                @NotNull HttpEntity<String> requestEntity) throws JsonProcessingException {
        log.info ("View dish by category,  body : " + requestEntity.getBody ());

        DishFindDto dishFindDto = Optional.ofNullable (
                objectMapper.readValue (requestEntity.getBody (), DishFindDto.class))
            .orElseThrow (() -> new ParseJsonException ("Error parse OrderDto"));

        return categoryMenuService.getListDishByCategory(titleRest, dishFindDto);
    }
}

// TODO application properties изменить catalog-database, пароль
// dish category
// добавить category name в Dish