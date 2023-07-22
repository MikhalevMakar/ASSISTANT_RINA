package ru.nsu.sber_portal.ccfit.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import ru.nsu.sber_portal.ccfit.models.ContextModel;

import javax.persistence.*;

@Entity
@Table(name = "category_menu")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class CategoryMenu extends ContextModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", length = MAX_LENGTH_TEXT)
    private String title;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rest_id")
    private Restaurant restaurant;

    @Column(name = "link_image", length = MAX_LENGTH_TEXT)
    private String linkImage;

    @OneToOne
    private Image image;

    @Override
    public String toString() {
        return "Id: " + id + " name category menu " + title;
    }
}
