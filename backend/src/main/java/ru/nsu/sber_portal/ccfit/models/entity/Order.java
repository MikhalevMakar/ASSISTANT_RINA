package ru.nsu.sber_portal.ccfit.models.entity;

import lombok.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderPattern;

import jakarta.persistence.*;


@Entity
@Table(name = "order_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends OrderPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = { CascadeType.REFRESH,
                           CascadeType.DETACH,
                           CascadeType.MERGE,
                           CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinColumn(name = "check_table_id")
    private CheckTable check;
    @Column(name = "price")
    private Long price;

    @Override
    public String toString() {
        return "Id: " + id;
    }
}