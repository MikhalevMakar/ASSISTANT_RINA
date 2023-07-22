package ru.nsu.sber_portal.ccfit.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import ru.nsu.sber_portal.ccfit.models.enums.SessionStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "check_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckTable { 

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "check", fetch = FetchType.EAGER)
    private List<Order> orders =  new ArrayList<>();

    @Column(name = "number_table")
    private Integer numberTable;

    @ManyToOne
    @JsonIgnore
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