package ru.nsu.sber_portal.ccfit.models.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DishDto {

     private Long id;

     private String title;

     private String description;

     private Long price;

     private double weight;

     private boolean isStopList;

     private String linkImage;
}
