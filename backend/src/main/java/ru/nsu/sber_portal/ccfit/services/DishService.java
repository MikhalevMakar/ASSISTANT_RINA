package ru.nsu.sber_portal.ccfit.services;

import ru.nsu.sber_portal.ccfit.exceptions.*;
import ru.nsu.sber_portal.ccfit.models.dto.DishDto;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.mappers.DishMapper;
import ru.nsu.sber_portal.ccfit.repositories.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    public List<Dish> listDish(String title) {
        return (title != null) ? dishRepository.findByTitle(title) : dishRepository.findAll();
    }

    public DishDto getDishById(Long id) {
        log.info("Find dish by id {}", id);

        return DishMapper.mapperToDto(dishRepository.findById(id)
                                        .orElseThrow(() -> new FindByIdException(
                                            String.format("Id %d isn't found", id))));
    }
}
