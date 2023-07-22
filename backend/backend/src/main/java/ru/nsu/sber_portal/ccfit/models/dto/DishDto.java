package ru.nsu.sber_portal.ccfit.models.dto;

import lombok.*;
import ru.nsu.sber_portal.ccfit.models.entity.*;

@AllArgsConstructor
@Data
public class DishDto {
     private String title;

     private String description;

     private int price;

     private double weight;

     private boolean isStopList;

     private String linkImage;

     private Image image;
}
