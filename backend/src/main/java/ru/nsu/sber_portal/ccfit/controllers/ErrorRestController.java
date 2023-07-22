package ru.nsu.sber_portal.ccfit.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sber_portal.ccfit.exceptions.FindByIdException;
import ru.nsu.sber_portal.ccfit.exceptions.FindRestByTitleException;

@CrossOrigin(origins = "http://localhost:3000")
@RestControllerAdvice
public class ErrorRestController {

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<String> handleFailedLoadImageException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Failed to load image");
    }

    @ExceptionHandler(value = {FindByIdException.class})
    public ResponseEntity<String> handleFailedFindByIdException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Failed to find entity by ID");
    }

    @ExceptionHandler(value = {FindRestByTitleException.class})
    public ResponseEntity<String> handleFailedFindByTitleException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("Failed to find entity by text");
    }
}

