package ru.nsu.sber_portal.ccfit.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.exceptions.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestControllerAdvice
public class ErrorRestController {

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<String> handleFailedLoadImageException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Failed to load image" + ex);
    }

    @ExceptionHandler(value = {FindByIdException.class})
    public ResponseEntity<String> handleFailedFindByIdException(@NotNull FindByIdException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Failed to find entity by ID " + ex.getMessage());
    }

    @ExceptionHandler(value = {FindRestByTitleException.class})
    public ResponseEntity<String> handleFailedFindByTitleException(@NotNull FindRestByTitleException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Failed to find entity by text " + ex.getMessage());
    }


    @ExceptionHandler(value = {DishNotFoundException.class})
    public ResponseEntity<String> handleFailedFindDishException(@NotNull DishNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Dish not found on db in rest " + ex.getMessage());
    }

    @ExceptionHandler(value = {ParseJsonException.class})
    public ResponseEntity<String> handleFailedParseException(@NotNull ParseJsonException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Failed parse OrderDto " + ex.getMessage());
    }

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    public ResponseEntity<String> handleFailedFindCategoryException(@NotNull CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Category menu not found on db in rest " + ex.getMessage());
    }
}

