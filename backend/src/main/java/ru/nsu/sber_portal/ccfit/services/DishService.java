package ru.nsu.sber_portal.ccfit.services;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sber_portal.ccfit.exceptions.DishNotFoundException;
import ru.nsu.sber_portal.ccfit.models.dto.DishDto;
import ru.nsu.sber_portal.ccfit.models.dto.DishFindDto;
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

    public static Dish createDish(@NotNull DishFindDto dishFindDto,
                                  @NotNull Restaurant restaurant,
                                  @NotNull DishRepository dishRepository) {
        log.info("Create dish");
        return Optional.ofNullable(dishFindDto.getId())
                .flatMap(dishRepository::findById)
                .or(() -> Optional.ofNullable(dishRepository.findByRestaurantIdAndTitleIgnoreCase(restaurant.getId(),
                                                                                                  dishFindDto.getTitle())))
                .orElseThrow(() -> new DishNotFoundException(restaurant.getNameRestaurant()));
    }

    public DishDto getDishDto(String titleRest, @NotNull DishFindDto dishFindDto) {
        log.info("Create dish dto");
        Restaurant restaurant = RestaurantService.createRestaurant(titleRest, restRepository);
        Dish dish = createDish(dishFindDto, restaurant, dishRepository);
        return DishMapper.mapperToDto(dish);
    }
}
