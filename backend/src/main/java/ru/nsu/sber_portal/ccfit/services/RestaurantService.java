package ru.nsu.sber_portal.ccfit.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.exceptions.*;
import ru.nsu.sber_portal.ccfit.models.dto.CategoryMenuDto;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.mappers.CategoryMenuMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class RestaurantService {

    @Getter
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }


    public Restaurant createRestaurant(@NotNull String nameRest) {
        return restaurantRepository.findByNameRestaurant(nameRest)
            .orElseThrow(() -> new FindRestByTitleException(
                String.format("Restaurant with title '%s' isn't found", nameRest)));
    }

    @Transactional
    public List<CategoryMenuDto> getCategoryMenuByRest(String titleRest) {
        Restaurant restaurant = createRestaurant(titleRest);

        List<CategoryMenu> categoryMenus = restaurant.getCategoryMenus();

        log.info("List category menu size " + categoryMenus.size());
        return categoryMenus.stream()
            .map(CategoryMenuMapper::mapperToDto)
            .toList();
    }
}