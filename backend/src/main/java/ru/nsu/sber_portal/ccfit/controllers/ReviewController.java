package ru.nsu.sber_portal.ccfit.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.exceptions.ParseJsonException;
import ru.nsu.sber_portal.ccfit.models.dto.ReviewDto;
import ru.nsu.sber_portal.ccfit.services.ReviewService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/backend", produces = "application/json")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(value = "/{titleRest}/review")
    public ResponseEntity<Void> addNewReview(@PathVariable String titleRest,
                                             @RequestBody ReviewDto reviewDto) {
        log.info("Add new review in rest " + titleRest);
        log.info("Review dto " + reviewDto);
        reviewService.addNewReview(titleRest, reviewDto);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/{titleRest}/reviews")
    public List<ReviewDto> getReviews(@PathVariable String titleRest) {
        log.info("Get review in rest " + titleRest);
        return reviewService.getListReviews(titleRest);
    }
}
