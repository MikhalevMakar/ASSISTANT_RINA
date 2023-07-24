package ru.nsu.sber_portal.ccfit.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ru.nsu.sber_portal.ccfit.models.dto.orderDto.OrderPattern;

@Entity
@Table(name = "order_table")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order extends OrderPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "check_table_id")
    private CheckTable check;

    @Column(name = "price")
    private Long price;

    @Override
    public String toString() {
        return "Id: " + id;
    }
}