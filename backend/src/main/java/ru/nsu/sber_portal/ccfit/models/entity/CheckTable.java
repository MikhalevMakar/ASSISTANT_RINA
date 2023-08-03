package ru.nsu.sber_portal.ccfit.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import ru.nsu.sber_portal.ccfit.models.enums.SessionStatus;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "check_table")
@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "number_table")
    private Integer numberTable;

    @OneToMany(cascade = { CascadeType.REFRESH,
                           CascadeType.DETACH,
                           CascadeType.MERGE,
                           CascadeType.PERSIST },
             fetch = FetchType.EAGER, mappedBy = "check",
             orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    @Column(name = "cost")
    private Long cost = 0L;

    @Column(name = "session_status")
    private SessionStatus sessionStatus;

    @Column(name = "date_created")
    private LocalDateTime dateOfCreated;

    public void addNewOrder(@NotNull Order order) {
        orders.add(order);
    }

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Date created: " + dateOfCreated;
    }
}