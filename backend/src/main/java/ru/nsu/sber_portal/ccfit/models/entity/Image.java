package ru.nsu.sber_portal.ccfit.models.entity;

import lombok.*;
import ru.nsu.sber_portal.ccfit.models.ContextModel;
import jakarta.persistence.*;

@Entity
@Table(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image extends ContextModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private Long size;

    @Column(name = "content_type", length = MAX_LENGTH_TEXT)
    private String contentType;

    @Lob
    @Column(name = "bytes", columnDefinition = "bytea")
    private byte[] bytes;

    @OneToOne(mappedBy = "image")
    private Dish dish;

    @OneToOne(mappedBy = "image")
    private CategoryMenu categoryMenu;

    @Override
    public String toString() {
        return "Id: " + id + " image " + name;
    }
}