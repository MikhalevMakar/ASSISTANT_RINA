package ru.nsu.sber_portal.ccfit.models.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import ru.nsu.sber_portal.ccfit.models.ContextModel;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends ContextModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_restaurant", length = MAX_LENGTH_TEXT)
    private String nameRestaurant;

    @Column(name = "info_rest", length = MAX_LENGTH_TEXT)
    private String infoRest;

    @Column(name = "count_table")
    private Integer countTable;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<CategoryMenu> categoryMenus;

    @OneToMany(cascade = { CascadeType.REFRESH,
                           CascadeType.DETACH,
                           CascadeType.MERGE,
                           CascadeType.PERSIST }, mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<CheckTable> checkTables;

    @OneToMany(cascade = { CascadeType.REFRESH,
                           CascadeType.MERGE,
                           CascadeType.PERSIST }, mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<Review> reviews;

    public void addCheckTable(CheckTable checkTable) {
        checkTables.add(checkTable);
    }

    @Override
    public String toString() {
        return "Id: " + id + " name restaurant" + nameRestaurant;
    }
}
