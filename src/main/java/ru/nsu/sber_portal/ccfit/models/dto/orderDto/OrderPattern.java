package ru.nsu.sber_portal.ccfit.models.dto.orderDto;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.jetbrains.annotations.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Data
public class OrderPattern {
    @NotNull
    @Column(name = "dish_id")
    protected Long dishId;

    @NotNull
    @Column(name = "number_table")
    protected Integer numberTable;

    @NotNull
    @Column(name = "count")
    protected Integer count;
}
