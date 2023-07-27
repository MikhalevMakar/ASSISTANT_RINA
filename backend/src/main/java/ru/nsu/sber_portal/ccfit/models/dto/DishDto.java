package ru.nsu.sber_portal.ccfit.models.dto;

import lombok.*;

@AllArgsConstructor
@Data
public class DishDto {
     private String title;

     private String description;

     private int price;

     private double weight;

     private boolean isStopList;

     private String linkImage;
}
