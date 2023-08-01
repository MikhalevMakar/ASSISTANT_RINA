package ru.nsu.sber_portal.ccfit.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "score")
    private Byte score;

    @Column(name = "text", columnDefinition = "text")
    private String text;

    @ManyToOne(cascade = { CascadeType.REFRESH,
                           CascadeType.MERGE,
                           CascadeType.PERSIST }, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Id: " + id;
    }
}
