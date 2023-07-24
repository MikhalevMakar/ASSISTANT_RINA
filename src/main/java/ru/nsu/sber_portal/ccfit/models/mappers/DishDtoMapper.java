package ru.nsu.sber_portal.ccfit.models.mappers;

import org.jetbrains.annotations.*;
import ru.nsu.sber_portal.ccfit.models.dto.DishDto;
import ru.nsu.sber_portal.ccfit.models.entity.Dish;


public class DishDtoMapper {

    @Contract("_ -> new")
    public static @NotNull Dish mapToEntity(@NotNull DishDto dishDto) {
        Dish dish =  new Dish();

        dish.setTitle(dishDto.getTitle());
        dish.setPrice(dishDto.getPrice());
        dish.setWeight(dishDto.getWeight());
        dish.setIsStopList(dishDto.isStopList());
        dish.setLinkImage(dishDto.getLinkImage());
        //dish.setImage(dishDto.getImage());
        return dish;
    }

    public static @NotNull DishDto mapToDto(@NotNull Dish dish) {
        return new DishDto(dish.getTitle(),
                           dish.getDescription(),
                           dish.getPrice(),
                           dish.getWeight(),
                           dish.getIsStopList(),
                           dish.getLinkImage());
    }
}

//TODO: add category and mealSize