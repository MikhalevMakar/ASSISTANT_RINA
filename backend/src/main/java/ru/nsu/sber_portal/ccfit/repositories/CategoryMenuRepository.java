package ru.nsu.sber_portal.ccfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sber_portal.ccfit.models.entity.CategoryMenu;

@Repository
public interface CategoryMenuRepository extends JpaRepository<CategoryMenu, Long> {
    CategoryMenu findByRestaurantIdAndTitle(Long restId, String titleCategory);
}
