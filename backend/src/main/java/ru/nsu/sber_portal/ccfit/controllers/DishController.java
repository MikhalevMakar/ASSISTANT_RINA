package ru.nsu.sber_portal.ccfit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import ru.nsu.sber_portal.ccfit.exceptions.ParseJsonException;
import ru.nsu.sber_portal.ccfit.models.dto.*;
import ru.nsu.sber_portal.ccfit.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/backend", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class DishController {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final DishService dishService;

    @PostMapping("/{titleRest}/dish")
    public ResponseEntity<DishDto> dishDescription(@PathVariable String titleRest,
                                                   @NotNull HttpEntity<String> requestEntity) {

        log.info("Title rest: " + titleRest + " dish description " + requestEntity);
        DishFindDto dishFindDto;
        try {
            dishFindDto = Optional.ofNullable(
                objectMapper.readValue(requestEntity.getBody(), DishFindDto.class))
            .orElseThrow(() -> new ParseJsonException ("Error parse OrderDto"));

        } catch (JsonProcessingException e) {
            log.warn("During call method readValue " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return Optional.ofNullable(dishService.getDishDto(titleRest, dishFindDto))
                       .map(ResponseEntity::ok)
               .orElse(ResponseEntity.notFound().build());
    }
}