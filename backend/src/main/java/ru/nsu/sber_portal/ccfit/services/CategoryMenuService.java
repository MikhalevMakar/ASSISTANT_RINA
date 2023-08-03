package ru.nsu.sber_portal.ccfit.services;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.exceptions.*;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.mappers.DishMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;


import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
@Data
@RequiredArgsConstructor
public class CategoryMenuService {

    private final DishRepository dishRepository;

    private final CategoryMenuRepository categoryMenuRepository;

    private final RestaurantService restaurantService;


    @Transactional
    public CategoryDishesDto getListDishByCategory(@NotNull String titleRest, @NotNull DishFindDto dishFindDto) {
        log.info("Get list by category_id {} and restaurant", dishFindDto.getId());

        Restaurant restaurant = restaurantService.createRestaurant(titleRest);

        CategoryMenu categoryMenu = Optional.ofNullable(dishFindDto.getId())
            .flatMap(categoryMenuRepository::findById)
            .or(() -> Optional.ofNullable(categoryMenuRepository.findByRestaurantIdAndTitleIgnoreCase(restaurant.getId(),
                                                                                                      dishFindDto.getTitle())))
            .orElseThrow(() -> new CategoryNotFoundException(titleRest));

        List<Dish> dishes = dishRepository
                                .findByCategoryMenuIdAndRestaurantId(categoryMenu.getId(),
                                                                     restaurant.getId());

        log.info("List dish size " + dishes.size());
        return new CategoryDishesDto(dishes.stream().map(DishMapper::mapperToDto).toList(),
                                     categoryMenu.getTitle());
    }
}