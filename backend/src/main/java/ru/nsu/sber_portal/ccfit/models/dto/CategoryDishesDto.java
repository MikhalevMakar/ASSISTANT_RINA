package ru.nsu.sber_portal.ccfit.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDishesDto {
    private List<DishDto> dishes;

    private String titleCategory;
}
