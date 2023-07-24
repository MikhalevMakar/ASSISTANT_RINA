package ru.nsu.sber_portal.ccfit.models.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal_size")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealSize {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Dish dish;


    @Column(name = "size")
    private String size;
}
