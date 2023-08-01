package ru.nsu.sber_portal.ccfit.models.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Byte score;

    private String text;
}
