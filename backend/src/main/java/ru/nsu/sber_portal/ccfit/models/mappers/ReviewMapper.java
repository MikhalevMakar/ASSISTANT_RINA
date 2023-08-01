package ru.nsu.sber_portal.ccfit.models.mappers;

import ru.nsu.sber_portal.ccfit.models.dto.ReviewDto;
import ru.nsu.sber_portal.ccfit.models.entity.Review;
import org.jetbrains.annotations.*;

public class ReviewMapper {

    @Contract("_ -> new")
    public static @NotNull Review mapperToEntity(@NotNull ReviewDto reviewDto) {
        Review review = new Review();
        review.setScore(reviewDto.getScore());
        review.setText(reviewDto.getText());
        return review;
    }

    @Contract("_ -> new")
    public static @NotNull ReviewDto mapperToDto(@NotNull Review review) {
        return new ReviewDto(review.getScore(), review.getText());
    }
}
