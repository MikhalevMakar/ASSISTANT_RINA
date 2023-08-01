package ru.nsu.sber_portal.ccfit.services;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.sber_portal.ccfit.exceptions.FindRestByTitleException;
import ru.nsu.sber_portal.ccfit.models.dto.ReviewDto;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.mappers.ReviewMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final RestaurantRepository restRepository;

    @Transactional
    public void addNewReview(@NotNull String titleRest, @NotNull ReviewDto reviewDto) {
        log.info("Add new review");

        Restaurant restaurant = restRepository.findRestaurantByNameRestaurant(titleRest)
            .orElseThrow(() -> new FindRestByTitleException("No such rest " + titleRest));

        Review review = ReviewMapper.mapperToEntity(reviewDto);
        log.info("Save review");
        review.setRestaurant(restaurant);
        restaurant.getReviews().add(review);
        restRepository.save(restaurant);
    }

    @Transactional
    public List<ReviewDto> getListReviews(@NotNull String titleRest) {
        Restaurant restaurant = RestaurantService.createRestaurant(titleRest, restRepository);
        return reviewRepository.getListByRestaurant(restaurant).stream()
                .map(ReviewMapper::mapperToDto)
                .toList();
    }
}
