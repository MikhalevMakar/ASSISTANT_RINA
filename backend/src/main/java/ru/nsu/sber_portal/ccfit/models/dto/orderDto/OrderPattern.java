package ru.nsu.sber_portal.ccfit.models.dto.orderDto;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

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