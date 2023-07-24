package ru.nsu.sber_portal.ccfit.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.nsu.sber_portal.ccfit.models.ContextModel;

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

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<CategoryMenu> categoryMenus;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<Dish> menuItems;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<CheckTable> checkTables;

    public void addCheckTable(CheckTable checkTable) {
        checkTables.add(checkTable);
    }

    @Override
    public String toString() {
        return "Id: " + id + " name restaurant" + nameRestaurant;
    }
}
