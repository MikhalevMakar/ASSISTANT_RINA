package ru.nsu.sber_portal.ccfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sber_portal.ccfit.models.entity.Restaurant;
import ru.nsu.sber_portal.ccfit.models.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> getListByRestaurant(Restaurant restaurant);
}
