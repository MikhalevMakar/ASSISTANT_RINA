package ru.nsu.sber_portal.ccfit.services;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sber_portal.ccfit.models.dto.DishDto;
import ru.nsu.sber_portal.ccfit.models.dto.DishFindDto;
import ru.nsu.sber_portal.ccfit.models.entity.CategoryMenu;
import ru.nsu.sber_portal.ccfit.models.entity.Dish;
import ru.nsu.sber_portal.ccfit.models.entity.Restaurant;
import ru.nsu.sber_portal.ccfit.models.mappers.DishMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DishService {
    private final RestaurantRepository restRepository;

    private final DishRepository dishRepository;

    public DishDto getDishDto(String titleRest, @NotNull DishFindDto dishFindDto) {
        log.info("Create dish dto");
        Restaurant restaurant = RestaurantService.createRestaurant(titleRest, restRepository);

        Dish dish = Optional.ofNullable(dishFindDto.getId())
            .flatMap(dishRepository::findById)
            .orElseGet(() -> dishRepository.findByRestaurantIdAndTitle(restaurant.getId(),
                                                                       dishFindDto.getTitle()));
        return DishMapper.mapperToDto(dish);
    }
}
