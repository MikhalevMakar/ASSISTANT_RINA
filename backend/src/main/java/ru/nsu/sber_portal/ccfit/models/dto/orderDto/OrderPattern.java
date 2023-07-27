package ru.nsu.sber_portal.ccfit.models.dto.orderDto;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Slf4j
public class OrderPattern {
    @Column(name = "dish_id")
    protected Long dishId;

    @Column(name = "number_table")
    protected Integer numberTable;

    @Column(name = "count")
    protected Integer count;

}
