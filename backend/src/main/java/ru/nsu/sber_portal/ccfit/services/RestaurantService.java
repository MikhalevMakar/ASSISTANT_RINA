package ru.nsu.sber_portal.ccfit.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.exceptions.*;
import ru.nsu.sber_portal.ccfit.models.dto.CategoryMenuDto;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.mappers.CategoryMenuMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.*;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public static Restaurant createRestaurant(@NotNull String nameRest,
                                              @NotNull RestaurantRepository restRepository) {
        return restRepository.findByNameRestaurant(nameRest)
            .orElseThrow(() -> new FindRestByTitleException("No such rest " + nameRest));
    }

    private Optional<Restaurant> getRestByTitle(String title) {
        return ofNullable(restaurantRepository.findByNameRestaurant(title))
            .orElseThrow(() -> new FindRestByTitleException(
                String.format("Restaurant with title '%s' isn't found", title)));
    }

    @Transactional
    public List<CategoryMenuDto> getCategoryMenuByRest(String titleRest) {
        Restaurant restaurant = getRestByTitle(titleRest).orElseThrow(
            () -> new RestaurantNotFoundException(MessageFormat.format("Restaurant not found {}", titleRest))
        );

        List<CategoryMenu> categoryMenus = restaurant.getCategoryMenus();

        log.info("List category menu size " + categoryMenus.size());
        return categoryMenus.stream()
            .map(CategoryMenuMapper::mapperToDto)
            .toList();
    }
}