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

    private final ImageRepository imageRepository;

    public List<Dish> listDish(String title) {
        return (title != null) ? dishRepository.findByTitle(title) : dishRepository.findAll();
    }

    public void saveDish(@NotNull Dish dish, @NotNull MultipartFile file) {
        Image image = toImageEntity(file);
        Dish savedDish = dishRepository.save(dish);
        image.setDish(savedDish);
        Image savedImage = imageRepository.save(image);
        savedDish.setImage(savedImage);

        log.info("Saving new dish. Title: {}; Price: {}, Image {}", savedDish.getTitle(),
                                                                    savedDish.getPrice(),
                                                                    savedDish.getImage().getSize());

        dishRepository.save(savedDish);
    }

    private @NotNull Image toImageEntity(@NotNull MultipartFile file) {
        Image image = new Image();
        image.setName(file.getName());
        image.setName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            log.warn("IOException occurred", e);
            throw new IllegalStateException(e);
        }
        return image;
    }

    public Dish getDishById(Long id) {
        log.info("Find dish by id {}", id);

        return dishRepository.findById(id)
               .orElseThrow(() -> new FindByIdException(String.format("Id %d isn't found", id)));
    }
}
