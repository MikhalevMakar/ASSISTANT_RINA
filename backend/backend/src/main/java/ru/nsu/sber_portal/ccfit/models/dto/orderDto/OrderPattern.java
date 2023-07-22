package ru.nsu.sber_portal.ccfit.models.dto.orderDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
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
