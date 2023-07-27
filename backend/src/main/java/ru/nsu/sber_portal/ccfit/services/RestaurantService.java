package ru.nsu.sber_portal.ccfit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.exceptions.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.repositories.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final CategoryMenuRepository categoryMenuRepository;

    private Optional<Restaurant> getRestByTitle(String title) {
        return ofNullable(restaurantRepository.findByNameRestaurant(title))
            .orElseThrow(() -> new FindRestByTitleException(String.format("Restaurant with title '%s' isn't found", title)));
    }

    public List<CategoryMenu> getCategoryMenuByRest(String title) {
        Restaurant restaurant = getRestByTitle(title).orElse(null);
        if (restaurant == null)
            return Collections.emptyList();

        return categoryMenuRepository.findByRestaurantId(restaurant.getId());
    }
}
