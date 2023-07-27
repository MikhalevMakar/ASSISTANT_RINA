package ru.nsu.sber_portal.ccfit.services;

import ru.nsu.sber_portal.ccfit.exceptions.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.repositories.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.jetbrains.annotations.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    public List<Dish> listDish(String title) {
        return (title != null) ? dishRepository.findByTitle(title) : dishRepository.findAll();
    }

    public Dish getDishById(Long id) {
        log.info("Find dish by id {}", id);

        return dishRepository.findById(id)
               .orElseThrow(() -> new FindByIdException(String.format("Id %d isn't found", id)));
    }
}
