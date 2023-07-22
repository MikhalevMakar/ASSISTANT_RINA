package ru.nsu.sber_portal.ccfit.models.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "dish")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaidOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "dish_id")
    private Long dish_id;

    private Long countFoodItem;
}
