package ru.nsu.sber_portal.ccfit.models.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.nsu.sber_portal.ccfit.models.ContextModel;

import java.time.LocalDateTime;

@Entity
@Table(name = "dish")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish extends ContextModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "is_stop_list")
    private Boolean isStopList;

    @Column(name = "link_image", length = MAX_LENGTH_TEXT)
    private String linkImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryMenu categoryMenu;

    @Column(name = "date_created")
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Id: " + id + " name dish " + title;
    }
}