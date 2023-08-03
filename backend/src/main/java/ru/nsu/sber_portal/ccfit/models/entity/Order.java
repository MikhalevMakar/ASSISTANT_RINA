package ru.nsu.sber_portal.ccfit.models.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "order_table")
@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = { CascadeType.REFRESH,
                           CascadeType.MERGE,
                           CascadeType.PERSIST },
               fetch = FetchType.EAGER)

    @JoinColumn(name = "check_table_id")
    private CheckTable check;

    @Column(name = "price")
    private Long price;

    @Column(name = "dish_id")
    protected Long dishId;

    @Column(name = "number_table")
    protected Integer numberTable;

    @Column(name = "count")
    protected Long count;

    @Override
    public String toString() {
        return "Id: " + id;
    }
}