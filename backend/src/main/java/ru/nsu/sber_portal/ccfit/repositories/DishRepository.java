package ru.nsu.sber_portal.ccfit.repositories;

import ru.nsu.sber_portal.ccfit.models.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findByTitle(String titleRestaurant);

    List<Dish> findByCategoryMenuIdAndRestaurantId(Long categoryId , Long restId);
}