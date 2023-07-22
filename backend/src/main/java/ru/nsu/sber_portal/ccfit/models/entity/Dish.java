package ru.nsu.sber_portal.ccfit.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.nsu.sber_portal.ccfit.models.ContextModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryMenu categoryMenu;

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

    @JsonIgnore
    @OneToMany(mappedBy = "dish", fetch = FetchType.EAGER)
    List<MealSize> mealSize;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

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