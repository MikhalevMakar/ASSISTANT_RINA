package ru.nsu.sber_portal.ccfit.repositories;

import ru.nsu.sber_portal.ccfit.models.entity.CategoryMenu;
import ru.nsu.sber_portal.ccfit.models.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sber_portal.ccfit.models.entity.Restaurant;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findByTitle(String title);

    List<Dish> findByCategoryMenuIdAndRestaurant(Long category_id , Restaurant restaurant);
}